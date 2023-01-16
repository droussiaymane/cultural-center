package com.ju.islamicculturalcenter.dto.request.admin.student;

import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminResetStudentPasswordRequestDto implements BaseRequestDto{

    private Long id;

    private String newPassword;

    private String confirmPassword;
}
