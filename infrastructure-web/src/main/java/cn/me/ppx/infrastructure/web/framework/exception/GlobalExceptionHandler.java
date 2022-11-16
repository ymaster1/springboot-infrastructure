package cn.me.ppx.infrastructure.web.framework.exception;

import cn.me.ppx.infrastructure.common.dto.BaseResponse;
import cn.me.ppx.infrastructure.common.exception.BizException;
import cn.me.ppx.infrastructure.common.exception.SysCodeEnum;
import cn.me.ppx.infrastructure.common.exception.SysException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ym
 * @date 2022/9/30 10:48
 * 全局异常拦截
 *
 * 也可以使用HandlerExceptionResolver实现
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常
     */
    @ExceptionHandler(BizException.class)
    public BaseResponse<Object> handleBizException(BizException e) {
        log.error(e.getMessage(), e);
        return BaseResponse.fail(e);
    }


    /**
     * 系统异常
     */
    @ExceptionHandler(SysException.class)
    public BaseResponse<Object> handleSysException(SysException e) {
        log.error(e.getMessage(), e);
        return BaseResponse.fail(e);
    }

    /**
     * 乱七八糟的其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse<Object> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return BaseResponse.fail(new SysException(SysCodeEnum.SYSTEM_ERROR));
    }
}
