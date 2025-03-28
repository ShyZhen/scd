package com.litblc.shiro.common.Result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(name = "common统一返回类result")
public class Result<T> implements Serializable {

    //打开类文件，将光标定位到类名处。
    //按下 Alt + Enter → 选择 Add serialVersionUID field

    @Serial
    private static final long serialVersionUID = 7064836058516333477L;

    @Schema(description = "状态码")
    private Integer code;

    @Schema(description = "状态")
    private Boolean status;

    @Schema(description = "返回信息")
    private String msg;

    @Schema(description = "返回数据")
    private T data;


    /**
     * 成功返回（无参数）
     * @return
     * @param <T>
     */
    public static <T> Result<T> success() {
        return response(HttpStatusEnum.HTTP_OK.getCode(), true, HttpStatusEnum.HTTP_OK.getMessage());
    }

    /**
     * 成功返回（枚举参数）
     * @param httpStatusEnum
     * @return
     * @param <T>
     */
    public static <T> Result<T> success(HttpStatusEnum httpStatusEnum) {
        return response(httpStatusEnum.getCode(), true, httpStatusEnum.getMessage());
    }

    /**
     * 成功返回（手动指定状态码+信息）
     * @param code
     * @param msg
     * @return
     * @param <T>
     */
    public static <T> Result<T> success(Integer code, String msg) {
        return response(code, true, msg);
    }

    /**
     * 成功返回（自动code+手动信息+数据）
     * @param msg
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> success(String msg, T data) {
        return response(HttpStatusEnum.HTTP_OK.getCode(), true, msg, data);
    }

    /**
     * 成功返回（全手动）
     * @param code
     * @param msg
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> success(Integer code, String msg, T data) {
        return response(code, true, msg, data);
    }

    /**
     * 成功返回（手动信息）
     * @param msg
     * @return
     * @param <T>
     */
    public static <T> Result<T> successWithMsg(String msg) {
        return response(HttpStatusEnum.HTTP_OK.getCode(), true, msg, null);
    }

    /**
     * 成功返回（手动数据）
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> successWithData(T data) {
        return response(HttpStatusEnum.HTTP_OK.getCode(), true, HttpStatusEnum.HTTP_OK.getMessage(), data);
    }

    /**
     * 失败返回（无参数）
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail() {
        return response(HttpStatusEnum.HTTP_SERVER_ERROR.getCode(), false, HttpStatusEnum.HTTP_SERVER_ERROR.getMessage());
    }

    /**
     * 失败返回（枚举参数）
     * @param httpStatusEnum
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(HttpStatusEnum httpStatusEnum) {
        return response(httpStatusEnum.getCode(), false, httpStatusEnum.getMessage());
    }

    /**
     * 失败返回（手动指定状态码+信息）
     * @param code
     * @param msg
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(Integer code, String msg) {
        return response(code, false, msg);
    }

    /**
     * 失败返回（自动code+手动信息+数据）
     * @param msg
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(String msg, T data) {
        return response(HttpStatusEnum.HTTP_SERVER_ERROR.getCode(), false, msg, data);
    }

    /**
     * 失败返回（全手动）
     * @param code
     * @param msg
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(Integer code, String msg, T data) {
        return response(code, false, msg, data);
    }

    /**
     * 失败返回（手动信息）
     * @param msg
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(String msg) {
        return response(HttpStatusEnum.HTTP_SERVER_ERROR.getCode(), false, msg, null);
    }

    /**
     * 失败返回（手动数据）
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(T data) {
        return response(HttpStatusEnum.HTTP_SERVER_ERROR.getCode(), false, HttpStatusEnum.HTTP_SERVER_ERROR.getMessage(), data);
    }

    /**
     * 全参数方法
     * 静态工厂方法，创建新实例并设置默认值
     * @param code
     * @param status
     * @param msg
     * @param data
     * @return
     * @param <T>
     */
    private static <T> Result<T> response(Integer code, Boolean status, String msg, T data) {
        Result<T> res = new Result<>();
        res.setCode(code);
        res.setStatus(status);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }

    /**
     * 不需要data方法
     * @param code
     * @param status
     * @param msg
     * @return
     * @param <T>
     */
    private static <T> Result<T> response(Integer code, Boolean status, String msg) {
        Result<T> res = new Result<>();
        res.setCode(code);
        res.setStatus(status);
        res.setMsg(msg);
        return res;
    }

}
