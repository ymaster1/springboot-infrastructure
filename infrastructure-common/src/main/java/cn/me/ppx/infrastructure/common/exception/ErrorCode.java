package cn.me.ppx.infrastructure.common.exception;

public interface ErrorCode {
    /**
     * 获得错误码
     * @return
     */
    int getErrCode();

    String getMessage();

}
