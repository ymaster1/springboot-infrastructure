package cn.me.ppx.infrastructure.common.exception;


import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;


public abstract class AssertTool {

    public static void isTrue(boolean expression, int errorCode, String errMessage) throws BizException {
        if (!expression) {
            throw new BizException(errorCode, errMessage);
        }
    }

    public static void isTrue(boolean expression, ResultCode ResultCode)throws BizException {
        if (!expression) {
            throw new BizException(ResultCode);
        }
    }


    public static void isFalse(boolean expression, int errorCode, String errMessage) throws BizException{
        if (expression) {
            throw new BizException(errorCode, errMessage);
        }
    }

    public static void isFalse(boolean expression, ResultCode ResultCode)throws BizException {
        if (expression) {
            throw new BizException(ResultCode);
        }
    }

    public static void notNull(Object object, int errorCode, String errMessage)throws BizException {
        if (object == null) {
            throw new BizException(errorCode, errMessage);
        }
    }

    public static void notNull(Object object, ResultCode err)throws BizException {
        if (object == null) {
            throw new BizException(err);
        }
    }


    public static void assertNull(Object obj, ResultCode err) throws BizException{
        if (null != obj) {
            throw new BizException(err);
        }
    }

    public static void assertNull(Object object, int errorCode, String errMessage) throws BizException{
        if (null != object) {
            throw new BizException(errorCode, errMessage);
        }
    }

    public static void assertNotBlank(String str, int errorCode, String errMessage)throws BizException {
        if (StringUtils.isBlank(str)) {
            throw new BizException(errorCode, errMessage);
        }
    }

    public static void assertNotBlank(String str, ResultCode err)throws BizException {
        if (StringUtils.isBlank(str)) {
            throw new BizException(err);
        }
    }

    public static void notEmpty(Collection<?> collection, int errorCode, String errMessage)throws BizException {
        if (collection == null || collection.isEmpty()) {
            throw new BizException(errorCode, errMessage);
        }
    }

    public static void notEmpty(Collection<?> collection, ResultCode err)throws BizException {
        if (collection == null || collection.isEmpty()) {
            throw new BizException(err);
        }
    }


    public static void notEmpty(Map<?, ?> map, int errorCode, String errMessage)throws BizException {
        if (map == null || map.isEmpty()) {
            throw new BizException(errorCode, errMessage);
        }
    }

    public static void notEmpty(Map<?, ?> map, ResultCode err)throws BizException {
        if (map == null || map.isEmpty()) {
            throw new BizException(err);
        }
    }

}

