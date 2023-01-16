package com.ju.islamicculturalcenter.dto.request.admin.instructorcourse;


import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminInstructorCourseRequestDto implements BaseRequestDto {

    private Long courseId;

    private Long instructorId;
}
