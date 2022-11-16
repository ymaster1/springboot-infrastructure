package cn.me.ppx.infrastructure.rbac.handler;

import cn.me.ppx.infrastructure.common.utils.StringUtils;
import cn.me.ppx.infrastructure.rbac.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ym
 * @date 2022/11/15 17:28
 *
 */
public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("auth_token");
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        User user = new User();
        request.setAttribute("user",user);
        return true;
    }
}
