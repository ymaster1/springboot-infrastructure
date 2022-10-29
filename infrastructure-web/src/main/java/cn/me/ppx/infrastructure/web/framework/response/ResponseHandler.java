package cn.me.ppx.infrastructure.web.framework.response;

import cn.hutool.json.JSONObject;
import cn.me.ppx.infrastructure.common.dto.BaseResponse;
import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * @author ym
 * @date 2022/10/28 13:49
 * 该注解亦可用于封装统一返回值
 *  当方法参数里面有HttpServletResponse 时它将不会生效
 */
@RestControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice<Object>, WebMvcConfigurer {
    /**
     * 哪些接口需要进行返回值包装
     *
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 当返回类型是String时，用的是StringHttpMessageConverter转换器，无法转换为Json格式,
     * 需要将处理 Object 类型的 HttpMessageConverter 放得靠前一些
     * 或者直接手动调用BaseResponse.build()就行了
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }

    /**
     * 返回值包装
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 已经封装过了(除了异常那边也会封装处理)
        if (body instanceof BaseResponse) {
            return body;
        }
        // 对于没有返回值的接口可以主动调用BaseResponse.success()进行封装，当然这里我们可以自己做个兜底
        if (body == null) {
            return BaseResponse.success();
        }
        return BaseResponse.build(body);
    }
}
