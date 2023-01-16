package com.ju.islamicculturalcenter.service.iservice.instructor;

import com.ju.islamicculturalcenter.dto.request.instructor.material.InstructorMaterialRequestDto;
import com.ju.islamicculturalcenter.dto.request.instructor.material.InstructorMaterialUpdateRequestDto;
import com.ju.islamicculturalcenter.dto.response.instructor.material.InstructorMaterialResponseDto;
import com.ju.islamicculturalcenter.entity.MaterialEntity;
import com.ju.islamicculturalcenter.service.IBaseService;

import java.util.List;

public interface MaterialService extends IBaseService<MaterialEntity, InstructorMaterialRequestDto, InstructorMaterialResponseDto, InstructorMaterialUpdateRequestDto> {

    List<InstructorMaterialResponseDto> viewCourseMaterials(Long courseId);
}
