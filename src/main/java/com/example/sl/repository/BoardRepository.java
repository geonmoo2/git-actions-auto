package com.example.sl.repository;

import com.example.sl.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Modifying
    @Query("update BoardEntity b set b.boardHits = b.boardHits + 1 where b.id = :id")
    void updateHits(@Param("id") Long id);

    Page<BoardEntity> findByBoardTitleContainingOrderByIdDesc(String query, Pageable pageable);

    Page<BoardEntity> findByBoardWriterContainingOrderByIdDesc(String query, Pageable pageable);

    Page<BoardEntity> findByBoardContentsContainingOrderByIdDesc(String query, Pageable pageable);

    Page<BoardEntity> findAllByOrderByIdDesc(Pageable pageable);

    // 이전 게시글 찾기
    Optional<BoardEntity> findFirstByIdLessThanOrderByIdDesc(Long id);

    // 다음 게시글 찾기
    Optional<BoardEntity> findFirstByIdGreaterThanOrderByIdAsc(Long id);
}
