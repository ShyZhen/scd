package com.litblc.shiro.Common.Exception;

import com.litblc.shiro.Common.Result.HttpStatusEnum;
import com.litblc.shiro.Common.Result.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 全局异常处理类
 */
// 用于定义全局异常处理类的注解，注解@RestControllerAdvice的类的方法可以使用@ExceptionHandler、@InitBinder、@ModelAttribute注解到方法上
// 用于所有注解了@RequestMapping的控制器的方法

@Slf4j
@RestControllerAdvice
public class GlobalException {

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求,msg'{}'", requestURI, e.getMethod(), e.getMessage());
        return Result.fail(HttpStatusEnum.HTTP_METHOD_NOT_ALLOWED.getCode(), HttpStatusEnum.HTTP_METHOD_NOT_ALLOWED.getMessage());
    }

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<?> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return Result.fail(HttpStatusEnum.HTTP_FORBIDDEN.getCode(), HttpStatusEnum.HTTP_FORBIDDEN.getMessage());
    }

    /**
     * 业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public Result<?> handleServiceException(ServiceException e) {
        String msg = Optional.ofNullable(e.getMessage()).orElse("服务异常");
        log.error("请求地址'{}', at:\n\t{}", msg, buildSimpleStatckTraceMsg(e));
        return Result.fail(msg);
    }

    /**
     * 未知的运行时异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", uri, e);
        return Result.fail(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return Result.fail(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getMessage();
        return Result.fail(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常'{}'", e.getMessage());
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return Result.fail(message);
    }






    private String buildSimpleStatckTraceMsg(Throwable e) {
        if (e.getStackTrace().length > 3) {
            List<StackTraceElement> stacks = Arrays.asList(e.getStackTrace()[0], e.getStackTrace()[1], e.getStackTrace()[2]);
            return StringUtils.join(stacks, "\n\t");
        }
        return StringUtils.join(e.getStackTrace(), "\n\t");
    }
}
