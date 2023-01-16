package com.ju.islamicculturalcenter.dto.request.admin.student;

import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUpdateStudentRequestDto implements BaseRequestDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String facebookUrl;

    private LocalDate dateOfBirth;
}
