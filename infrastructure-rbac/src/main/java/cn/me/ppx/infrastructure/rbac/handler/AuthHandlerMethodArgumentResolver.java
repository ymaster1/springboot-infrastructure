package cn.me.ppx.infrastructure.rbac.handler;

import cn.me.ppx.infrastructure.rbac.User;
import cn.me.ppx.infrastructure.rbac.annotation.Auth;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ym
 * @date 2022/11/15 15:41
 */
public class AuthHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * @param parameter the method parameter to check
     * @return
     * @Auth User user 这种才会进行解析
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Auth ann = parameter.getParameterAnnotation(Auth.class);
        Class<?> parameterType = parameter.getParameterType();
        return (ann != null &&
                (User.class.isAssignableFrom(parameterType)));
    }

    /**
     * 可以自己从request中获取，也可以在handler中处理，然后放到这里面
     *
     * @return User
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            return null;
        }
        return request.getAttribute("user");
    }
}
