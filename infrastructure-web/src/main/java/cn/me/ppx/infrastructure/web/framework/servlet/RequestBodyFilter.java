package cn.me.ppx.infrastructure.web.framework.servlet;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description: TODO
 * @author: gaojian@metagroup.com
 * @date: 2022/10/10 17:42
 */
@Component
public class RequestBodyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        ServletRequest request = null;
        if (servletRequest instanceof HttpServletRequest) {
            request = new InfraHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        }
        if (request == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(request, servletResponse);
        }
    }

}
