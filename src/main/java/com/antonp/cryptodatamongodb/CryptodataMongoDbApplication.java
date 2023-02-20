package com.antonp.cryptodatamongodb;

import com.antonp.cryptodatamongodb.service.HttpRequestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptodataMongoDbApplication {

    private static HttpRequestService service;

    public CryptodataMongoDbApplication(HttpRequestService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CryptodataMongoDbApplication.class, args);
    }

}
