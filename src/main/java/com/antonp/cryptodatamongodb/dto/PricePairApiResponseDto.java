package com.antonp.cryptodatamongodb.dto;

import com.antonp.cryptodatamongodb.model.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PricePairApiResponseDto {
    private BigDecimal lprice;
    private Currency curr1;
    private Currency curr2;
}
