package com.antonp.cryptodatamongodb.dto;

import com.antonp.cryptodatamongodb.model.Currency;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PricePairApiResponseDto {
    private BigDecimal lprice;
    private Currency curr1;
    private Currency curr2;
}
