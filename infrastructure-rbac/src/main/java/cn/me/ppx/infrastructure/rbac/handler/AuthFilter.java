package cn.me.ppx.infrastructure.rbac.handler;

import cn.me.ppx.infrastructure.common.utils.StringUtils;
import cn.me.ppx.infrastructure.rbac.config.AuthHeaderEnum;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ym
 * @date 2022/11/15 17:26
 * filter的作用应该只控制在拦截请求上
 * 这里是对需要认证的而未进行认证的请求进行拦截
 */
public class AuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(AuthHeaderEnum.BACKEND.getCode());
        // 这里只对是否携带token进行校验
        if (StringUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);
        }
    }
}
