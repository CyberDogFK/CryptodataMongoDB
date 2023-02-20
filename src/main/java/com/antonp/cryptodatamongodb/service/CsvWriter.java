package com.antonp.cryptodatamongodb.service;

import com.antonp.cryptodatamongodb.model.Currency;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

public interface CsvWriter {
    String prepareCurrencyCsvReport();
    Resource writeToCsv(String string);
}
