package com.ju.islamicculturalcenter.dto.request.admin.instructor;

import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminInstructorRequestDto implements BaseRequestDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String facebookUrl;

    private String password;

    private String imageUrl;

    private Boolean isVolunteer;

    private Double salary;

    private String cvUrl;
}
