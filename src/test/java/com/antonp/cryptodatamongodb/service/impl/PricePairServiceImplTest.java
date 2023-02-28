package com.antonp.cryptodatamongodb.service.impl;

import com.antonp.cryptodatamongodb.dto.PricePairApiRequestDto;
import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.model.PricePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PricePairServiceImplTest {
    @Autowired
    private PricePairServiceImpl pricePairService;
    @MockBean
    private HttpRequestServiceImpl httpRequestService;

    @Test
    void getAllPricePairFromApi_Ok() {
        PricePairApiRequestDto apiPairBtcUsd = new PricePairApiRequestDto();
        apiPairBtcUsd.setCurr1(Currency.BTC);
        apiPairBtcUsd.setCurr2(Currency.USD);
        apiPairBtcUsd.setLprice(BigDecimal.valueOf(5000));
        PricePair expectedPairBtcUsd = new PricePair();
        expectedPairBtcUsd.setCurrency1(Currency.BTC);
        expectedPairBtcUsd.setCurrency2(Currency.USD);
        expectedPairBtcUsd.setPrice(BigDecimal.valueOf(5000));

        PricePairApiRequestDto apiPairEthUsd = new PricePairApiRequestDto();
        apiPairEthUsd.setCurr1(Currency.ETH);
        apiPairEthUsd.setCurr2(Currency.USD);
        apiPairEthUsd.setLprice(BigDecimal.valueOf(5000));
        PricePair expectedPairEthUsd = new PricePair();
        expectedPairEthUsd.setCurrency1(Currency.ETH);
        expectedPairEthUsd.setCurrency2(Currency.USD);
        expectedPairEthUsd.setPrice(BigDecimal.valueOf(5000));

        PricePairApiRequestDto apiPairXrpUsd = new PricePairApiRequestDto();
        apiPairXrpUsd.setCurr1(Currency.XRP);
        apiPairXrpUsd.setCurr2(Currency.USD);
        apiPairXrpUsd.setLprice(BigDecimal.valueOf(5000));
        PricePair expectedPairXrpUsd = new PricePair();
        expectedPairXrpUsd.setCurrency1(Currency.ETH);
        expectedPairXrpUsd.setCurrency2(Currency.USD);
        expectedPairXrpUsd.setPrice(BigDecimal.valueOf(5000));


        Mockito.when(httpRequestService.getCurrencyLastPrice(Currency.BTC, Currency.USD, PricePairApiRequestDto.class))
                .thenReturn(apiPairBtcUsd);
        Mockito.when(httpRequestService.getCurrencyLastPrice(Currency.ETH, Currency.USD, PricePairApiRequestDto.class))
                .thenReturn(apiPairEthUsd);
        Mockito.when(httpRequestService.getCurrencyLastPrice(Currency.XRP, Currency.USD, PricePairApiRequestDto.class))
                .thenReturn(apiPairXrpUsd);

        List<PricePair> actual = pricePairService.getAllPricePairFromApi(Currency.USD);

        Assertions.assertEquals(Currency.values().length - 1, actual.size(),
                "You must return all pairs, except pair with asking currency");
        Assertions.assertNotNull(actual.get(actual.indexOf(expectedPairBtcUsd)),
                "You must return pair BTC/USD");
        Assertions.assertNotNull(actual.get(actual.indexOf(expectedPairEthUsd)),
                "You must return pair ETH/USD");
        Assertions.assertNotNull(actual.get(actual.indexOf(expectedPairXrpUsd)),
                "You must return pair XRP/USD");
    }
}
