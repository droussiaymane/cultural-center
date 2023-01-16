package com.ju.islamicculturalcenter.dto.request.instructor.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstructorUpdatePassword {

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;
}
