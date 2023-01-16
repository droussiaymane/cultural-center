package com.ju.islamicculturalcenter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ju.islamicculturalcenter.entity.enums.DaysOfWeek;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Table(name = "course")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class CourseEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "duration", nullable = false)
    private Double duration;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "lecture_time")
    private LocalTime lectureTime;

    @Column(name = "days_of_week")
    @ElementCollection
    private List<DaysOfWeek> daysOfWeek;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "max_participants", nullable = false)
    private Integer maxParticipants;

    @Column(name = "is_prerecorded")
    private Boolean isPreRecorded;

    @Column(name = "is_online")
    private Boolean isOnline;

    @Column(name = "is_free", nullable = false)
    private Boolean isFree;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "classroom")
    private String classroom;

    @Column(name = "semester")
    private String semester;

    @Column(name = "year", nullable = false)
    @JsonFormat(pattern = "yyyy")
    private LocalDate year;

    @Column(name = "teams_link")
    private String teamsLink;

    @Column(name = "last_reg_day", nullable = false)
    private LocalDate lastRegDay;

    @Builder
    public CourseEntity(Timestamp creationDate, Long createdById, Timestamp updateDate, Long updatedById, Boolean active, Long id, String name, String description, Double duration, LocalDate startDate, LocalDate endDate, LocalTime lectureTime, List<DaysOfWeek> daysOfWeek, String category, Integer maxParticipants, Boolean isPreRecorded, Boolean isOnline, Boolean isFree, Double price, String classroom, String semester, LocalDate year, String teamsLink, LocalDate lastRegDay) {
        super(creationDate, createdById, updateDate, updatedById, active);
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lectureTime = lectureTime;
        this.daysOfWeek = daysOfWeek;
        this.category = category;
        this.maxParticipants = maxParticipants;
        this.isPreRecorded = isPreRecorded;
        this.isOnline = isOnline;
        this.isFree = isFree;
        this.price = price;
        this.classroom = classroom;
        this.semester = semester;
        this.year = year;
        this.teamsLink = teamsLink;
        this.lastRegDay = lastRegDay;
    }
}
