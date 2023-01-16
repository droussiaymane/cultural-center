package com.ju.islamicculturalcenter.repos;

import com.ju.islamicculturalcenter.entity.CourseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends BaseRepo<CourseEntity, Long> {

    @Query("Select a from CourseEntity a where a.name like %:name%")
    List<CourseEntity> searchByName(String name);

    Optional<CourseEntity> findByName(String name);
}
