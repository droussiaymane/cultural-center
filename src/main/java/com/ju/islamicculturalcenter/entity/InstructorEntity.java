package com.ju.islamicculturalcenter.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "instructor")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class InstructorEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_volunteer", nullable = false)
    private Boolean isVolunteer;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "cv_url")
    private String cvUrl;

    @Column(name = "sub_number", nullable = false)
    private String subNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    @Cascade(CascadeType.ALL)
    private UserEntity user;

    @Builder
    public InstructorEntity(Timestamp creation_Date, Long createdById, Timestamp updateDate, Long updatedById, Boolean active, Long id, String imageUrl, Boolean isVolunteer, Double salary, String cvUrl, String subNumber, UserEntity user) {
        super(creation_Date, createdById, updateDate, updatedById, active);
        this.id = id;
        this.imageUrl = imageUrl;
        this.isVolunteer = isVolunteer;
        this.salary = salary;
        this.cvUrl = cvUrl;
        this.subNumber = subNumber;
        this.user = user;
    }
}
