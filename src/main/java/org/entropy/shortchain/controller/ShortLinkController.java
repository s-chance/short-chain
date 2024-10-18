package org.entropy.shortchain.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.entropy.shortchain.annotation.EnableStatistics;
import org.entropy.shortchain.entity.dto.QueryParams;
import org.entropy.shortchain.entity.po.ShortUrls;
import org.entropy.shortchain.entity.vo.ShortUrlsVO;
import org.entropy.shortchain.service.ShortUrlsService;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Validated
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortUrlsService shortUrlsService;

    @PostMapping("/short-chain")
    public ShortUrlsVO getShortLink(@RequestParam @URL String originalLink,
                                    @RequestParam(required = false)
                                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                    LocalDateTime expireTime) {
        return shortUrlsService.getShortLink(originalLink, expireTime);
    }

    @GetMapping("/{key:[a-zA-Z0-9]+}")
    @EnableStatistics
    public ResponseEntity<String> redirectToOriginalLink(@PathVariable("key") String key, HttpServletRequest request) {
        return shortUrlsService.redirectToOriginal(key, request);
    }

    @GetMapping("/query")
    public Page<ShortUrls> queryInfo(@ModelAttribute QueryParams queryParams,
                                     @RequestParam(defaultValue = "1") Long pageNum,
                                     @RequestParam(defaultValue = "10") Long pageSize) {
        return shortUrlsService.queryInfo(queryParams, pageNum, pageSize);
    }

    @GetMapping("/404")
    public String notFound() {
        return "Not Found";
    }


}
