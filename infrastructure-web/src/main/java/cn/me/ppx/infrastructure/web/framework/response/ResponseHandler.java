package cn.me.ppx.infrastructure.web.framework.response;

import cn.me.ppx.infrastructure.common.dto.BaseResponse;
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
     * 这里表示所有都支持
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 当返回类型是String时，用的是StringHttpMessageConverter转换器，接收的是一个String,但是我们已经包装成BaseResponse了，要报错
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
     * 此时controller已经return了，这里是对响应给客户端之前进行包装
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     * todo 可以根据需要是否使用标准http响应
     * @see org.springframework.http.HttpEntity
     * @see org.springframework.http.ResponseEntity
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 已经封装过了(除了异常那边也会封装处理)
        System.out.println("ResponseBodyAdvice");
        if (body instanceof BaseResponse) {
            return body;
        }
        // 对于没有返回值的接口可以主动调用BaseResponse.success()进行封装，当然这里我们可以自己做个兜底
        if (body == null) {
            return BaseResponse.success();
        }
        // 当返回值是文件这些的时候还没处理 todo
        return BaseResponse.build(body);
    }
}
