package com.ju.islamicculturalcenter.entity.enums;

import com.ju.islamicculturalcenter.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mst_roles")
public class UserRoleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "groups")
    private Group groups;

    @Builder
    public UserRoleEntity(Timestamp creationDate, Timestamp updatedDate, Boolean isActive, Boolean isEnabled, Long createdBy, Long updatedBy, Long id, String title, String description, Group groups) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.groups = groups;
    }
}
