package com.ju.islamicculturalcenter.dto.response.instructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ju.islamicculturalcenter.dto.BaseResponseDto;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstructorCertificateResponseDto implements BaseResponseDto{

    private Long id;

    private String link;

    private Timestamp creationDate;

    private Long courseId;

    private String courseName;

    private Long instructorId;

    private String instructorName;

    private Long studentId;

    private String studentName;
}
