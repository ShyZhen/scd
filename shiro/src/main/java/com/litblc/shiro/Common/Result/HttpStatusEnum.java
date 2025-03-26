package com.litblc.shiro.Common.Result;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum HttpStatusEnum {
    HTTP_OK(HttpStatus.OK.value(), "操作成功"),
    HTTP_CREATED(HttpStatus.CREATED.value(), "创建成功"),
    HTTP_ACCEPTED(HttpStatus.ACCEPTED.value(), "操作成功"),
    HTTP_NO_CONTENT(HttpStatus.NO_CONTENT.value(), "操作成功"),
    HTTP_MOVED_PERMANENTLY(HttpStatus.MOVED_PERMANENTLY.value(), "资源已被移除"),
    HTTP_SEE_OTHER(HttpStatus.SEE_OTHER.value(), "重定向"),
    HTTP_NOT_MODIFIED(HttpStatus.NOT_MODIFIED.value(), "资源未被修改"),
    HTTP_BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "参数错误"),
    HTTP_UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "未授权"),
    HTTP_FORBIDDEN(HttpStatus.FORBIDDEN.value(), "访问受限"),
    HTTP_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "资源未找到"),
    HTTP_METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "不允许的请求类型"),
    HTTP_CONFLICT(HttpStatus.CONFLICT.value(), "资源被锁定"),
    HTTP_UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY.value(), "无法处理的类型"),
    HTTP_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误"),
    OTHER(601, "系统错误"),
    SERVICE(9999, "系统错误");  // 自定义业务异常

    private final Integer code;
    private final String message;

    HttpStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
