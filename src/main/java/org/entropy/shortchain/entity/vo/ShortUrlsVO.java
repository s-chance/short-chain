package org.entropy.shortchain.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class ShortUrlsVO {
    @NotBlank
    @URL
    private String shortUrl;
    @NotBlank
    @URL
    private String originalUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String expireTime;
}
