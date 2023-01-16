package com.ju.islamicculturalcenter.dto.request.instructor.material;

import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorMaterialUpdateRequestDto implements BaseRequestDto {

    private String url;

    private LocalDate year;
}
