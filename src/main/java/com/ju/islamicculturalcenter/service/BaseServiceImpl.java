package com.ju.islamicculturalcenter.service;

import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import com.ju.islamicculturalcenter.dto.BaseResponseDto;
import com.ju.islamicculturalcenter.dto.response.ResponseList;
import com.ju.islamicculturalcenter.entity.BaseEntity;
import com.ju.islamicculturalcenter.exceptions.NotFoundException;
import com.ju.islamicculturalcenter.exceptions.ValidationException;
import com.ju.islamicculturalcenter.mappers.BaseMapper;
import com.ju.islamicculturalcenter.repos.BaseRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.join;

public abstract class BaseServiceImpl<T extends BaseEntity, R extends BaseRequestDto, S extends BaseResponseDto, U extends BaseRequestDto> implements IBaseService<T,R,S,U>{

    @Override
    public ResponseList<S> findAllByActive(Integer page, Integer size, Boolean active) {

        PageRequest pageRequest = PageRequest.of(page + 1, size);
        Pageable pageable = pageRequest.previous();

        Page<T> allByIsActive = getRepo().findAllByIsActive(active, pageable);
        List<S> list = allByIsActive.stream() //stream is like for loop but is faster having parallel loops and return the element in collection
                .map(getMapper()::mapEntityToDto)// map method loops through every element of the return type
                .collect(Collectors.toList());

        return ResponseList.<S>builder()
                .data(list)
                .totalElements(allByIsActive.getTotalElements())
                .build();
    }

    @Override
    public S findById(Long id, Boolean active) {
        return getMapper().mapEntityToDto(getRepo().findByIdAndIsActive(id,active)
                .orElseThrow(() -> new NotFoundException("No Entity Found with ID: " + id)));
    }

    @Override
    public S save(R dto) {
        preAddValidation(dto);
        preSave(dto);
        T entity = getRepo().save(getMapper().mapDtoToEntity(dto));
        postSave(dto, entity);
        return getMapper().mapEntityToDto(entity);
    }

    @Override
    public S update(Long id, U dto) {
        T t = getRepo().findByIdAndIsActive(id, true)
                .orElseThrow(() -> new NotFoundException("No Entity Found with ID: " + id));

        preUpdate(t, dto);
        preUpdateValidation(dto);

        T save = getRepo().save(updateEntity(t, dto));

        return getMapper().mapEntityToDto(save);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        cascadeDelete(id);
        getRepo().softDelete(id);
    }


    public abstract T updateEntity(T entity, U dto);

    public abstract BaseRepo<T,Long> getRepo(); // to be implemented by every class that extends this class [return the repo of the entity the service implements]

    public abstract BaseMapper<T,R,S> getMapper(); // to be implemented by every class that extends this class [return the mapper of the dto's the service implements]

    public abstract void preAddValidation(R dto);

    public abstract void preUpdateValidation(U dto);

    public void preSave(R requestDto){

    }

    public void postSave(R requestDto, T entity){

    }

    public void preUpdate(T entity, U updateDto){

    }

    public void cascadeDelete(Long id) {

    }

    protected void validate(List<String> violations) {
        if (!violations.isEmpty()) {
            throw new ValidationException(join(",", violations));
        }
    }
}
