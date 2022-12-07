package cn.me.ppx.infrastructure.common.dto;



import cn.me.ppx.infrastructure.common.exception.BaseException;
import cn.me.ppx.infrastructure.common.utils.MDCUtils;

import java.io.Serializable;

import static cn.me.ppx.infrastructure.common.exception.SysCodeEnum.SYSTEM_SUCCESS;

/**
 * Response to caller
 *
 * @author fulan.zjf 2017年10月21日 下午8:53:17
 */
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String requestId;

    private int code;

    private String msg;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Response [success=" + requestId + ", code=" + code + ", msg=" + msg + "]";
    }

    public static <T> BaseResponse<T> success() {
        BaseResponse<T> response = new BaseResponse<>();
        response.setRequestId(MDCUtils.getMsgId());
        response.setMsg(SYSTEM_SUCCESS.getMessage());
        response.setCode(SYSTEM_SUCCESS.getResultCode());
        return response;
    }

    public static <T> BaseResponse<T> fail(BaseException e) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setRequestId(MDCUtils.getMsgId());
        response.setCode(e.getCode());
        response.setMsg(e.getMessage());
        return response;
    }

    public static <T> BaseResponse<T> fail(int errCode, String errMessage) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setRequestId(MDCUtils.getMsgId());
        response.setCode(errCode);
        response.setMsg(errMessage);
        return response;
    }

    public static <T> BaseResponse<T> fail(int errCode, String errMessage,T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setRequestId(MDCUtils.getMsgId());
        response.setCode(errCode);
        response.setMsg(errMessage);
        response.setData(data);
        return response;
    }

    public static <T> BaseResponse<T> build(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setRequestId(MDCUtils.getMsgId());
        response.setMsg(SYSTEM_SUCCESS.getMessage());
        response.setCode(SYSTEM_SUCCESS.getResultCode());
        response.setData(data);
        return response;
    }

}

