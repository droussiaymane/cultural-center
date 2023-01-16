package com.ju.islamicculturalcenter.repos;

import com.ju.islamicculturalcenter.entity.StudentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends BaseRepo<StudentEntity, Long> {

    @Query("Select a from StudentEntity a where a.user.firstName like %:name%")
    List<StudentEntity> searchByName(String name);
}
