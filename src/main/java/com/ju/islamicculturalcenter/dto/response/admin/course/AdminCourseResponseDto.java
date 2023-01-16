package com.ju.islamicculturalcenter.dto.response.admin.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ju.islamicculturalcenter.dto.BaseAdminResponse;
import com.ju.islamicculturalcenter.dto.BaseResponseDto;
import com.ju.islamicculturalcenter.dto.response.admin.AdminStudentListResponseDto;
import com.ju.islamicculturalcenter.dto.response.admin.AdminStudentResponseDto;
import com.ju.islamicculturalcenter.dto.response.admin.instructor.AdminInstructorListResponseDto;
import com.ju.islamicculturalcenter.dto.response.admin.instructor.AdminInstructorResponseDto;
import com.ju.islamicculturalcenter.entity.enums.DaysOfWeek;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminCourseResponseDto extends BaseAdminResponse implements BaseResponseDto {

    private String name;

    private String description;

    private Double duration;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime lectureTime;

    private List<DaysOfWeek> daysOfWeek;

    private String category;

    private Integer maxParticipants;

    private Boolean isPreRecorded;

    private Boolean isOnline;

    private Boolean isFree;

    private Double price;

    private String classroom;

    private String semester;

    @JsonFormat(pattern = "yyyy")
    private LocalDate year;

    private String teamsLink;

    private LocalDate lastRegDay;

    private List<AdminStudentResponseDto> students;

    private List<AdminInstructorResponseDto> instructors;

    @Builder
    public AdminCourseResponseDto(Long id, Long createdById, Long updatedById, Timestamp creationDate, Timestamp updateDate, Boolean isActive, String name, String description, Double duration, LocalDate startDate, LocalDate endDate, LocalTime lectureTime, List<DaysOfWeek> daysOfWeek, String category, Integer maxParticipants, Boolean isPreRecorded, Boolean isOnline, Boolean isFree, Double price, String classroom, String semester, LocalDate year, String teamsLink, LocalDate lastRegDay, List<AdminStudentResponseDto> students, List<AdminInstructorResponseDto> instructors) {
        super(id, createdById, updatedById, creationDate, updateDate, isActive);
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lectureTime = lectureTime;
        this.daysOfWeek = daysOfWeek;
        this.category = category;
        this.maxParticipants = maxParticipants;
        this.isPreRecorded = isPreRecorded;
        this.isOnline = isOnline;
        this.isFree = isFree;
        this.price = price;
        this.classroom = classroom;
        this.semester = semester;
        this.year = year;
        this.teamsLink = teamsLink;
        this.lastRegDay = lastRegDay;
        this.students = students;
        this.instructors = instructors;
    }
}
