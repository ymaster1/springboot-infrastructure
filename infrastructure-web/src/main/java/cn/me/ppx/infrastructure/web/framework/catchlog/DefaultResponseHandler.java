package cn.me.ppx.infrastructure.web.framework.catchlog;


import cn.me.ppx.infrastructure.common.dto.BaseResponse;
import cn.me.ppx.infrastructure.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

/**
 * ResponseHandler
 *
 * @author Frank Zhang
 * @date 2020-11-10 3:18 PM
 */
@Slf4j
public class DefaultResponseHandler implements ResponseHandlerI {

    @Override
    public Object handle(Class returnType, int errCode, String errMsg) {
        if (isColaResponse(returnType)) {
            return handleColaResponse(returnType, errCode, errMsg);
        }
        return null;
    }

    public Object handle(Class returnType, BaseException e) {
        return handle(returnType, e.getCode(), e.getMessage());
    }


    private static Object handleColaResponse(Class returnType, int errCode, String errMsg) {
        try {
            BaseResponse response = (BaseResponse) returnType.newInstance();
            response.setRequestId("false");
            response.setCode(errCode);
            response.setMsg(errMsg);
            return response;
        } catch (Exception ex) {

            return null;
        }
    }

    private static boolean isColaResponse(Class returnType) {
        return returnType == BaseResponse.class || returnType.getGenericSuperclass() == BaseResponse.class;
    }
}

