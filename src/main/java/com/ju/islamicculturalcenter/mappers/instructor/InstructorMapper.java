package com.ju.islamicculturalcenter.mappers.instructor;

import com.ju.islamicculturalcenter.dto.request.instructor.profile.InstructorRequestDto;
import com.ju.islamicculturalcenter.dto.response.instructor.profile.InstructorResponseDto;
import com.ju.islamicculturalcenter.entity.InstructorEntity;
import com.ju.islamicculturalcenter.entity.UserEntity;
import com.ju.islamicculturalcenter.entity.enums.Group;
import com.ju.islamicculturalcenter.entity.enums.UserRoleEntity;
import com.ju.islamicculturalcenter.exceptions.NotFoundException;
import com.ju.islamicculturalcenter.mappers.BaseMapper;
import com.ju.islamicculturalcenter.repos.UserRoleRepo;
import com.ju.islamicculturalcenter.service.auth.UserDetailsUtil;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;


public class InstructorMapper implements BaseMapper<InstructorEntity, InstructorRequestDto, InstructorResponseDto> {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRoleRepo userRoleRepo;

    public InstructorMapper(BCryptPasswordEncoder bCryptPasswordEncoder, UserRoleRepo userRoleRepo) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRoleRepo = userRoleRepo;
    }

    public InstructorEntity mapDtoToEntity(InstructorRequestDto requestDto) {
        return InstructorEntity.builder()
                .user(UserEntity.builder()
                        .active(true)
                        .creation_Date(new Timestamp(System.currentTimeMillis()))
                        .updateDate(new Timestamp(System.currentTimeMillis()))
                        .createdById(UserDetailsUtil.userDetails().getId())
                        .updatedById(UserDetailsUtil.userDetails().getId())
                        .firstName(requestDto.getFirstName())
                        .lastName(requestDto.getLastName())
                        .email(requestDto.getEmail())
                        .phoneNumber(requestDto.getPhoneNumber())
                        .facebookUrl(requestDto.getFacebookUrl())
                        .userName(requestDto.getEmail())
                        .password(bCryptPasswordEncoder.encode(requestDto.getPassword()))
                        .role(getInstructorRole())
                        .build())
                .imageUrl(requestDto.getImageUrl())
                .isVolunteer(requestDto.getIsVolunteer())
                .cvUrl(requestDto.getCvUrl())
                .active(true)
                .creation_Date(new Timestamp(System.currentTimeMillis()))
                .updateDate(new Timestamp(System.currentTimeMillis()))
                .createdById(UserDetailsUtil.userDetails().getId())
                .updatedById(UserDetailsUtil.userDetails().getId())
                .build();
    }


    public InstructorResponseDto mapEntityToDto(InstructorEntity instructorEntity) {
        return InstructorResponseDto.builder()
                .id(instructorEntity.getId())
                .firstName(instructorEntity.getUser().getFirstName())
                .lastName(instructorEntity.getUser().getLastName())
                .email(instructorEntity.getUser().getEmail())
                .userName(instructorEntity.getUser().getEmail())
                .phoneNumber(instructorEntity.getUser().getPhoneNumber())
                .facebookUrl(instructorEntity.getUser().getFacebookUrl())
                .imageUrl(instructorEntity.getImageUrl())
                .isVolunteer(instructorEntity.getIsVolunteer())
                .salary(instructorEntity.getSalary())
                .cvUrl(instructorEntity.getCvUrl())
                .subNumber(instructorEntity.getSubNumber())
                .build();
    }

    private UserRoleEntity getInstructorRole() {
        return userRoleRepo.findOne(Example.of(UserRoleEntity.builder()
                        .groups(Group.INSTRUCTORS).build()))
                .orElseThrow(() -> new NotFoundException("No Role with Instructor found"));
    }
}
