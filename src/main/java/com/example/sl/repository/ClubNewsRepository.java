package com.example.sl.repository;

import com.example.sl.entity.ClubNewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClubNewsRepository extends JpaRepository<ClubNewsEntity, Long> {

    @Modifying
    @Query("update ClubNewsEntity b set b.boardHits = b.boardHits + 1 where b.id = :id")
    void updateHits(@Param("id") Long id);

    Page<ClubNewsEntity> findByBoardTitleContainingOrderByIdDesc(String query, Pageable pageable);

    Page<ClubNewsEntity> findByBoardWriterContainingOrderByIdDesc(String query, Pageable pageable);

    Page<ClubNewsEntity> findByBoardContentsContainingOrderByIdDesc(String query, Pageable pageable);

    Page<ClubNewsEntity> findAllByOrderByIdDesc(Pageable pageable);

    Optional<ClubNewsEntity> findFirstByIdLessThanOrderByIdDesc(Long id);

    Optional<ClubNewsEntity> findFirstByIdGreaterThanOrderByIdAsc(Long id);
}