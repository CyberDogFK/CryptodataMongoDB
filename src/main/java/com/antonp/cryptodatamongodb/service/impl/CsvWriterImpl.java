package com.antonp.cryptodatamongodb.service.impl;

import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.model.PricePair;
import com.antonp.cryptodatamongodb.service.CsvWriter;
import com.antonp.cryptodatamongodb.service.PricePairService;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
public class CsvWriterImpl implements CsvWriter {
    private static final Path ROOT = Paths.get("uploads");
    private static final String OUTPUT_FILE_NAME = "output.csv";
    private static final String REPORT_FIRST_LINE = "Cryptocurrency Name, Min Price, Max Price";
    private static final String COMA_SEPARATOR = ",";
    private final PricePairService pricePairService;

    public CsvWriterImpl(PricePairService pricePairService) {
        this.pricePairService = pricePairService;
    }

    @Override
    public String prepareCurrencyCsvReport(Currency valuesIn) {
        List<String> currencyList = new ArrayList<>();
        currencyList.add(REPORT_FIRST_LINE);
        for (Currency currency : Currency.values()) {
            if (!currency.equals(valuesIn)) {
                currencyList.add(getCsvLineForCurrency(currency));
            }
        }
        return separateLines(currencyList.toArray(new String[0]));
    }

    @Override
    public Resource writeToCsv(String string) throws RuntimeException {
        File outputCsv = new File(ROOT + "/" + OUTPUT_FILE_NAME);
        if (!outputCsv.exists()) {
            try {
                outputCsv.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Can't write to file" + outputCsv.getAbsolutePath(), e);
            }
        }

        try (PrintWriter printWriter = new PrintWriter(outputCsv)) {
            Files.write(outputCsv.toPath(), string.getBytes());
            printWriter.write(string);
            return new UrlResource(outputCsv.toPath().toUri());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCsvLineForCurrency(Currency currency) {
        PricePair maxWithName = pricePairService.getMaxWithName(currency);
        PricePair minWithName = pricePairService.getMinWithName(currency);
        return convertLineToCsv(new String[]{currency.name(), maxWithName.getPrice().toString(),
                minWithName.getPrice().toString()});
    }

    private String separateLines(String[] lines) {
        return String.join(System.lineSeparator(), lines);
    }

    private String convertLineToCsv(String[] data) {
        return String.join(COMA_SEPARATOR, data);
    }
}
