package com.example.sl.repository;

import com.example.sl.entity.FaqBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FaqBoardRepository extends JpaRepository<FaqBoardEntity, Long> {

    @Modifying
    @Query("update FaqBoardEntity b set b.boardHits = b.boardHits + 1 where b.id = :id")
    void updateHits(@Param("id") Long id);

    Page<FaqBoardEntity> findByBoardTitleContainingOrderByIdDesc(String query, Pageable pageable);

    Page<FaqBoardEntity> findByBoardWriterContainingOrderByIdDesc(String query, Pageable pageable);

    Page<FaqBoardEntity> findByBoardContentsContainingOrderByIdDesc(String query, Pageable pageable);

    Page<FaqBoardEntity> findAllByOrderByIdDesc(Pageable pageable);

    Optional<FaqBoardEntity> findFirstByIdLessThanOrderByIdDesc(Long id);

    Optional<FaqBoardEntity> findFirstByIdGreaterThanOrderByIdAsc(Long id);
}
