package com.ju.islamicculturalcenter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseAdminResponse { // HAS ALL THE ATTRIBUTES COMMON FOR ALL ADMIN RESPONSES

    private Long id;

    private Long createdById;

    private Long updatedById;

    private Timestamp creationDate;

    private Timestamp updateDate;

    private Boolean isActive;
}
