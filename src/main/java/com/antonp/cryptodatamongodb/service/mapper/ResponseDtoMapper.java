package com.antonp.cryptodatamongodb.service.mapper;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T model);
}
