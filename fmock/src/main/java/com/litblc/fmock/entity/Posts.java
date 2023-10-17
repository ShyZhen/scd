package com.litblc.fmock.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author zhenhuaixiu
 * @Date 2023/10/17 15:32
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@Schema(title = "文章Posts表")
@TableName(value = "posts", autoResultMap = true)
public class Posts {
    // 指定自增策略
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String uuid;

    // 比如没开启自动转驼峰的配置项map-underscore-to-camel-case:false,或者名字不一样可以自行指定
    // @TableField(value = "user_id")
    private Long userId;
    private String title;
    @Schema(title = "摘要，无富文本格式")
    private String summary;
    @Schema(title = "海报图片url")
    private String poster;
    @Schema(title = "json格式delta格式富文本")
    private String content;

    // 创建时自动填充
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // 创建与修改时自动填充
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
