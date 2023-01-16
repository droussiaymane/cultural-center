package com.ju.islamicculturalcenter.dto.request.admin.instructor;

import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminResetInstructorPasswordRequestDto implements BaseRequestDto{

    private Long id;

    private String newPassword;

    private String confirmPassword;
}
