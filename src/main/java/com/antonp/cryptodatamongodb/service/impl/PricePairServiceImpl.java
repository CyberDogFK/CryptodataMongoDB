package com.antonp.cryptodatamongodb.service.impl;

import com.antonp.cryptodatamongodb.dto.PricePairApiResponseDto;
import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.model.PricePair;
import com.antonp.cryptodatamongodb.repository.PricePairRepository;
import com.antonp.cryptodatamongodb.service.HttpRequestService;
import com.antonp.cryptodatamongodb.service.PricePairService;
import com.antonp.cryptodatamongodb.service.mapper.RequestDtoMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PricePairServiceImpl implements PricePairService {
    private static final String URL = "https://cex.io/api/last_price/";
    private static final String URL_SEPARATOR = "/";
    private HttpRequestService httpRequestService;
    private PricePairRepository pricePairRepository;
    private RequestDtoMapper<PricePairApiResponseDto, PricePair> apiRequestMapper;

    public PricePairServiceImpl(HttpRequestService httpRequestService,
                                PricePairRepository pricePairRepository,
                                RequestDtoMapper<PricePairApiResponseDto,
                                        PricePair> apiRequestMapper) {
        this.httpRequestService = httpRequestService;
        this.pricePairRepository = pricePairRepository;
        this.apiRequestMapper = apiRequestMapper;
    }

    @Override
    public List<PricePair> getAllPricePairFromApi(Currency pricesIn) {
        List<PricePair> pricePairs = new ArrayList<>();
        for (Currency currency : Currency.values()) {
            if (!currency.equals(pricesIn)) {
                pricePairs.add(apiRequestMapper.mapToModel(httpRequestService.get(
                        createUrl(currency, pricesIn), PricePairApiResponseDto.class
                )));
            }
        }
        return pricePairs;
    }

    @Override
    public List<PricePair> saveAll(List<PricePair> pricePairs) {
        return pricePairRepository.saveAll(pricePairs);
    }

    @Scheduled(fixedRate = 10000)
    public List<PricePair> refreshOldestPricePairs() {
        return saveAll(getAllPricePairFromApi(Currency.USD));
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

    private String createUrl(Currency currencyFor, Currency currencyIn) {
        return String.join(URL_SEPARATOR, URL, currencyFor.name(), currencyIn.name());
    }
}
