package com.ju.islamicculturalcenter.dto.request.admin.course;

import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import com.ju.islamicculturalcenter.entity.enums.DaysOfWeek;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCourseRequestDto implements BaseRequestDto {

    private String name;

    private String description;

    private Double duration;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime lectureTime;

    private List<DaysOfWeek> daysOfWeek;

    private String category;

    private Integer maxParticipants;

    private Boolean isPreRecorded = false;

    private Boolean isOnline = false;

    private Boolean isFree = false;

    private Double price;

    private String classroom;

    private String semester;

    private LocalDate year;

    private String teamsLink;

    private LocalDate lastRegDay;
}
