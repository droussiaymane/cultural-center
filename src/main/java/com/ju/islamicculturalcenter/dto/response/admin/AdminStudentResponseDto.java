package com.ju.islamicculturalcenter.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ju.islamicculturalcenter.dto.BaseAdminResponse;
import com.ju.islamicculturalcenter.dto.BaseResponseDto;
import com.ju.islamicculturalcenter.dto.response.admin.course.AdminCourseListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminStudentResponseDto extends BaseAdminResponse implements BaseResponseDto {

    private String firstName;

    private String lastName;

    private String userName;

    private String email;

    private String phoneNumber;

    private String facebookUrl;

    private LocalDate dateOfBirth;

    private Integer courseCount;

    private Boolean isVerified;

    private List<AdminCourseListResponseDto> courses;

    private Boolean paid;

    @Builder
    public AdminStudentResponseDto(Long id, Long createdById, Long editedById, Timestamp creationDate, Timestamp editedDate, Boolean isActive, String firstName, String lastName, String userName, String email, String phoneNumber, String facebookUrl, LocalDate dateOfBirth, Integer courseCount, Boolean isVerified, List<AdminCourseListResponseDto> courses) {
        super(id, createdById, editedById, creationDate, editedDate, isActive);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.facebookUrl = facebookUrl;
        this.dateOfBirth = dateOfBirth;
        this.courseCount = courseCount;
        this.isVerified = isVerified;
        this.courses = courses;
    }
}
