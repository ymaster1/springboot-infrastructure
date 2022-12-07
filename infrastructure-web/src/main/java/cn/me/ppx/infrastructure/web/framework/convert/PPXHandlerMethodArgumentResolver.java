package cn.me.ppx.infrastructure.web.framework.convert;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author ym
 * @date 2022/11/15 9:47
 * 参数解析器，借助参数转换Convert来实现的
 * @see <a href="https://cloud.tencent.com/developer/article/1497397">...</a>
 */
public class PPXHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * 有时候需要对接第三方接口，但是对方的返回值是个嵌套很深的json,或者命名也不规范，这时候可以自己定义一个解析器关注自己需要的参数就行了，转换成自己想要的
     * @param parameter the method parameter to check
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return null;
    }
}
