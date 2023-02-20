package com.antonp.cryptodatamongodb.service.impl;

import com.antonp.cryptodatamongodb.service.mapper.RequestDtoMapper;
import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.model.PricePair;
import com.antonp.cryptodatamongodb.dto.PricePairApiResponseDto;
import com.antonp.cryptodatamongodb.repository.PricePairRepository;
import com.antonp.cryptodatamongodb.service.HttpRequestService;
import com.antonp.cryptodatamongodb.service.PricePairService;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PricePairServiceImpl implements PricePairService {
    private static final String URL = "https://cex.io/api/last_price/";
    private static final String URL_SEPARATOR = "/";
    private static final String URL_BTC_USD = URL + Currency.BTC + URL_SEPARATOR + Currency.USD;
    private static final String URL_ETH_USD = URL + Currency.ETH + URL_SEPARATOR + Currency.USD;
    private static final String URL_XRP_USD = URL + Currency.XRP + URL_SEPARATOR + Currency.USD;


    private HttpRequestService httpRequestService;
    private PricePairRepository pricePairRepository;
    private RequestDtoMapper<PricePairApiResponseDto, PricePair> apiRequestMapper;

    public PricePairServiceImpl(HttpRequestService httpRequestService,
                                PricePairRepository pricePairRepository,
                                RequestDtoMapper<PricePairApiResponseDto, PricePair> apiRequestMapper) {
        this.httpRequestService = httpRequestService;
        this.pricePairRepository = pricePairRepository;
        this.apiRequestMapper = apiRequestMapper;
    }

    @Override
    public List<PricePair> getAllPricePairFromApi() {
        List<PricePair> pricePairs = new ArrayList<>();
        pricePairs.add(apiRequestMapper.mapToModel(httpRequestService.get(
                URL_BTC_USD, PricePairApiResponseDto.class)));
        pricePairs.add(apiRequestMapper.mapToModel(httpRequestService.get(
                URL_ETH_USD, PricePairApiResponseDto.class)));
        pricePairs.add(apiRequestMapper.mapToModel(httpRequestService.get(
                URL_XRP_USD, PricePairApiResponseDto.class)));
        return pricePairs;
    }


    @Override
    public PricePair save(PricePair pair) {
        return pricePairRepository.save(pair);
    }

    @Override
    public List<PricePair> saveAll(List<PricePair> pricePairs) {
        return pricePairRepository.saveAll(pricePairs);
    }

    //@Scheduled(fixedRate = 10000)
    @Override
    public List<PricePair> refreshOldestPricePairs() {
        List<PricePair> pricePairs = saveAll(getAllPricePairFromApi());
        System.out.println(pricePairs);
        return pricePairs;
    }

    @Override
    public List<PricePair> getAll() {
        return pricePairRepository.findAll();
    }

    @Override
    public List<PricePair> getAll(PageRequest pageRequest) {
        return pricePairRepository.findAll(pageRequest).toList();
    }

    @Override
    public List<PricePair> getAllByName(Currency name, PageRequest pageRequest) {
        return pricePairRepository.findAllByCurrency1(name, pageRequest);
    }

    @Override
    public PricePair getMinWithName(Currency name) {
        return pricePairRepository.findTopByCurrency1LikeOrderByPriceAsc(name);
    }

    @Override
    public PricePair getMaxWithName(Currency name) {
        return pricePairRepository.findTopByCurrency1LikeOrderByPriceDesc(name);
    }
}
