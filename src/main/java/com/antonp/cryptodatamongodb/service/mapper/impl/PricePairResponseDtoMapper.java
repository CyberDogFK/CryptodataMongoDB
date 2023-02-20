package com.antonp.cryptodatamongodb.service.mapper.impl;

import com.antonp.cryptodatamongodb.dto.PricePairResponseDto;
import com.antonp.cryptodatamongodb.service.mapper.ResponseDtoMapper;
import com.antonp.cryptodatamongodb.model.PricePair;
import org.springframework.stereotype.Service;

@Service
public class PricePairResponseDtoMapper implements ResponseDtoMapper<PricePairResponseDto, PricePair> {
    @Override
    public PricePairResponseDto mapToDto(PricePair model) {
        PricePairResponseDto pricePairResponseDto = new PricePairResponseDto();
        pricePairResponseDto.setPrice(model.getPrice());
        pricePairResponseDto.setCurrency1(model.getCurrency1());
        pricePairResponseDto.setCurrency2(model.getCurrency2());
        return pricePairResponseDto;
    }
}
