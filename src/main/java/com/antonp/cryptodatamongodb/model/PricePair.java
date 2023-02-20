package com.antonp.cryptodatamongodb.model;

import java.math.BigDecimal;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class PricePair {
    @Id
    private String id;
    private BigDecimal price;
    private Currency currency1;
    private Currency currency2;
}
