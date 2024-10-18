package org.entropy.shortchain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.entropy.shortchain.mapper")
public class ShortChainApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortChainApplication.class, args);
    }

}
