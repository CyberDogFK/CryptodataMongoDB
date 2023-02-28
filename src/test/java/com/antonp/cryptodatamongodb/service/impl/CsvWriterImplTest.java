package com.antonp.cryptodatamongodb.service.impl;

import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.model.PricePair;
import com.antonp.cryptodatamongodb.service.PricePairService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CsvWriterImplTest {
    private static final String CSV_REPORT_HEADER = "Cryptocurrency Name, Min Price, Max Price";
    private static final String COMA_SEPARATOR = ",";
    @Mock
    private PricePairService pricePairService;
    @InjectMocks
    private CsvWriterImpl csvWriter;
    @Test
    void prepareCurrencyCsvReportForAllCurrency_Ok() {
        Currency testCurrencyIn = Currency.USD;
        PricePair maxPricePairBtc = new PricePair(Currency.BTC, testCurrencyIn, BigDecimal.valueOf(100));
        PricePair minPricePairBtc = new PricePair(Currency.BTC, testCurrencyIn, BigDecimal.valueOf(10));
        PricePair maxPricePairEth = new PricePair(Currency.ETH, testCurrencyIn, BigDecimal.valueOf(100));
        PricePair minPricePairEth = new PricePair(Currency.ETH, testCurrencyIn, BigDecimal.valueOf(10));
        PricePair maxPricePairXrp = new PricePair(Currency.XRP, testCurrencyIn, BigDecimal.valueOf(100));
        PricePair minPricePairXrp = new PricePair(Currency.XRP, testCurrencyIn, BigDecimal.valueOf(10));
        Mockito.when(pricePairService.getMaxWithName(Currency.BTC)).thenReturn(maxPricePairBtc);
        Mockito.when(pricePairService.getMinWithName(Currency.BTC)).thenReturn(minPricePairBtc);
        Mockito.when(pricePairService.getMaxWithName(Currency.ETH)).thenReturn(maxPricePairEth);
        Mockito.when(pricePairService.getMinWithName(Currency.ETH)).thenReturn(minPricePairEth);
        Mockito.when(pricePairService.getMaxWithName(Currency.XRP)).thenReturn(maxPricePairXrp);
        Mockito.when(pricePairService.getMinWithName(Currency.XRP)).thenReturn(minPricePairXrp);

        String expected = CSV_REPORT_HEADER + System.lineSeparator()
                + Currency.BTC.name() + COMA_SEPARATOR + maxPricePairBtc.getPrice() + COMA_SEPARATOR + minPricePairBtc.getPrice() + System.lineSeparator()
                + Currency.ETH.name() + COMA_SEPARATOR + maxPricePairEth.getPrice() + COMA_SEPARATOR + minPricePairEth.getPrice() + System.lineSeparator()
                + Currency.XRP.name() + COMA_SEPARATOR + maxPricePairXrp.getPrice() + COMA_SEPARATOR + minPricePairXrp.getPrice();
        String actual = csvWriter.prepareCurrencyCsvReport(testCurrencyIn);
        Assertions.assertEquals(expected, actual,
                "You must prepare string with all currency's,"
                        + " max and min price for those currency");
    }


    @Test
    void writeToCsv_shouldWriteCsvFileToDiskAndReturnResource() throws IOException {
        String csvString = "Cryptocurrency Name, Min Price, Max Price\n" +
                "BTC,100.0,50.0\n" +
                "ETH,100.0,50.0\n" +
                "BCH,100.0,50.0\n" +
                "LTC,100.0,50.0\n" +
                "XRP,100.0,50.0\n";

        Resource resource = csvWriter.writeToCsv(csvString);

        Assertions.assertNotNull(resource, "You must return resource");
        Path path = Paths.get(resource.getURI());
        Assertions.assertTrue(Files.exists(path), "You must create file with data");
        List<String> lines = Files.readAllLines(path);
        String expectedCsv = "Cryptocurrency Name, Min Price, Max Price\n" +
                "BTC,100.0,50.0\n" +
                "ETH,100.0,50.0\n" +
                "BCH,100.0,50.0\n" +
                "LTC,100.0,50.0\n" +
                "XRP,100.0,50.0";
        Assertions.assertEquals(expectedCsv, String.join(System.lineSeparator(), lines),
                "You must save data to csv in format " + expectedCsv);
        Files.deleteIfExists(path);
    }
}
