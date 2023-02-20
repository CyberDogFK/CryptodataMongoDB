package com.antonp.cryptodatamongodb.dto;

import com.antonp.cryptodatamongodb.model.Currency;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PricePairResponseDto {
    private BigDecimal price;
    private Currency currency1;
    private Currency currency2;
}
