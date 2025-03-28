package com.litblc.shiro.dto.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class UserResponseDto implements Serializable {

        @Serial
        private static final long serialVersionUID = -6721476310291714096L;

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

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")  // 实体类尽量不做特殊处理和时区转换，在dto中进行转换 （GMT+8东八区，建议使用Asia/Shanghai）
        private ZonedDateTime createdAt;
}

