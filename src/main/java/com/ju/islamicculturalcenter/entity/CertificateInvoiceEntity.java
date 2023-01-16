package com.ju.islamicculturalcenter.entity;

import com.ju.islamicculturalcenter.entity.enums.CertificateInvoiceTypes;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "certificate_Invoice")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class CertificateInvoiceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "type")
    private CertificateInvoiceTypes type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private InstructorEntity instructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private CourseEntity course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentEntity student;

    @Builder
    public CertificateInvoiceEntity(Timestamp creation_Date, Long createdById, Timestamp updateDate, Long updatedById, Boolean active, Long id, String link, CertificateInvoiceTypes type, InstructorEntity instructor, CourseEntity course, StudentEntity student) {
        super(creation_Date, createdById, updateDate, updatedById, active);
        this.id = id;
        this.link = link;
        this.type = type;
        this.instructor = instructor;
        this.course = course;
        this.student = student;
    }
}
