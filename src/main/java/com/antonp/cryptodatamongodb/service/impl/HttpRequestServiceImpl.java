package com.antonp.cryptodatamongodb.service.impl;

import com.antonp.cryptodatamongodb.lib.HttpClientRequestException;
import com.antonp.cryptodatamongodb.service.HttpRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

@Service
public class HttpRequestServiceImpl implements HttpRequestService {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T get(String url, Class<T> clazz) {
        HttpGet get = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(get)) {
            return objectMapper.readValue(response.getEntity().getContent(), clazz);
        } catch (IOException e) {
            throw new HttpClientRequestException("Can't fetch url " + url, e);
        }
    }
}
