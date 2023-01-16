package com.ju.islamicculturalcenter.mappers.instructor;

import com.ju.islamicculturalcenter.dto.request.instructor.material.InstructorMaterialRequestDto;
import com.ju.islamicculturalcenter.dto.response.instructor.material.InstructorMaterialResponseDto;
import com.ju.islamicculturalcenter.entity.CourseEntity;
import com.ju.islamicculturalcenter.entity.InstructorEntity;
import com.ju.islamicculturalcenter.entity.MaterialEntity;
import com.ju.islamicculturalcenter.entity.UserEntity;
import com.ju.islamicculturalcenter.exceptions.NotFoundException;
import com.ju.islamicculturalcenter.mappers.BaseMapper;
import com.ju.islamicculturalcenter.repos.CourseRepo;
import com.ju.islamicculturalcenter.repos.InstructorRepo;
import com.ju.islamicculturalcenter.service.auth.UserDetailsUtil;
import org.springframework.data.domain.Example;

import java.sql.Timestamp;

public class InstructorMaterialMapper implements BaseMapper<MaterialEntity, InstructorMaterialRequestDto, InstructorMaterialResponseDto> {

    private final InstructorRepo instructorRepo;
    private final CourseRepo courseRepo;

    public InstructorMaterialMapper(InstructorRepo instructorRepo, CourseRepo courseRepo) {
        this.instructorRepo = instructorRepo;
        this.courseRepo = courseRepo;
    }

    @Override
    public MaterialEntity mapDtoToEntity(InstructorMaterialRequestDto instructorMaterialRequestDto) {

        return MaterialEntity.builder()
                .active(true)
                .createdById(UserDetailsUtil.userDetails().getId())
                .updatedById(UserDetailsUtil.userDetails().getId())
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .updateDate(new Timestamp(System.currentTimeMillis()))
                .url(instructorMaterialRequestDto.getUrl())
                .year(instructorMaterialRequestDto.getYear())
                .instructor(getInstructorById(UserDetailsUtil.userDetails().getId()))
                .course(getCourseById(instructorMaterialRequestDto.getCourseId()))
                .build();
    }

    @Override
    public InstructorMaterialResponseDto mapEntityToDto(MaterialEntity materialEntity) {

        return InstructorMaterialResponseDto.builder()
                .id(materialEntity.getId())
                .isActive(materialEntity.getIsActive())
                .courseId(materialEntity.getCourse().getId())
                .url(materialEntity.getUrl())
                .year(materialEntity.getYear())
                .build();
    }

    private InstructorEntity getInstructorById(Long id){
        return instructorRepo.findOne(Example.of(InstructorEntity.builder()
                .active(true)
                .user(UserEntity.builder().id(id).build())
                .build()))
                .orElseThrow(() -> new NotFoundException("No instructor found with ID :" + id));
    }

    private CourseEntity getCourseById(Long id){
        return courseRepo.findOne(Example.of(CourseEntity.builder()
                        .active(true)
                        .id(id)
                        .build()))
                .orElseThrow(() -> new NotFoundException("No course found with ID :" + id));
    }
}
