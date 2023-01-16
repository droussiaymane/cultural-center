package com.ju.islamicculturalcenter.dto.request.student;

import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentUpdateProfileRequest implements BaseRequestDto {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String facebookUrl;

    private Date dateOfBirth;
}
