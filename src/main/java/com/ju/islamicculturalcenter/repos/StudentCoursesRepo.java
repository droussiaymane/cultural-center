package com.ju.islamicculturalcenter.repos;

import com.ju.islamicculturalcenter.entity.StudentCoursesEntity;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCoursesRepo extends BaseRepo<StudentCoursesEntity, Long> {

    List<StudentCoursesEntity> findAllByCourseId(Long courseId);
}
