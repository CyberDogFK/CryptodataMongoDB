package com.antonp.cryptodatamongodb.repository;

import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.model.PricePair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
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
import java.util.List;

@Testcontainers
@DataMongoTest
public class PricePairRepositoryTest {
    private static final Currency TEST_CURRENCY_1 = Currency.BTC;
    private static final Currency TEST_CURRENCY_2 = Currency.ETH;
    private static final Currency TEST_CURRENCY2_STANDARD = Currency.USD;
    private static final String TEST_ID_1 = "1";
    private static final String TEST_ID_2 = "2";
    private static final String TEST_ID_3 = "3";
    private static final BigDecimal TEST_DECIMAL_MIN = BigDecimal.ZERO;
    private static final BigDecimal TEST_DECIMAL_AVG = BigDecimal.ONE;
    private static final BigDecimal TEST_DECIMAL_MAX = BigDecimal.TEN;
    private PricePair pricePair1MinPrice;
    private PricePair pricePair2DifferentCurrency;
    private PricePair pricePair3MaxPrice;

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
        System.out.println("START");
        mongoDBContainer.start();

        pricePair1MinPrice = new PricePair(TEST_ID_1, TEST_CURRENCY_1, TEST_CURRENCY2_STANDARD, TEST_DECIMAL_MIN);
        pricePair2DifferentCurrency = new PricePair(TEST_ID_2, TEST_CURRENCY_2, TEST_CURRENCY2_STANDARD, TEST_DECIMAL_AVG);
        pricePair3MaxPrice = new PricePair(TEST_ID_3, TEST_CURRENCY_1, TEST_CURRENCY2_STANDARD, TEST_DECIMAL_MAX);

        pricePairRepository.save(pricePair1MinPrice);
        pricePairRepository.save(pricePair2DifferentCurrency);
        pricePairRepository.save(pricePair3MaxPrice);
    }

    @AfterEach
    void afterEach() {
        pricePairRepository.deleteAll();
    }

    @Test
    void findAllByCurrency1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("price"));
        List<PricePair> actual = pricePairRepository.findAllByCurrency1(TEST_CURRENCY_1, pageable);
        Assertions.assertEquals(2, actual.size(),
                "You must return list of pricePairs with same currency1, but you return: " + actual);
        Assertions.assertEquals(pricePair1MinPrice, actual.get(0),
                "You must realise supporting of PageRequest,and return it sorting by price");
        Assertions.assertEquals(pricePair3MaxPrice, actual.get(1),
                "You must realise supporting of PageRequest, and return it sorting by price");
    }

    @Test
    void getPairWithMaxPriceByName() {
        PricePair actual = pricePairRepository.findTopByCurrency1LikeOrderByPriceDesc(TEST_CURRENCY_1);
        Assertions.assertEquals(pricePair3MaxPrice.getPrice(), actual.getPrice(),
                "You must return price pair with asking name, and maximum price value");
    }

    @Test
    void getPairWithMinPriceByName() {
        PricePair actual = pricePairRepository.findTopByCurrency1LikeOrderByPriceAsc(TEST_CURRENCY_1);
        Assertions.assertEquals(pricePair1MinPrice.getPrice(), actual.getPrice(),
                "You must return price pair with asking name, and minimum price value");
    }
}
