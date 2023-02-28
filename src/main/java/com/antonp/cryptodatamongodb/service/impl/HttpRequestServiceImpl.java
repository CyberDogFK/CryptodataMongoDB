package com.antonp.cryptodatamongodb.service.impl;

import com.antonp.cryptodatamongodb.lib.ApiErrorException;
import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.service.HttpRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class HttpRequestServiceImpl implements HttpRequestService {
    private static final String URL = "https://cex.io/api/last_price";
    private static final String URL_SEPARATOR = "/";
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getCurrencyLastPrice(Currency currency, Currency pricesIn, Class<T> clazz) {
        String url = createUrl(currency, pricesIn);
        HttpGet get = new HttpGet(url);
        String responseString = "";
        try (CloseableHttpResponse response = httpClient.execute(get)) {
            responseString = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(responseString, clazz);
        } catch (IOException e) {
            throw new ApiErrorException("Can't fetch url: " + url
                    + ", response: " + responseString, e);
        }
    }

    private String createUrl(Currency currencyFor, Currency currencyIn) {
        return String.join(URL_SEPARATOR, URL, currencyFor.name(), currencyIn.name());
    }
}
