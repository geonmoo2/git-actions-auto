package com.example.sl.repository;

import com.example.sl.entity.FanBoardEntity;
import com.example.sl.entity.FanCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FanCommentRepository extends JpaRepository<FanCommentEntity, Long> {
    List<FanCommentEntity> findAllByFanBoardEntityOrderByIdDesc(FanBoardEntity fanBoardEntity);
    void deleteByFanBoardEntity_Id(Long id);
}
