package com.ju.islamicculturalcenter.repos;

import com.ju.islamicculturalcenter.entity.AdminEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepo extends BaseRepo<AdminEntity, Long> {

    @Query("Select a from AdminEntity a where a.user.firstName like %:name%")
    List<AdminEntity> searchByName(String name);
}
