package cn.me.ppx.infrastructure.web.framework.servlet;

import cn.me.ppx.infrastructure.common.utils.MDCUtils;
import cn.me.ppx.infrastructure.common.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Set;

import static cn.me.ppx.infrastructure.web.framework.mdc.IpUtils.getIpAddress;


@Slf4j
public class RequestContextFilter implements Filter {

    private static final String RESPONSE_HEADER_NAME_REDIRECT = "Location";

    private Set<String> excludeUris;
    /**
     * 是否打印请求参数
     */
    private Boolean logParams;
    /**
     * 是否强制验证请求
     */
    private Boolean auth;

    /**
     * 根据servletContext 生成mdc前缀
     * 没有就忽略
     *
     * @param config
     */
    @Override
    public void init(FilterConfig config) {
        log.info("request filter init...");
        String contextPath = config.getServletContext().getContextPath();
        if (StringUtils.isBlank(contextPath)) {
            return;
        }
        StringBuilder sb = new StringBuilder(5);
        boolean isAppend = false;
        for (int i = 0, size = contextPath.length(); i < size; i++) {
            char c = contextPath.charAt(i);
            if ('/' == c || '-' == c || '_' == c || '\\' == c || '#' == c) {
                isAppend = true;
                continue;
            }
            if (c >= 'A' && c <= 'Z') {
                sb.append((char) (c + 32));
                isAppend = false;
                continue;
            }
            if (isAppend) {
                sb.append(c);
                isAppend = false;
            }
        }
        if (sb.length() > 0) {
            sb.append('_');
            System.out.println(sb);
            MDCUtils.setMsgIdPrefix(sb.toString());
        }
    }

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (auth) {
            // 网关或者内部服务调用，也可能直接接口调用
            System.out.println("auth  pass");
        }
        MDCUtils.init(req.getHeader(MDCUtils.KEY_MSG_ID));
        StringBuilder logBuffer = new StringBuilder("请求:请求方法[").append(req.getMethod()).append("]");
        //指定不拦截的路径，一般是静态资源
        if (this.isExclusionRequest(req)) {
            log.info(logBuffer.toString());
            chain.doFilter(request, response);
            return;
        }
        logBuffer.append(" 客户端请求IP地址:[").append(getIpAddress(req)).append("]");
        long start = System.currentTimeMillis();
        // 打印请求参数,看选择吧
        if (logParams) {
            this.logRequestParameters(logBuffer, req);
        }
        // servlet不可重复读问题
        if (RequestWrapper.isMustWrapper(req)) {
            RequestWrapper wrappedRequest = RequestWrapper.getRequestWrapper(req);
            this.logRequestBody(logBuffer, wrappedRequest);
            request = wrappedRequest;
        }
        log.info(logBuffer.toString());

        ResponseWrapper respWrapper = ResponseWrapper.getResponseWrapper(res);
        try {
            chain.doFilter(request, respWrapper);
        } finally {
            this.logResponse(respWrapper, start);
            this.removeMdcData();
        }
    }

    @Override
    public void destroy() {
    }

    private void removeMdcData() {
        MDCUtils.removeMsgId();
        MDCUtils.removeUrL();
    }

    private void logRequestParameters(StringBuilder logBuffer, HttpServletRequest request) {
        logBuffer.append(" 请求参数:[");
        if (HttpMethod.GET.name().equalsIgnoreCase(request.getMethod())) {
            logBuffer.append(request.getQueryString()).append("]");
            return;
        }
        String contentType = request.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            return;
        }
        if (contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            //不能调用getParameterNames及getParameter方法，否则后端接口可能取不到文件（使用MultipartHttpServletRequest获取文件时）
            return;
        }
        Enumeration<String> en = request.getParameterNames();
        if (!en.hasMoreElements()) {
            return;
        }

        while (en.hasMoreElements()) {
            String key = en.nextElement();
            if (StringUtils.isNotBlank(key)) {
                String value = request.getParameter(key);
                logBuffer.append("&").append(key).append("=").append(value);
            }
        }
        logBuffer.append("]");
    }

    private void logRequestBody(StringBuilder logBuffer, RequestWrapper request) {
        String contentType = request.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            return;
        }
        logBuffer.append(" contentType:[").append(contentType).append("]");
        String requestBody = request.getRequestText();
        if (null != requestBody) {
            logBuffer.append(" 请求消息体:[").append(requestBody).append("]");
        }
    }

    private void logResponse(ResponseWrapper response, long startMs) {
        StringBuilder logBuffer = new StringBuilder();
        int status = response.getStatus();
        logBuffer.append("响应: 请求处理共花费时间[")
                .append(System.currentTimeMillis() - startMs)
                .append("]毫秒, 返回状态:[")
                .append(status).append("]");
        if (302 == status) {
            //重定向
            logBuffer.append(", 重定向到[").append(response.getHeader(RESPONSE_HEADER_NAME_REDIRECT)).append("]");
            log.info(logBuffer.toString());
            return;
        }
        String contentType = response.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            log.info(logBuffer.toString());
            return;
        }
        logBuffer.append(" ,contentType:[").append(contentType).append("]");
        try {
            String content = response.getResponseText();
            if (null != content) {
                logBuffer.append(", size=[").append(content.length()).append("]bytes");
                logBuffer.append(", 响应消息体:[");
                logBuffer.append(content).append("]");
            }
        } catch (Exception e) {
            log.warn("Failed to parse response payload", e);
        }
        log.info(logBuffer.toString());
    }

    /**
     * 判断请求是否需要排除
     * (允许未登录访问，不输出响应日志)
     *
     * @param request
     * @return
     */
    public boolean isExclusionRequest(HttpServletRequest request) {
        if (null == request) {
            return true;
        }
        String servletPath = request.getServletPath();
        return Utils.isMatcher(excludeUris, servletPath);
    }

    public void setExcludeUris(Set<String> excludeUris) {
        this.excludeUris = excludeUris;
    }

    /**
     * 是否打印请求参数
     *
     * @param logParams
     */
    public void setLogParams(Boolean logParams) {
        this.logParams = logParams;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }
}
