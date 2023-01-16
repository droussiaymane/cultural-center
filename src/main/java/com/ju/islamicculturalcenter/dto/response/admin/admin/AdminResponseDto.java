package com.ju.islamicculturalcenter.dto.response.admin.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ju.islamicculturalcenter.dto.BaseAdminResponse;
import com.ju.islamicculturalcenter.dto.BaseResponseDto;
import com.ju.islamicculturalcenter.entity.enums.UserRoleEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminResponseDto extends BaseAdminResponse implements BaseResponseDto {

    private String firstName;

    private String lastName;

    private String userName;

    private String email;

    private String phoneNumber;

    private String facebookUrl;

    private UserRoleEntity role;

    private String address;

    private String iban;

    @Builder
    public AdminResponseDto(Long id, Long createdById, Long updatedById, Timestamp creationDate, Timestamp updateDate, Boolean isActive, String firstName, String lastName, String userName, String email, String phoneNumber, String facebookUrl, UserRoleEntity role, String address, String iban) {
        super(id, createdById, updatedById, creationDate, updateDate, isActive);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.facebookUrl = facebookUrl;
        this.role = role;
        this.address = address;
        this.iban = iban;
    }
}
