package cn.me.ppx.infrastructure.common.exception;


/**
 * @author ym
 * @date 2022/9/30 11:05
 * 系统组件预先定义的错误类型
 */
public enum SysCodeEnum implements ErrorCode {
    SYSTEM_ERROR(1, "系统异常");
    private final int code;
    private final String msg;

    SysCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getErrCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
