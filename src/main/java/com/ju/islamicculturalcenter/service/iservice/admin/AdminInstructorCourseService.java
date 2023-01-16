package com.ju.islamicculturalcenter.service.iservice.admin;

import com.ju.islamicculturalcenter.dto.request.admin.instructorcourse.AdminInstructorCourseRequestDto;

public interface AdminInstructorCourseService {

    void assignInstructorToCourse(AdminInstructorCourseRequestDto requestDto);

    void unAssignInstructorToCourse(AdminInstructorCourseRequestDto requestDto);
}
