package com.litblc.shiro.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(title = "dto注册请求")
public class RegisterRequestDto {
    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "密码需至少8位，包含字母和数字")
    private String password;

    //@NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不合法")
    private String email;

    @NotBlank(message = "手机号不能为空")
    private String mobile;

    private String gender;

    private LocalDateTime createdAt;
}
