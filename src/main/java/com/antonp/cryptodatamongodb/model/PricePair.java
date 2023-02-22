package com.antonp.cryptodatamongodb.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricePair {
    @Id
    private String id;
    private Currency currency1;
    private Currency currency2;
    private BigDecimal price;

    public PricePair(Currency currency1, Currency currency2, BigDecimal price) {
        this.currency1 = currency1;
        this.currency2 = currency2;
        this.price = price;
    }
}
