package org.entropy.shortchain.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.entropy.shortchain.annotation.validated.StateValidated;
import org.hibernate.validator.constraints.URL;

import java.time.OffsetDateTime;

@Data
@Builder
@TableName("short_urls")
public class ShortUrls {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotBlank
    private String shortCode;

    @NotBlank
    @URL
    private String originalUrl;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private OffsetDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private OffsetDateTime expireTime;

    private Integer clickCount;

    @StateValidated(message = "状态只能为 ACTIVE、DISABLED")
    private String status;
}
