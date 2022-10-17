package cn.me.ppx.infrastructure.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @author yinlin
 */
@Slf4j
public class JsonMapper {

    public static ObjectMapper MAPPER = nonNullMapper();

    private static TypeFactory typeFactory = MAPPER.getTypeFactory();

    public static ObjectMapper getMapper(JsonInclude.Include include) {
        ObjectMapper mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        if (include != null) {
            mapper.setSerializationInclusion(include);
        }
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //增加转义符支持
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        //日期类型转换为时间戳
        SimpleModule module = new SimpleModule("DatesModule", new Version(0, 1, 0, "alpha", "cn.org.easysite", "kds"));
        module.addSerializer(Date.class, new DateTimeSerializer());
        mapper.registerModule(module);
        return mapper;
    }

    public static class DateTimeSerializer extends JsonSerializer<Date> {
        @Override
        public void serialize(
                Date value,
                JsonGenerator jgen,
                SerializerProvider provider) throws IOException {
            if (null == jgen) {
                return;
            }
            if (value == null) {
                jgen.writeNull();
            } else {
                jgen.writeNumber(value.getTime());
            }
        }
    }

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
     */
    public static ObjectMapper nonEmptyMapper() {
        return JsonMapper.getMapper(JsonInclude.Include.NON_EMPTY);
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
     */
    public static ObjectMapper nonNullMapper() {
        return JsonMapper.getMapper(JsonInclude.Include.NON_NULL);
    }

    /**
     * 转换成JSON
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        if (null == obj) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException jpe) {
            log.error("object to json error ", jpe);
        }
        return null;
    }

    /**
     * JSON转换为对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (null == json) {
            return null;
        }
        try {
            return MAPPER.readValue(json, constructType(clazz));
        } catch (IOException ioe) {
            log.error("json to object ", ioe);
        }
        return null;
    }

    /**
     * 从JSON字符串转换为带范型的对象
     * @param json 需要转换的JSON字符串
     * @param javaType 需要转换的JavaType
     * @param <T> 返回的对象类型
     * @return 转换后的对象
     */
    public static <T> T fromJson(String json, JavaType javaType) {
        if (null == json) {
            return null;
        }
        try {
            return MAPPER.readValue(json, javaType);
        } catch (IOException ioe) {
            log.error("json to object ", ioe);
        }
        return null;
    }

    /**
     * 将Class 转换为JavaType
     * @param clazz 需要转换的Class
     * @return 转换后的JavaType对象
     */
    public static JavaType constructType(Class<?> clazz) {
        return typeFactory.constructType(clazz);
    }

    /**
     * 将class 包含枚举类型转换为JavaType
     * @param clazz 需要转换的Class
     * @param elementClass 范型Class
     * @return 转换后的JavaType对象
     */
    public static JavaType constructType(Class clazz, Class elementClass) {
        return typeFactory.constructParametrizedType(clazz, clazz, elementClass);
    }

    public static JavaType constructType(Class clazz, JavaType elementClass) {
        return typeFactory.constructParametrizedType(clazz, clazz, elementClass);
    }

    /**
     * 构造Collection类型.
     */
    public static JavaType constructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return typeFactory.constructCollectionType(collectionClass, elementClass);
    }

    /**
     * 构造Map类型.
     */
    public static JavaType constructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return typeFactory.constructMapType(mapClass, keyClass, valueClass);
    }
}
