package com.antonp.cryptodatamongodb.service.mapper.impl;

import com.antonp.cryptodatamongodb.dto.PricePairApiRequestDto;
import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.model.PricePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

public class PricePairApiRequestMapperImplTest {
    private final static Currency CURRENCY_1 = Currency.BTC;
    private final static Currency CURRENCY_2 = Currency.USD;
    private final static BigDecimal PRICE = BigDecimal.TEN;
    private PricePairApiRequestMapperImpl requestMapper;

    @BeforeEach
    void setUp() {
        requestMapper = new PricePairApiRequestMapperImpl();
    }

    @Test
    void correctRequestMapToModel_Ok() {
        PricePairApiRequestDto input = new PricePairApiRequestDto();
        input.setCurr1(CURRENCY_1);
        input.setCurr2(CURRENCY_2);
        input.setLprice(PRICE);
        PricePair expected = new PricePair();
        expected.setCurrency1(CURRENCY_1);
        expected.setCurrency2(CURRENCY_2);
        expected.setPrice(PRICE);

        PricePair actual = requestMapper.mapToModel(input);
        Assertions.assertEquals(expected, actual);
    }
}
