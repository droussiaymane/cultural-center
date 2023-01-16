package com.ju.islamicculturalcenter.dto.request.instructor.student;

import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorStudentCourseRequestDto implements BaseRequestDto {

    private Long studentId;

    private Long courseId;

    private Boolean paid;
}
