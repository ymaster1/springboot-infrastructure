package cn.me.ppx.infrastructure.rbac.handler;

import cn.me.ppx.infrastructure.common.utils.StringUtils;
import cn.me.ppx.infrastructure.rbac.User;
import cn.me.ppx.infrastructure.redis.table.TableRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ym
 * @date 2022/11/15 17:28
 * 为了能让Autowired生效，有两种解决方案
 * 1：将本身注册为bean
 * 2：给该Interceptor赋予注入的能力，但本身不用注册为bean
 */
public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private TableRedis tableRedis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("auth_token");
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        User user = new User();
        request.setAttribute("user", user);
        return true;
    }
}
