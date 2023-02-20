package com.antonp.cryptodatamongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptodataMongoDbApplication {
    public static void main(String[] args) {
        SpringApplication.run(CryptodataMongoDbApplication.class, args);
    }
}
