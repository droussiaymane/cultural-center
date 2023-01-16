package com.ju.islamicculturalcenter.dto.response.instructor.material;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ju.islamicculturalcenter.dto.BaseResponseDto;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstructorMaterialResponseDto implements BaseResponseDto {

    private Long id;

    private Boolean isActive;

    private Long courseId;

    private String url;

    private LocalDate year;
}
