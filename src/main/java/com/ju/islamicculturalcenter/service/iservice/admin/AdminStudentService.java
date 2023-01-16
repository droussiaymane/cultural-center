package com.ju.islamicculturalcenter.service.iservice.admin;

import com.ju.islamicculturalcenter.dto.request.admin.student.AdminResetStudentPasswordRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.student.AdminStudentRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.student.AdminUpdateStudentRequestDto;
import com.ju.islamicculturalcenter.dto.response.admin.AdminStudentResponseDto;
import com.ju.islamicculturalcenter.entity.StudentEntity;
import com.ju.islamicculturalcenter.service.IBaseService;

import java.util.List;

public interface AdminStudentService extends IBaseService<StudentEntity, AdminStudentRequestDto, AdminStudentResponseDto, AdminUpdateStudentRequestDto> {

    void resetPassword(AdminResetStudentPasswordRequestDto requestDto);

    List<AdminStudentResponseDto> searchStudentByName(String name);
}

