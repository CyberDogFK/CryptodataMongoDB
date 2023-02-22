package com.antonp.cryptodatamongodb.repository;

import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.model.PricePair;
import com.antonp.cryptodatamongodb.repository.PricePairRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Testcontainers
@DataMongoTest(excludeAutoConfiguration = MongoDataAutoConfiguration.class)
public class PricePairRepositoryTest {
    private static final Currency TEST_CURRENCY_1 = Currency.BTC;
    private static final Currency TEST_CURRENCY_2 = Currency.USD;
    private static Pageable pageable = PageRequest.of(0, 1, Sort.by("price"));

    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private PricePairRepository pricePairRepository;

    @BeforeEach
    public void setUp() {
        mongoDBContainer.start();
    }

    @AfterEach
    void afterEach() {
        pricePairRepository.deleteAll();
    }

    @Test
    void findMaxByName() {
        pricePairRepository.save(new PricePair("1", TEST_CURRENCY_1, TEST_CURRENCY_2, BigDecimal.valueOf(5000)));
        List<PricePair> actual = pricePairRepository.findAllByCurrency1(TEST_CURRENCY_1, pageable);
        System.out.println(actual);
        Assertions.assertEquals(1, actual.size());
    }
}
