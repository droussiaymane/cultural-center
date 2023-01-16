package com.ju.islamicculturalcenter.service.iservice.admin;

import com.ju.islamicculturalcenter.dto.request.admin.instructor.AdminInstructorRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.instructor.AdminInstructorUpdateRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.instructor.AdminResetInstructorPasswordRequestDto;
import com.ju.islamicculturalcenter.dto.response.admin.instructor.AdminInstructorResponseDto;
import com.ju.islamicculturalcenter.entity.InstructorEntity;
import com.ju.islamicculturalcenter.service.IBaseService;

import java.util.List;

public interface AdminInstructorService extends IBaseService<InstructorEntity, AdminInstructorRequestDto, AdminInstructorResponseDto, AdminInstructorUpdateRequestDto> {

    void resetPassword(AdminResetInstructorPasswordRequestDto requestDto);

    List<AdminInstructorResponseDto> searchInstructorByName(String name);
}
