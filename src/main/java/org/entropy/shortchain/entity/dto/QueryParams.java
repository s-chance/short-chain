package org.entropy.shortchain.entity.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.entropy.shortchain.annotation.validated.StateValidated;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class QueryParams {

    private String shortCode;

    @URL
    private String originalUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Min(0)
    private Integer minClickCount;

    @Max(Integer.MAX_VALUE)
    private Integer maxClickCount;

    @StateValidated(message = "状态只能为 ACTIVE、DISABLED")
    private String status;
}
