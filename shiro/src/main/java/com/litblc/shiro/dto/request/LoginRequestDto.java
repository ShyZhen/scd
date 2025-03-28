package com.litblc.shiro.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(title = "dto登录请求")
public class LoginRequestDto {
    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotBlank(message = "密码不能为空")
    private String password;
}
