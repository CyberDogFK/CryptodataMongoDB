package com.antonp.cryptodatamongodb.controller;

import com.antonp.cryptodatamongodb.dto.PricePairResponseDto;
import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.model.PricePair;
import com.antonp.cryptodatamongodb.service.CsvWriter;
import com.antonp.cryptodatamongodb.service.PricePairService;
import com.antonp.cryptodatamongodb.service.mapper.ResponseDtoMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cryptocurrencies")
@CrossOrigin
public class CryptocurrenciesController {
    private final PricePairService pricePairService;
    private final ResponseDtoMapper<PricePairResponseDto, PricePair> responseDtoMapper;
    private final CsvWriter csvWriter;

    public CryptocurrenciesController(PricePairService pricePairService,
                                      ResponseDtoMapper<PricePairResponseDto,
                                              PricePair> responseDtoMapper,
                                      CsvWriter csvWriter) {
        this.pricePairService = pricePairService;
        this.responseDtoMapper = responseDtoMapper;
        this.csvWriter = csvWriter;
    }

    @GetMapping
    public List<PricePairResponseDto> getAll(@RequestParam Currency name,
                                             @RequestParam(defaultValue = "0") Integer page,
                                             @RequestParam(defaultValue = "10") Integer size,
                                             @RequestParam(defaultValue = "price") String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return pricePairService.getAllByName(name, pageRequest).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/minprice")
    public PricePairResponseDto getWithMinPrice(@RequestParam Currency name) {
        return responseDtoMapper.mapToDto(pricePairService.getMinWithName(name));
    }

    @GetMapping("/maxprice")
    public PricePairResponseDto getWithMaxPrice(@RequestParam Currency name) {
        return responseDtoMapper.mapToDto(pricePairService.getMaxWithName(name));
    }

    @GetMapping("/csv")
    public ResponseEntity<Resource> getCsvReport() {
        Resource hello = csvWriter.writeToCsv(csvWriter.prepareCurrencyCsvReport(Currency.USD));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + hello.getFilename() + "\"").body(hello);
    }
}
