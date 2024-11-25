package org.entropy.shortchain.listener;

import lombok.RequiredArgsConstructor;
import org.entropy.shortchain.event.ClickShortLinkEvent;
import org.entropy.shortchain.service.ShortUrlsService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClickEventListener {

    private final ShortUrlsService shortUrlsService;

    @Async("clickStatsExecutor")
    @EventListener(ClickShortLinkEvent.class)
    public void onClick(ClickShortLinkEvent event) {
        String shortCode = event.getShortCode();
        shortUrlsService.incrClickCount(shortCode);
    }
}
