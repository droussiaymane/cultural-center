package com.ju.islamicculturalcenter.mappers.admin;

import com.ju.islamicculturalcenter.dto.request.admin.admin.AdminRequestDto;
import com.ju.islamicculturalcenter.dto.response.admin.admin.AdminResponseDto;
import com.ju.islamicculturalcenter.entity.AdminEntity;
import com.ju.islamicculturalcenter.entity.UserEntity;
import com.ju.islamicculturalcenter.entity.enums.Group;
import com.ju.islamicculturalcenter.entity.enums.UserRoleEntity;
import com.ju.islamicculturalcenter.exceptions.NotFoundException;
import com.ju.islamicculturalcenter.mappers.BaseMapper;
import com.ju.islamicculturalcenter.repos.UserRoleRepo;
import com.ju.islamicculturalcenter.service.auth.UserDetailsUtil;
import org.springframework.data.domain.Example;

import java.sql.Timestamp;

public class AdminMapper implements BaseMapper<AdminEntity, AdminRequestDto, AdminResponseDto> {

    private final UserRoleRepo userRoleRepo;

    public AdminMapper(UserRoleRepo userRoleRepo) {
        this.userRoleRepo = userRoleRepo;
    }

    @Override
    public AdminEntity mapDtoToEntity(AdminRequestDto adminRequestDto) {
        return AdminEntity.builder()
                .creation_Date(new Timestamp(System.currentTimeMillis()))
                .createdById(UserDetailsUtil.userDetails().getId())
                .updateDate(new Timestamp(System.currentTimeMillis()))
                .updatedById(UserDetailsUtil.userDetails().getId())
                .active(true)
                .user(UserEntity.builder()
                        .creation_Date(new Timestamp(System.currentTimeMillis()))
                        .createdById(UserDetailsUtil.userDetails().getId())
                        .updateDate(new Timestamp(System.currentTimeMillis()))
                        .updatedById(UserDetailsUtil.userDetails().getId())
                        .active(true)
                        .firstName(adminRequestDto.getFirstName())
                        .lastName(adminRequestDto.getLastName())
                        .email(adminRequestDto.getEmail())
                        .userName(adminRequestDto.getEmail())
                        .phoneNumber(adminRequestDto.getPhoneNumber())
                        .facebookUrl(adminRequestDto.getFacebookUrl())
                        .role(getAdminRole(adminRequestDto.getRoleId()))
                        .build())
                .address(adminRequestDto.getAddress())
                .iban(adminRequestDto.getIban())
                .build();
    }

    @Override
    public AdminResponseDto mapEntityToDto(AdminEntity adminEntity) {
        return AdminResponseDto.builder()
                .id(adminEntity.getId())
                .creationDate(adminEntity.getCreationDate())
                .createdById(adminEntity.getCreatedById())
                .updateDate(adminEntity.getUpdateDate())
                .updatedById(adminEntity.getUpdatedById())
                .isActive(adminEntity.getIsActive())
                .firstName(adminEntity.getUser().getFirstName())
                .lastName(adminEntity.getUser().getLastName())
                .email(adminEntity.getUser().getEmail())
                .userName(adminEntity.getUser().getUserName())
                .phoneNumber(adminEntity.getUser().getPhoneNumber())
                .facebookUrl(adminEntity.getUser().getFacebookUrl())
                .role(adminEntity.getUser().getRole())
                .address(adminEntity.getAddress())
                .iban(adminEntity.getIban())
                .build();
    }

    private UserRoleEntity getAdminRole(Long id) {
        return userRoleRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("No Role found with ID :{" + id + "}"));
    }
}
