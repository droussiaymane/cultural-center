package com.ju.islamicculturalcenter.service.iservice.admin;


import com.ju.islamicculturalcenter.dto.request.admin.course.AdminCourseRequestDto;
import com.ju.islamicculturalcenter.dto.request.admin.course.AdminUpdateCourseRequestDto;
import com.ju.islamicculturalcenter.dto.response.admin.course.AdminCourseResponseDto;
import com.ju.islamicculturalcenter.entity.CourseEntity;
import com.ju.islamicculturalcenter.service.IBaseService;

import java.util.List;

public interface AdminCourseService extends IBaseService<CourseEntity, AdminCourseRequestDto, AdminCourseResponseDto, AdminUpdateCourseRequestDto>{

    List<AdminCourseResponseDto> searchCourseByName(String name);
}
