package org.entropy.shortchain;

import org.entropy.shortchain.service.ShortUrlsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@SpringBootTest
class ShortChainApplicationTests {

    @Autowired
    private ShortUrlsService shortUrlsService;

    @Test
    void contextLoads() {
    }

}
