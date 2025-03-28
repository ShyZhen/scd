package com.litblc.shiro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Schema(title = "用户表", description = "用户的数据库表结构")
@TableName(value = "users", autoResultMap = true)
public class Users implements Serializable {

    @Serial  // 在Java 14后，引入了 @Serial 注解，以便更好地标识 serialVersionUID 字段。提高代码的可读性和可维护性，明确表示该字段是用于序列化的。
    private static final long serialVersionUID = -7391002919175007314L; // 显式声明UID。用于序列化和反序列化的唯一标识符。它用于确保在反序列化时，加载的类与序列化时的类是兼容的。

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "由后端自动生成")
    private String uuid;
    private String name;

    private String email;
    private String mobile;

    @JsonIgnore  // 过滤敏感字段,不会出现在 JSON 输出中
    private String password;
    private String gender;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  // 实体类尽量不做特殊处理和时区转换，在dto中转换
    private LocalDateTime createdAt;
}
