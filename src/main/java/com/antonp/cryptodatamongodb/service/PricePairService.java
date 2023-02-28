package com.antonp.cryptodatamongodb.service;

import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.model.PricePair;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface PricePairService {
    List<PricePair> getAllPricePairFromApi(Currency pricesIn);

    List<PricePair> getAllByName(Currency name, PageRequest pageRequest);

    List<PricePair> saveAll(List<PricePair> pricePairs);

    PricePair getMinWithName(Currency name);

    PricePair getMaxWithName(Currency name);
}
