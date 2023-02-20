package com.antonp.cryptodatamongodb.service;

import com.antonp.cryptodatamongodb.model.Currency;
import org.springframework.core.io.Resource;

public interface CsvWriter {
    String prepareCurrencyCsvReport(Currency valuesIn);

    Resource writeToCsv(String string);
}
