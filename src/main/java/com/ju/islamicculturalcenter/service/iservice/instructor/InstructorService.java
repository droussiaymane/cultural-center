package com.ju.islamicculturalcenter.service.iservice.instructor;

import com.ju.islamicculturalcenter.dto.request.instructor.profile.InstructorRequestDto;
import com.ju.islamicculturalcenter.dto.request.instructor.profile.InstructorUpdateProfileDto;
import com.ju.islamicculturalcenter.dto.request.instructor.profile.InstructorUpdatePassword;
import com.ju.islamicculturalcenter.dto.response.instructor.profile.InstructorResponseDto;
import com.ju.islamicculturalcenter.entity.InstructorEntity;
import com.ju.islamicculturalcenter.service.IBaseService;

public interface InstructorService extends IBaseService<InstructorEntity, InstructorRequestDto, InstructorResponseDto, InstructorUpdateProfileDto> {

    InstructorResponseDto viewProfile();

    void updatePassword(InstructorUpdatePassword request);
}
