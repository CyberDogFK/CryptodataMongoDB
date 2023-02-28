package com.antonp.cryptodatamongodb.service;

import com.antonp.cryptodatamongodb.model.Currency;

public interface HttpRequestService {
    <T> T getCurrencyLastPrice(Currency currency, Currency pricesIn, Class<T> clazz);
}
