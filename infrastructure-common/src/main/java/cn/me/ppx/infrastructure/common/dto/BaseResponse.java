package cn.me.ppx.infrastructure.common.dto;



import cn.me.ppx.infrastructure.common.exception.BaseException;

import java.io.Serializable;

/**
 * Response to caller
 *
 * @author fulan.zjf 2017年10月21日 下午8:53:17
 */
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;

    private int errCode;

    private String errMessage;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    @Override
    public String toString() {
        return "Response [success=" + success + ", errCode=" + errCode + ", errMessage=" + errMessage + "]";
    }

    public static <T> BaseResponse<T> success() {
        BaseResponse<T> response = new BaseResponse<>();
        response.setSuccess(true);
        return response;
    }

    public static <T> BaseResponse<T> fail(BaseException e) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setSuccess(false);
        response.setErrCode(e.getCode());
        response.setErrMessage(e.getMessage());
        return response;
    }

    public static <T> BaseResponse<T> fail(int errCode, String errMessage) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static <T> BaseResponse<T> build(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

}

