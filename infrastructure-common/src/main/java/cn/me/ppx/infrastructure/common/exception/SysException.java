package cn.me.ppx.infrastructure.common.exception;



public class SysException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_ERR_CODE = SysCodeEnum.SYSTEM_ERROR.getResultCode();

    public SysException(String errMessage) {
        super(DEFAULT_ERR_CODE, errMessage);
    }
    public SysException(ResultCode codeEnum){
        super(codeEnum);
    }

    public SysException(int errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public SysException(String errMessage, Throwable e) {
        super(DEFAULT_ERR_CODE, errMessage, e);
    }

    public SysException(int errorCode, String errMessage, Throwable e) {
        super(errorCode, errMessage, e);
    }

    public SysException(ResultCode errorMsg, Throwable e) {
        super(errorMsg.getResultCode(), errorMsg.getMessage(), e);
    }

}
