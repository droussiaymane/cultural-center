package com.ju.islamicculturalcenter.mappers;

import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import com.ju.islamicculturalcenter.dto.BaseResponseDto;
import com.ju.islamicculturalcenter.entity.BaseEntity;

public interface BaseMapper<T extends BaseEntity, R extends BaseRequestDto, S extends BaseResponseDto> {
    //BASE MAPPER WITH 2 METHODS TO USE IN BASE SERVICE IMPL

    T mapDtoToEntity(R r);

    S mapEntityToDto(T t);
}
