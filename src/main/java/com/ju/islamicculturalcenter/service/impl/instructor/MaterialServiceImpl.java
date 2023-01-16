package com.ju.islamicculturalcenter.service.impl.instructor;

import com.ju.islamicculturalcenter.dto.request.instructor.material.InstructorMaterialRequestDto;
import com.ju.islamicculturalcenter.dto.request.instructor.material.InstructorMaterialUpdateRequestDto;
import com.ju.islamicculturalcenter.dto.response.instructor.material.InstructorMaterialResponseDto;
import com.ju.islamicculturalcenter.entity.CourseEntity;
import com.ju.islamicculturalcenter.entity.InstructorEntity;
import com.ju.islamicculturalcenter.entity.MaterialEntity;
import com.ju.islamicculturalcenter.entity.UserEntity;
import com.ju.islamicculturalcenter.exceptions.NotFoundException;
import com.ju.islamicculturalcenter.mappers.instructor.InstructorMaterialMapper;
import com.ju.islamicculturalcenter.repos.CourseRepo;
import com.ju.islamicculturalcenter.repos.InstructorRepo;
import com.ju.islamicculturalcenter.repos.MaterialRepo;
import com.ju.islamicculturalcenter.service.BaseServiceImpl;
import com.ju.islamicculturalcenter.service.auth.UserDetailsUtil;
import com.ju.islamicculturalcenter.service.helper.CompositeValidator;
import com.ju.islamicculturalcenter.service.iservice.instructor.MaterialService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class MaterialServiceImpl extends BaseServiceImpl<MaterialEntity, InstructorMaterialRequestDto, InstructorMaterialResponseDto, InstructorMaterialUpdateRequestDto> implements MaterialService {

    private final MaterialRepo materialRepo;
    private final CourseRepo courseRepo;
    private final InstructorRepo instructorRepo;
    private final InstructorMaterialMapper instructorMaterialMapper;

    public MaterialServiceImpl(MaterialRepo materialRepo, CourseRepo courseRepo, InstructorRepo instructorRepo) {
        this.materialRepo = materialRepo;
        this.courseRepo = courseRepo;
        this.instructorRepo = instructorRepo;
        this.instructorMaterialMapper = new InstructorMaterialMapper(instructorRepo, courseRepo);
    }

    @Override
    public List<InstructorMaterialResponseDto> viewCourseMaterials(Long courseId) {
        return materialRepo.findAll(Example.of(MaterialEntity.builder()
                        .instructor(InstructorEntity.builder().user(UserEntity.builder().id(UserDetailsUtil.userDetails().getId()).build()).build())
                        .course(CourseEntity.builder().id(courseId).build())
                        .build()))
                .stream().map(instructorMaterialMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        MaterialEntity material = materialRepo.findOne(Example.of(MaterialEntity.builder().active(true).id(id).build()))
                .orElseThrow(() -> new NotFoundException("No Material found with ID :" + id));
        materialRepo.delete(material);
    }

    @Override
    public MaterialEntity updateEntity(MaterialEntity entity, InstructorMaterialUpdateRequestDto dto) {

        entity.setUrl(dto.getUrl());
        entity.setYear(dto.getYear());

        entity.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        return entity;
    }

    @Override
    public MaterialRepo getRepo() {
        return materialRepo;
    }

    @Override
    public InstructorMaterialMapper getMapper() {
        return instructorMaterialMapper;
    }

    @Override
    public void preAddValidation(InstructorMaterialRequestDto dto) {

        List<String> violations = new CompositeValidator<InstructorMaterialRequestDto, String>()
                .addValidator(r -> nonNull(r.getCourseId()), "courseId cannot be null")
                .addValidator(r -> CompositeValidator.hasValue(r.getUrl()), "url cannot be null")
                .addValidator(r -> nonNull(r.getYear()), "year cannot be null")
                .addValidator(r -> isNull(r.getYear()) || !r.getYear().with(lastDayOfYear()).isBefore(LocalDate.now()), "year cannot be in the past")
                .validate(dto);
        validate(violations);
    }
    @Override
    public void preUpdateValidation(InstructorMaterialUpdateRequestDto dto) {

        List<String> violations = new CompositeValidator<InstructorMaterialUpdateRequestDto, String>()
                .addValidator(r -> CompositeValidator.hasValue(r.getUrl()), "url cannot be null")
                .addValidator(r -> nonNull(r.getYear()), "year cannot be null")
                .addValidator(r -> isNull(r.getYear()) || !r.getYear().with(lastDayOfYear()).isBefore(LocalDate.now()), "year cannot be in the past")
                .validate(dto);
        validate(violations);
    }
}
