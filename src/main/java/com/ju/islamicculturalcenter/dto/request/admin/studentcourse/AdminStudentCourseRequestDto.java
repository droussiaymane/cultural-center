package com.ju.islamicculturalcenter.dto.request.admin.studentcourse;

import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminStudentCourseRequestDto implements BaseRequestDto {

    private Long studentId;

    private Long courseId;
}
