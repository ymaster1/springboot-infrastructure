package cn.me.ppx.infrastructure.common.exception;

public interface ResultCode {
    /**
     * 获得响应码
     * @return
     */
    int getResultCode();

    String getMessage();

}
