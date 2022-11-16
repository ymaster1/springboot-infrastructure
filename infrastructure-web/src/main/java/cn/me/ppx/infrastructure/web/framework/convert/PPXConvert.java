package cn.me.ppx.infrastructure.web.framework.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 * @author ym
 * @date 2022/11/15 9:43
 * 由spring3.0引入的通用转换器 -----Converter
 * 由spring3.0引入的通用格式化器 -----Formatter
 * 属于 spring-core
 */
public class PPXConvert implements Converter<String, LocalDateTime>, Formatter<String> {


    @Override
    public Converter andThen(Converter after) {
        return Converter.super.andThen(after);
    }

    @Override
    public LocalDateTime convert(String source) {
        return null;
    }

    /**
     * 将String类型转到T类型
     * @param text the text string
     * @param locale the current user locale
     * @return
     * @throws ParseException
     */
    @Override
    public String parse(String text, Locale locale) throws ParseException {
        return null;
    }

    /**
     *  将Object写为String类型
     * @param object the instance to print
     * @param locale the current user locale
     * @return
     */
    @Override
    public String print(String object, Locale locale) {
        return null;
    }
}
