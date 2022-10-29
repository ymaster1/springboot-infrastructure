package cn.me.ppx.infrastructure.common.exception;



public class BizException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_ERR_CODE = SysCodeEnum.SYSTEM_ERROR.getResultCode();

    public BizException(String errMessage) {
        super(DEFAULT_ERR_CODE, errMessage);
    }

    public BizException(int errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public BizException(String errMessage, Throwable e) {
        super(DEFAULT_ERR_CODE, errMessage, e);
    }

    public BizException(int errorCode, String errMessage, Throwable e) {
        super(errorCode, errMessage, e);
    }

    public BizException(ResultCode ResultCode, Throwable e) {
        super(ResultCode.getResultCode(), ResultCode.getMessage(), e);
    }

    public BizException(ResultCode ResultCode) {
        super(ResultCode.getResultCode(), ResultCode.getMessage());
    }

}