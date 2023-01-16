package com.ju.islamicculturalcenter.dto.response.instructor.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ju.islamicculturalcenter.dto.BaseResponseDto;
import com.ju.islamicculturalcenter.entity.PositionEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstructorResponseDto implements BaseResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    private String email;

    private String phoneNumber;

    private String facebookUrl;

    private PositionEntity position;

    private String imageUrl;

    private Boolean isVolunteer;

    private Double salary;

    private String cvUrl;

    private String subNumber;
}
