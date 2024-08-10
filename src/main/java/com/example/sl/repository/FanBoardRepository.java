package com.example.sl.repository;

import com.example.sl.entity.FanBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FanBoardRepository extends JpaRepository<FanBoardEntity, Long> {

    @Modifying
    @Query("update FanBoardEntity b set b.boardHits = b.boardHits + 1 where b.id = :id")
    void updateHits(@Param("id") Long id);

    Page<FanBoardEntity> findByBoardTitleContainingOrderByIdDesc(String query, Pageable pageable);

    Page<FanBoardEntity> findByBoardWriterContainingOrderByIdDesc(String query, Pageable pageable);

    Page<FanBoardEntity> findByBoardContentsContainingOrderByIdDesc(String query, Pageable pageable);

    Page<FanBoardEntity> findAllByOrderByIdDesc(Pageable pageable);
}