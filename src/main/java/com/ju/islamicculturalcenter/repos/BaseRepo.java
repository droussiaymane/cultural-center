package com.ju.islamicculturalcenter.repos;

import com.ju.islamicculturalcenter.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepo<T extends BaseEntity, ID> extends JpaRepository<T, ID> {

    Page<T> findAllByIsActive(Boolean isActive, Pageable pageable);

    Optional<T> findByIdAndIsActive(Long id, Boolean isActive);

    @Modifying
    @Query("UPDATE #{#entityName} t SET t.isActive = false WHERE t.id = :id")
    void softDelete(@Param("id") Long id);
}
