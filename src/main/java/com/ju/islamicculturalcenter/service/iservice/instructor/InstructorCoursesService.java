package com.ju.islamicculturalcenter.service.iservice.instructor;

import com.ju.islamicculturalcenter.dto.response.instructor.course.InstructorCourseResponseDto;

import java.util.List;

public interface InstructorCoursesService {

    List<InstructorCourseResponseDto> myCourses();

    List<InstructorCourseResponseDto> searchCourseByName(String name);
}
