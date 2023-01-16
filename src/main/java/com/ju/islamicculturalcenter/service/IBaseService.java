package com.ju.islamicculturalcenter.service;

import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import com.ju.islamicculturalcenter.dto.BaseResponseDto;
import com.ju.islamicculturalcenter.dto.response.ResponseList;
import com.ju.islamicculturalcenter.entity.BaseEntity;

import java.util.List;

public interface IBaseService<T extends BaseEntity, R extends BaseRequestDto, S extends BaseResponseDto, U extends BaseRequestDto> {
    //BASE SERVICE WHICH TAKES 3 TYPES [ENTITY, REQUEST DTO, RESPONSE DTO] FOR ANY IMPLEMENTATION SERVICE

    ResponseList<S> findAllByActive(Integer page, Integer size, Boolean active);

    S findById(Long id, Boolean active);

    S save(R dto);

    S update(Long id, U dto);

    void deleteById(Long id);
}
