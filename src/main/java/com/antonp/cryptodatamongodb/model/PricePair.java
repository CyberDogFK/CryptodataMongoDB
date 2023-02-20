package com.antonp.cryptodatamongodb.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;

@Data
public class PricePair {
    @Id
    private String id;
    private BigDecimal price;
    private Currency currency1;
    private Currency currency2;
}
