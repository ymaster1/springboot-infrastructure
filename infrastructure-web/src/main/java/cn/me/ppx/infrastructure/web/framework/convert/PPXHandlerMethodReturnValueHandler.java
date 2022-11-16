package cn.me.ppx.infrastructure.web.framework.convert;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author ym
 * @date 2022/11/15 9:40
 * 返回值解析器
 * 另一种更直接的统一响应格式实现，不需要每次调用都使用Response.build()
 * 其实和ResponseBodyAdvice的作用是一样的，只不过这个是在return之前
 * 要使用还有点麻烦
 */
public class PPXHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return false;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        System.out.println("HandlerMethodReturnValueHandler");

    }
}
