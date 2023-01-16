package com.ju.islamicculturalcenter.dto.request.admin.studentcourse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminPaidStudentCourseRequest {

    private Long studentId;

    private Long courseId;
}
