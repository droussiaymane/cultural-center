package com.ju.islamicculturalcenter.dto.response.admin.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ju.islamicculturalcenter.dto.BaseResponseDto;
import com.ju.islamicculturalcenter.entity.enums.DaysOfWeek;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminCourseListResponseDto implements BaseResponseDto {

    private Long id;

    private Long name;

    private List<DaysOfWeek> daysOfWeek;

    private String category;

    private String semester;
}
