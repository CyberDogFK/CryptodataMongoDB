package com.antonp.cryptodatamongodb.service.mapper.impl;

import com.antonp.cryptodatamongodb.service.mapper.RequestDtoMapper;
import com.antonp.cryptodatamongodb.model.PricePair;
import com.antonp.cryptodatamongodb.dto.PricePairApiResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class PricePairApiRequestMapperImpl implements RequestDtoMapper<PricePairApiResponseDto, PricePair> {
    @Override
    public PricePair mapToModel(PricePairApiResponseDto apiResponse) {
        PricePair pricePair = new PricePair();
        pricePair.setPrice(apiResponse.getLprice());
        pricePair.setCurrency1(apiResponse.getCurr1());
        pricePair.setCurrency2(apiResponse.getCurr2());
        return pricePair;
    }
}
