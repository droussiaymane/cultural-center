package com.ju.islamicculturalcenter.repos;

import com.ju.islamicculturalcenter.entity.InstructorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepo extends BaseRepo<InstructorEntity, Long> {

    InstructorEntity findInstructorEntityById(Long id);

    @Query("Select a from InstructorEntity a where a.user.firstName like %:name%")
    List<InstructorEntity> searchByName(String name);
}
