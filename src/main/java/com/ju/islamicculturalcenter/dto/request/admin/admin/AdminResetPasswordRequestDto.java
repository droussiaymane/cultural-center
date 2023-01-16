package com.ju.islamicculturalcenter.dto.request.admin.admin;

import com.ju.islamicculturalcenter.dto.BaseRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminResetPasswordRequestDto implements BaseRequestDto{

    private Long id;

    private String newPassword;

    private String confirmPassword;
}
