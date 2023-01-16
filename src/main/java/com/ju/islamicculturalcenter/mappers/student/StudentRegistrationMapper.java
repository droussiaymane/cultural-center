package com.ju.islamicculturalcenter.mappers.student;

import com.ju.islamicculturalcenter.dto.request.student.StudentRegistrationRequestDto;
import com.ju.islamicculturalcenter.dto.response.student.StudentRegistrationResponseDto;
import com.ju.islamicculturalcenter.entity.StudentEntity;
import com.ju.islamicculturalcenter.entity.UserEntity;
import com.ju.islamicculturalcenter.entity.enums.Group;
import com.ju.islamicculturalcenter.entity.enums.UserRoleEntity;
import com.ju.islamicculturalcenter.exceptions.NotFoundException;
import com.ju.islamicculturalcenter.mappers.BaseMapper;
import com.ju.islamicculturalcenter.repos.UserRoleRepo;
import org.springframework.data.domain.Example;

import java.sql.Timestamp;

public class StudentRegistrationMapper implements BaseMapper<StudentEntity, StudentRegistrationRequestDto, StudentRegistrationResponseDto> {

    private final UserRoleRepo userRoleRepo;

    public StudentRegistrationMapper(UserRoleRepo userRoleRepo) {
        this.userRoleRepo = userRoleRepo;
    }

    @Override
    public StudentEntity mapDtoToEntity(StudentRegistrationRequestDto studentRegistrationRequestDto) {
        return StudentEntity.builder()
                .user(UserEntity.builder()
                        .firstName(studentRegistrationRequestDto.getFirstName())
                        .lastName(studentRegistrationRequestDto.getLastName())
                        .email(studentRegistrationRequestDto.getEmail())
                        .userName(studentRegistrationRequestDto.getEmail())
                        .phoneNumber(studentRegistrationRequestDto.getPhoneNumber())
                        .facebookUrl(studentRegistrationRequestDto.getFacebookUrl())
                        .createdById(-1L)
                        .updatedById(-1L)
                        .active(true)
                        .creation_Date(new Timestamp(System.currentTimeMillis()))
                        .updateDate(new Timestamp(System.currentTimeMillis()))
                        .role(getStudentRole())
                        .build())
                .createdById(-1L)
                .updatedById(-1L)
                .dateOfBirth(studentRegistrationRequestDto.getDateOfBirth())
                .active(true)
                .creation_Date(new Timestamp(System.currentTimeMillis()))
                .updateDate(new Timestamp(System.currentTimeMillis()))
                .isVerified(true)
                .courseCount(0)
                .build();
    }

    @Override
    public StudentRegistrationResponseDto mapEntityToDto(StudentEntity studentEntity) {

        return StudentRegistrationResponseDto.builder()
                .id(studentEntity.getId())
                .firstName(studentEntity.getUser().getFirstName())
                .lastName(studentEntity.getUser().getLastName())
                .userName(studentEntity.getUser().getUserName())
                .email(studentEntity.getUser().getEmail())
                .phoneNumber(studentEntity.getUser().getPhoneNumber())
                .facebookUrl(studentEntity.getUser().getFacebookUrl())
                .dateOfBirth(studentEntity.getDateOfBirth())
                .courseCount(studentEntity.getCourseCount())
                .build();

    }

    private UserRoleEntity getStudentRole() {
        return userRoleRepo.findOne(Example.of(UserRoleEntity.builder()
                        .groups(Group.STUDENTS).build()))
                .orElseThrow(() -> new NotFoundException("No Role with Student found"));
    }
}
