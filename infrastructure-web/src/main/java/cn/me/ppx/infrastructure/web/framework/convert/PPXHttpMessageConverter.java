package cn.me.ppx.infrastructure.web.framework.convert;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.List;

/**
 * @author ym
 * @date 2022/11/15 9:41
 * 由spring 3.0 引入,专门用于将http请求内容转换为java对象,
 * 以及将java对象转换成HTTP响应内容，
 * 说白了就是对HTTP的反序列化和序列化，它的仅仅被用于HTTP主体和java对象之间的转换。
 */
public class PPXHttpMessageConverter implements HttpMessageConverter<String> {
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    /**
     * 可以对指定类型的MediaType自定义响应
     * @return
     */
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return null;
    }

    /**
     * 请求报文
     * @return
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */

    @Override
    public String read(Class<? extends String> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    /**
     * 响应报文
     * @param outputMessage the message to write to
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    public void write(String s, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

    }
}
