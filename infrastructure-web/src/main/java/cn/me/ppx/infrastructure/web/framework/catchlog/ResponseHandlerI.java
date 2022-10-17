package cn.me.ppx.infrastructure.web.framework.catchlog;

public interface ResponseHandlerI {
    public Object handle(Class returnType, int errCode, String errMsg);
}

