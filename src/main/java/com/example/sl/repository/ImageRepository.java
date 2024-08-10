package com.example.sl.repository;

import com.example.sl.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    @Query(value = "SELECT * FROM image_entity ORDER BY id LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<ImageEntity> findAllWithPagination(@Param("offset") int offset, @Param("limit") int limit);

    List<ImageEntity> findByNameContaining(String name);
}
