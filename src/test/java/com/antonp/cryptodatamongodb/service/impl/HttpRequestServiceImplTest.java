package com.antonp.cryptodatamongodb.service.impl;

import com.antonp.cryptodatamongodb.dto.PricePairApiRequestDto;
import com.antonp.cryptodatamongodb.lib.ApiErrorException;
import com.antonp.cryptodatamongodb.model.Currency;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class HttpRequestServiceImplTest {
    private static final String DECLARED_FIELD = "httpClient";
    private static final String API_URL = "https://cex.io/api/last_price";
    private static final String API_RESPONSE_PRICE_FIELD = "lprice";
    private static final String API_RESPONSE_CURRENCY_1_FIELD = "curr1";
    private static final String API_RESPONSE_CURRENCY_2_FILED = "curr2";
    private static final String URL_SEPARATOR = "/";
    @InjectMocks
    private HttpRequestServiceImpl httpRequestService;
    @Mock
    private static CloseableHttpClient httpClient;

    @BeforeEach
    void setUp() {
        try {
            Field field = httpRequestService.getClass()
                    .getDeclaredField(DECLARED_FIELD);
            field.setAccessible(true);
            field.set(httpRequestService, httpClient);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException("Can't mock http client, you must have for "
                    + "CloseableHttpClient field with name " + DECLARED_FIELD, e);
        }
    }

    @Test
    void getCurrencyLastPriceFromApi_Ok() {
        Currency currencyFor = Currency.BTC;
        Currency currencyIn = Currency.USD;
        BigDecimal price = BigDecimal.valueOf(46106.66);
        String url = String.join(URL_SEPARATOR, API_URL, currencyFor.name(), currencyIn.name());
        try {
            CloseableHttpResponse response = mockResponse(prepareHttpGetBody(price, currencyFor, currencyIn));
            Mockito.when(httpClient.execute(ArgumentMatchers.argThat((ArgumentMatcher<HttpGet>) argument -> {
                String uri = argument.getURI().toString();
                Assertions.assertEquals(url, uri, "You must prepare correct http url " + url);
                return uri.contains(url);
            }))).thenReturn(response);
        } catch (Exception e) {
            throw new RuntimeException("Can't mock response for test: ", e);
        }

        PricePairApiRequestDto actual = httpRequestService
                .getCurrencyLastPrice(currencyFor, currencyIn, PricePairApiRequestDto.class);

        Assertions.assertEquals(price, actual.getLprice(),
                "You should read and save value of price");
        Assertions.assertEquals(currencyFor, actual.getCurr1(),
                "You should read and save value of currency1");
        Assertions.assertEquals(currencyIn, actual.getCurr2(),
                "You should read and save value of currency2");
    }

    @Test
    void incorrectPairApiError_Exception() {
        Currency currencyFor = Currency.USD;
        Currency currencyIn = Currency.USD;
        String url = String.join(URL_SEPARATOR, API_URL, currencyFor.name(), currencyIn.name());
        String errorApiMessage = "{\"error\":\"Invalid Symbols Pair\"}";
        String expectedExceptionMessage = "Can't fetch url: " + url
                + ", response: " + errorApiMessage;
        try {
            CloseableHttpResponse response = mockResponse(errorApiMessage);
            Mockito.when(httpClient.execute(ArgumentMatchers.argThat((ArgumentMatcher<HttpGet>) argument -> {
                String uri = argument.getURI().toString();
                return uri.contains(url);
            }))).thenReturn(response);
        } catch (Exception e) {
            throw new RuntimeException("Can't mock response", e);
        }

        ApiErrorException actualException = Assertions.assertThrows(ApiErrorException.class, () ->
                        httpRequestService.getCurrencyLastPrice(currencyFor, currencyIn, PricePairApiRequestDto.class),
                "When you get error from api, you must throw exception");
        Assertions.assertEquals(expectedExceptionMessage, actualException.getMessage());
    }


    private String prepareHttpGetBody(BigDecimal price, Currency currencyFor, Currency currencyIn) {
        return "{\"" + API_RESPONSE_PRICE_FIELD + "\":\"" + price + "\","
                + "\"" + API_RESPONSE_CURRENCY_1_FIELD + "\":\"" + currencyFor.name() + "\""
                + ",\"" + API_RESPONSE_CURRENCY_2_FILED + "\":\"" + currencyIn.name() + "\"}";
    }

    private CloseableHttpResponse mockResponse(String body) throws IOException {
        CloseableHttpResponse response = Mockito.mock(CloseableHttpResponse.class);
        HttpEntity entity = Mockito.mock(HttpEntity.class);
        Mockito.when(entity.getContent()).thenReturn(toInputStream(body));
        Mockito.when(response.getEntity()).thenReturn(entity);
        return response;
    }

    private InputStream toInputStream(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }
}
