package com.ju.islamicculturalcenter.dto.request.instructor.profile;

import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InstructorUpdateProfileDto implements BaseRequestDto {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String facebookUrl;

    private String imageUrl;

    private String cvUrl;
}