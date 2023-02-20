package com.antonp.cryptodatamongodb.service;

public interface HttpRequestService {
    <T> T get(String url, Class<T> clazz);
}
