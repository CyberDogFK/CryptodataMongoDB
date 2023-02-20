package com.antonp.cryptodatamongodb.dto;

import com.antonp.cryptodatamongodb.model.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PricePairResponseDto {
    private BigDecimal price;
    private Currency currency1;
    private Currency currency2;
}
