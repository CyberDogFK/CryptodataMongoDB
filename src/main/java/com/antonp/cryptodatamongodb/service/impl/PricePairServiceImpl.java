package com.antonp.cryptodatamongodb.service.impl;

import com.antonp.cryptodatamongodb.dto.PricePairApiRequestDto;
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
    private HttpRequestService httpRequestService;
    private PricePairRepository pricePairRepository;
    private RequestDtoMapper<PricePairApiRequestDto, PricePair> apiRequestMapper;

    public PricePairServiceImpl(HttpRequestService httpRequestService,
                                PricePairRepository pricePairRepository,
                                RequestDtoMapper<PricePairApiRequestDto,
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
                pricePairs.add(apiRequestMapper.mapToModel(httpRequestService.getCurrencyLastPrice(
                        currency, pricesIn, PricePairApiRequestDto.class)));
            }
        }
        return pricePairs;
    }

    @Scheduled(fixedRate = 10000)
    public List<PricePair> refreshOldestPricePairs() {
        return saveAll(getAllPricePairFromApi(Currency.USD));
    }

    @Override
    public List<PricePair> saveAll(List<PricePair> pricePairs) {
        return pricePairRepository.saveAll(pricePairs);
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
