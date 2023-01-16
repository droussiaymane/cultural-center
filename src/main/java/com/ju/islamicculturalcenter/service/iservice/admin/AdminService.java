package com.ju.islamicculturalcenter.service.iservice.admin;

import com.ju.islamicculturalcenter.dto.request.admin.admin.AdminRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.admin.AdminResetPasswordRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.admin.AdminUpdatePasswordRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.admin.AdminUpdateRequestDto;
import com.ju.islamicculturalcenter.dto.response.admin.admin.AdminResponseDto;
import com.ju.islamicculturalcenter.entity.AdminEntity;
import com.ju.islamicculturalcenter.service.IBaseService;

import java.util.List;

public interface AdminService extends IBaseService<AdminEntity, AdminRequestDto, AdminResponseDto, AdminUpdateRequestDto> {

    void resetPassword(AdminResetPasswordRequestDto requestDto);

    void updatePassword(AdminUpdatePasswordRequestDto requestDto);

    AdminResponseDto updateOwnProfile(AdminUpdateRequestDto requestDto);

    List<AdminResponseDto> searchAdminByName(String name1);
}
