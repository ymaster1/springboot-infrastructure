package cn.me.ppx.infrastructure.common.exception;


/**
 * @author ym
 * @date 2022/9/30 11:05
 * 系统组件预先定义的相应类型
 */
public enum SysCodeEnum implements ResultCode {
    SYSTEM_ERROR(500, "系统异常"),
    SYSTEM_SUCCESS(200, "成功"),

    TOPIC_MISS(501, "event bus topic miss");
    private final int code;
    private final String msg;

    SysCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getResultCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
