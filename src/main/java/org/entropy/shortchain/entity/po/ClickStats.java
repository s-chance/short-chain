package org.entropy.shortchain.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.entropy.shortchain.handler.MyInetTypeHandler;

import java.time.OffsetDateTime;

@Data
@Builder
@TableName("click_stats")
public class ClickStats {

    @TableId(type = IdType.AUTO)
    private Integer clickId;

    @NotBlank
    private String shortCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private OffsetDateTime clickTime;

    @TableField(typeHandler = MyInetTypeHandler.class)
    private String userIp;

    private String userAgent;

    private String referrer;
}
