package com.ju.islamicculturalcenter.mappers.admin;

import com.ju.islamicculturalcenter.dto.request.admin.student.AdminStudentRequestDto;
import com.ju.islamicculturalcenter.dto.response.admin.AdminStudentResponseDto;
import com.ju.islamicculturalcenter.entity.StudentEntity;
import com.ju.islamicculturalcenter.entity.UserEntity;
import com.ju.islamicculturalcenter.entity.enums.Group;
import com.ju.islamicculturalcenter.entity.enums.UserRoleEntity;
import com.ju.islamicculturalcenter.exceptions.NotFoundException;
import com.ju.islamicculturalcenter.mappers.BaseMapper;
import com.ju.islamicculturalcenter.repos.UserRoleRepo;
import com.ju.islamicculturalcenter.service.auth.UserDetailsUtil;
import org.springframework.data.domain.Example;

import java.sql.Timestamp;

public class AdminStudentMapper implements BaseMapper<StudentEntity, AdminStudentRequestDto, AdminStudentResponseDto> {

    private final UserRoleRepo userRoleRepo;

    public AdminStudentMapper(UserRoleRepo userRoleRepo) {
        this.userRoleRepo = userRoleRepo;
    }

    public StudentEntity mapDtoToEntity(AdminStudentRequestDto requestDto) {
        return StudentEntity.builder()
                .user(UserEntity.builder()
                        .firstName(requestDto.getFirstName())
                        .lastName(requestDto.getLastName())
                        .email(requestDto.getEmail())
                        .userName(requestDto.getEmail())
                        .phoneNumber(requestDto.getPhoneNumber())
                        .facebookUrl(requestDto.getFacebookUrl())
                        .createdById(UserDetailsUtil.userDetails().getId())
                        .updatedById(UserDetailsUtil.userDetails().getId())
                        .active(true)
                        .creation_Date(new Timestamp(System.currentTimeMillis()))
                        .updateDate(new Timestamp(System.currentTimeMillis()))
                        .role(getStudentRole())
                        .build())
                .createdById(UserDetailsUtil.userDetails().getId())
                .updatedById(UserDetailsUtil.userDetails().getId())
                .dateOfBirth(requestDto.getDateOfBirth())
                .active(true)
                .creation_Date(new Timestamp(System.currentTimeMillis()))
                .updateDate(new Timestamp(System.currentTimeMillis()))
                .isVerified(true)
                .courseCount(0)
                .build();
    }

    public AdminStudentResponseDto mapEntityToDto(StudentEntity studentEntity) {

        return AdminStudentResponseDto.builder()
                .id(studentEntity.getId())
                .creationDate(studentEntity.getCreationDate())
                .createdById(studentEntity.getCreatedById())
                .editedDate(studentEntity.getUpdateDate())
                .editedById(studentEntity.getUpdatedById())
                .isActive(studentEntity.getIsActive())
                .firstName(studentEntity.getUser().getFirstName())
                .lastName(studentEntity.getUser().getLastName())
                .userName(studentEntity.getUser().getUserName())
                .email(studentEntity.getUser().getEmail())
                .phoneNumber(studentEntity.getUser().getPhoneNumber())
                .facebookUrl(studentEntity.getUser().getFacebookUrl())
                .dateOfBirth(studentEntity.getDateOfBirth())
                .courseCount(studentEntity.getCourseCount())
                .isVerified(studentEntity.getIsVerified())
                .build();
    }

    private UserRoleEntity getStudentRole() {
        return userRoleRepo.findOne(Example.of(UserRoleEntity.builder()
                        .groups(Group.STUDENTS).build()))
                .orElseThrow(() -> new NotFoundException("No Role with Student found"));
    }
}
