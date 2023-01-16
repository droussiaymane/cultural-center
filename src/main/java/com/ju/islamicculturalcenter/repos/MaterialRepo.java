package com.ju.islamicculturalcenter.repos;

import com.ju.islamicculturalcenter.entity.MaterialEntity;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepo extends BaseRepo<MaterialEntity, Long> {
    List<MaterialEntity> findAllByCourseId(Long courseId);
    MaterialEntity findByIdAndCourseId(Long materialId, Long courseId);
}
