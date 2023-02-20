package com.antonp.cryptodatamongodb.service;

import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.model.PricePair;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface PricePairService {
    List<PricePair> getAllPricePairFromApi();
    PricePair save(PricePair pair);
    List<PricePair> saveAll(List<PricePair> pricePairs);
    List<PricePair> refreshOldestPricePairs();
    List<PricePair> getAll();
    List<PricePair> getAll(PageRequest pageRequest);
    List<PricePair> getAllByName(Currency name, PageRequest pageRequest);
    PricePair getMinWithName(Currency name);
    PricePair getMaxWithName(Currency name);
}
