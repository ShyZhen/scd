package com.litblc.shiro.Common.Result;

import lombok.Getter;

@Getter
public enum HttpStatusEnum {
    HTTP_OK(200, "操作成功"),
    HTTP_CREATED(201, "创建成功"),
    HTTP_ACCEPTED(202, "操作成功"),
    HTTP_NO_CONTENT(204, "操作成功"),
    HTTP_MOVED_PERMANENTLY(301, "资源已被移除"),
    HTTP_SEE_OTHER(303, "重定向"),
    HTTP_NOT_MODIFIED(304, "资源未被修改"),
    HTTP_BAD_REQUEST(400, "参数错误"),
    HTTP_UNAUTHORIZED(401, "未授权"),
    HTTP_FORBIDDEN(403, "访问受限"),
    HTTP_NOT_FOUND(404, "资源未找到"),
    HTTP_METHOD_NOT_ALLOWED(405, "不允许的请求类型"),
    HTTP_CONFLICT(409, "资源被锁定"),
    HTTP_UNPROCESSABLE_ENTITY(422, "无法处理的类型"),
    HTTP_SERVER_ERROR(500, "系统内部错误"),
    OTHER(601, "系统错误");

    private final Integer code;
    private final String message;

    HttpStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
