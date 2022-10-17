package cn.me.ppx.infrastructure.common.constants;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.TimeZone;

/**
 * @author yinlin
 * @version 1.0
 * @date 2017/6/5 11:31
 */
public class BaseConstants {

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final Charset DEFAULT_CHARSET_OBJ = Charset.forName(DEFAULT_CHARSET);

    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT+8");

    public static final String LONG_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

    //签名字段
    public static final String KEY_SIGN = "sign";
    //时间戳字段
    public static final String KEY_TIMESTAMP = "timestamp";

    public static final String PACKAGE_JAVA_LANG = "java.lang";

    public static final String URL_SEPARATOR = "/";

    public static final String HTTP_CONTENT_TYPE_JSON = "application/json";
    public static final String HTTP_CONTENT_TYPE_TEXT = "text/";
    public static final String HTTP_CONTENT_TYPE_XML = "xml";
    public static final String HTTP_CONTENT_TYPE_JS = "javascript";

    public static final String HTTP_HEADER_CONTENT_DISPOSITION = "Content-disposition";

    public static final String HEADER_SESSION_ID = "sessionId";

    public static final String HEADER_X_FORWARDED_FOR = "x-forwarded-for";

    public static final String HEADER_PROXY_CLIENT_IP = "Proxy-Client-IP";

    public static final String HEADER_WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
}
