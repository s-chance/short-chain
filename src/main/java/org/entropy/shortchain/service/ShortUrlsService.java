package org.entropy.shortchain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.entropy.shortchain.entity.dto.QueryParams;
import org.entropy.shortchain.util.Base62UrlShortener;
import org.entropy.shortchain.constant.LinkState;
import org.entropy.shortchain.entity.po.ClickStats;
import org.entropy.shortchain.entity.po.ShortUrls;
import org.entropy.shortchain.entity.vo.ShortUrlsVO;
import org.entropy.shortchain.mapper.ShortUrlsMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ShortUrlsService extends ServiceImpl<ShortUrlsMapper, ShortUrls> {
    private final ClickStatsService clickStatsService;

    @Value("${short-link.domain}")
    private String shortLinkDomain;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ShortUrlsVO getShortLink(String originalLink, LocalDateTime expireTime) {

        ShortUrlsVO shortUrlsVO = new ShortUrlsVO();
        shortUrlsVO.setOriginalUrl(originalLink);

        ShortUrls res = getOne(new LambdaQueryWrapper<ShortUrls>()
                .eq(ShortUrls::getOriginalUrl, originalLink));

        if (res != null && res.getShortCode() != null) {
            shortUrlsVO.setShortUrl(shortLinkDomain + res.getShortCode());
            if (expireTime != null && expireTime.isAfter(LocalDateTime.now())) {
                LambdaUpdateWrapper<ShortUrls> wrapper = new LambdaUpdateWrapper<ShortUrls>()
                        .eq(ShortUrls::getOriginalUrl, originalLink)
                        .set(ShortUrls::getExpireTime, expireTime);
                update(wrapper);
                String formattedExpireTime = expireTime.format(formatter);
                shortUrlsVO.setExpireTime(formattedExpireTime);
            } else {
                lambdaUpdate()
                        .eq(ShortUrls::getOriginalUrl, originalLink)
                        .set(ShortUrls::getExpireTime, null)
                        .update();
            }
            return shortUrlsVO;
        }

        if (expireTime != null && expireTime.isAfter(LocalDateTime.now())) {
            String formattedExpireTime = expireTime.format(formatter);
            shortUrlsVO.setShortUrl(shortLinkDomain + getShorCode(originalLink, expireTime));
            shortUrlsVO.setExpireTime(formattedExpireTime);
        } else {
            shortUrlsVO.setShortUrl(shortLinkDomain + getShorCode(originalLink));
        }
        return shortUrlsVO;
    }

    private String getShorCode(String originalLink) {
        String shortCode = Base62UrlShortener.encode(originalLink);
        ShortUrls shortUrls = ShortUrls.builder()
                .shortCode(shortCode)
                .originalUrl(originalLink)
                .status(LinkState.ACTIVE.name())
                .build();
        save(shortUrls);
        return shortCode;
    }

    private String getShorCode(String originalLink, LocalDateTime expireTime) {

        String shortCode = Base62UrlShortener.encode(originalLink);

        ShortUrls shortUrls = ShortUrls.builder()
                .shortCode(shortCode)
                .originalUrl(originalLink)
                .expireTime(expireTime.atOffset(ZoneOffset.ofHours(8)))
                .status(LinkState.ACTIVE.name())
                .build();
        save(shortUrls);
        return shortCode;
    }

    public ResponseEntity<String> redirectToOriginal(String key, HttpServletRequest request) {
        String originalLink = getOriginalLink(key);
        if (!originalLink.equalsIgnoreCase("/404")) {
            ClickStats clickStats = ClickStats.builder()
                    .shortCode(key)
                    .clickTime(OffsetDateTime.now(ZoneOffset.ofHours(8)))
                    .userIp(request.getRemoteAddr())
                    .userAgent(request.getHeader("User-Agent"))
                    .referrer(request.getHeader("referer"))
                    .build();
            clickStatsService.save(clickStats);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalLink));
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).headers(headers).build();
    }

    private String getOriginalLink(String shortCode) {
        LambdaQueryWrapper<ShortUrls> wrapper = new LambdaQueryWrapper<ShortUrls>()
                .eq(ShortUrls::getShortCode, shortCode);
        ShortUrls shortUrls = getOne(wrapper);
        if (shortUrls == null) {
            return "/404";
        }

        if (shortUrls.getExpireTime() != null
                && shortUrls.getExpireTime().isBefore(OffsetDateTime.now().withOffsetSameLocal(ZoneOffset.UTC))) {
            return "/404";
        }

        return shortUrls.getOriginalUrl();
    }

    public void incrClickCount(String shortCode) {
        lambdaUpdate().eq(ShortUrls::getShortCode, shortCode)
                .setIncrBy(ShortUrls::getClickCount, 1L)
                .update();
    }

    public Page<ShortUrls> queryInfo(QueryParams queryParams, Long pageNum, Long pageSize) {
        Page<ShortUrls> shortUrlsPage = new Page<>(pageNum, pageSize);
        return lambdaQuery()
                .like(StringUtils.isNotBlank(queryParams.getShortCode()), ShortUrls::getShortCode, queryParams.getShortCode())
                .like(StringUtils.isNotBlank(queryParams.getOriginalUrl()), ShortUrls::getOriginalUrl, queryParams.getOriginalUrl())
                .ge(queryParams.getStartTime() != null, ShortUrls::getCreateTime, queryParams.getStartTime())
                .le(queryParams.getEndTime() != null, ShortUrls::getCreateTime, queryParams.getEndTime())
                .ge(queryParams.getMinClickCount() != null, ShortUrls::getClickCount, queryParams.getMinClickCount())
                .le(queryParams.getMaxClickCount() != null, ShortUrls::getClickCount, queryParams.getMaxClickCount())
                .eq(StringUtils.isNotBlank(queryParams.getStatus()), ShortUrls::getStatus, queryParams.getStatus())
                .page(shortUrlsPage);
    }
}
