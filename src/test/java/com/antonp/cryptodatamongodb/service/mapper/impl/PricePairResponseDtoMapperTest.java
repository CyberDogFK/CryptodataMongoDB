package com.antonp.cryptodatamongodb.service.mapper.impl;

import com.antonp.cryptodatamongodb.dto.PricePairResponseDto;
import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.model.PricePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

public class PricePairResponseDtoMapperTest {
    private static final Currency CURRENCY_1 = Currency.BTC;
    private static final Currency CURRENCY_2 = Currency.USD;
    private static final BigDecimal PRICE = BigDecimal.TEN;

    private PricePairResponseDtoMapper responseDtoMapper;

    @BeforeEach
    void setUp() {
        responseDtoMapper = new PricePairResponseDtoMapper();
    }

    @Test
    void mapToDto_Ok() {
        PricePair pricePair = new PricePair();
        pricePair.setCurrency1(CURRENCY_1);
        pricePair.setCurrency2(CURRENCY_2);
        pricePair.setPrice(PRICE);
        PricePairResponseDto expectedDto = new PricePairResponseDto();
        expectedDto.setCurrency1(CURRENCY_1);
        expectedDto.setCurrency2(CURRENCY_2);
        expectedDto.setPrice(PRICE);
        PricePairResponseDto actual = responseDtoMapper.mapToDto(pricePair);
        Assertions.assertEquals(expectedDto, actual);
    }
}
