package com.ju.islamicculturalcenter.repos;

import com.ju.islamicculturalcenter.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends BaseRepo<UserEntity, Long>{

    Optional<UserEntity> findByIsActiveAndEmail(Boolean isActive, String email);

}
