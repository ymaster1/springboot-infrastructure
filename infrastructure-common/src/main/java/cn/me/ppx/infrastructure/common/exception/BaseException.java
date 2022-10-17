package cn.me.ppx.infrastructure.common.exception;



/**
 * @author Administrator
 */
public  class BaseException extends Exception {

    private int code;
    private String message;

    public BaseException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseException(int code, String message, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    public BaseException(int code, String message, Object... args) {
        this.code = code;
        this.message = String.format(message, args);
        if (args != null && args.length > 0 && args[args.length - 1] instanceof Throwable) {
            this.initCause((Throwable) args[args.length - 1]);
        }

    }

    public BaseException(ErrorCode errorCode, Object... args) {
        if (errorCode != null) {
            this.code = errorCode.getErrCode();
            this.message = String.format(errorCode.getMessage(), args);
        }

        if (args != null && args.length > 0 && args[args.length - 1] instanceof Throwable) {
            this.initCause((Throwable) args[args.length - 1]);
        }

    }

    public BaseException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        if (errorCode != null) {
            this.code = errorCode.getErrCode();
            this.message = errorCode.getMessage();
        }

    }

    public BaseException(ErrorCode errorCode) {
        if (errorCode != null) {
            this.code = errorCode.getErrCode();
            this.message = errorCode.getMessage();
        }

    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}

