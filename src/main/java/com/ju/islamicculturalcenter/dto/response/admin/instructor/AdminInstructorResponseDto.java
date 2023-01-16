package com.ju.islamicculturalcenter.dto.response.admin.instructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ju.islamicculturalcenter.dto.BaseAdminResponse;
import com.ju.islamicculturalcenter.dto.BaseResponseDto;
import com.ju.islamicculturalcenter.dto.response.admin.course.AdminCourseListResponseDto;
import com.ju.islamicculturalcenter.entity.PositionEntity;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminInstructorResponseDto extends BaseAdminResponse implements BaseResponseDto {

    private String firstName;

    private String lastName;

    private String userName;

    private String email;

    private String phoneNumber;

    private String facebookUrl;

    private String imageUrl;

    private Boolean isVolunteer;

    private Double salary;

    private String cvUrl;

    private String subNumber;

    private List<AdminCourseListResponseDto> courses;

    @Builder
    public AdminInstructorResponseDto(Long id, Long createdById, Long updatedById, Timestamp creationDate, Timestamp updateDate, Boolean isActive, String firstName, String lastName, String userName, String email, String phoneNumber, String facebookUrl, String imageUrl, Boolean isVolunteer, Double salary, String cvUrl, String subNumber, List<AdminCourseListResponseDto> courses) {
        super(id, createdById, updatedById, creationDate, updateDate, isActive);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.facebookUrl = facebookUrl;
        this.imageUrl = imageUrl;
        this.isVolunteer = isVolunteer;
        this.salary = salary;
        this.cvUrl = cvUrl;
        this.subNumber = subNumber;
        this.courses = courses;
    }
}
