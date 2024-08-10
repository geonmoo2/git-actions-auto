package com.example.sl.repository;

import com.example.sl.entity.GameInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameInfoRepository extends JpaRepository<GameInfoEntity, Long> {
}