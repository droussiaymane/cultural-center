package com.ju.islamicculturalcenter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;


@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class BaseEntity {

    @Column(name = "creation_date", nullable = false)
    protected Timestamp creationDate;

    @Column(name = "created_by_id", nullable = false)
    protected Long createdById;

    @Column(name = "update_date", nullable = false)
    protected Timestamp updateDate;

    @Column(name = "updated_by_id", nullable = false)
    protected Long updatedById;

    @Column(name = "is_active", nullable = false)
    protected Boolean isActive;

    public BaseEntity(Timestamp creation_Date, Long createdById, Timestamp updateDate, Long updatedById, Boolean active) {
        this.creationDate = creation_Date;
        this.createdById = createdById;
        this.updateDate = updateDate;
        this.updatedById = updatedById;
        this.isActive = active;
    }
}
