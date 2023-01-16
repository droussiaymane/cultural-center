package com.ju.islamicculturalcenter.repos;

import com.ju.islamicculturalcenter.entity.InstructorCoursesEntity;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorCoursesRepo extends BaseRepo<InstructorCoursesEntity, Long> {

    List<InstructorCoursesEntity> findAllByInstructor_User_IdAndCourse_NameLike(Long instructorId, String name);
}
