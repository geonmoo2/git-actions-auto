package com.example.sl.entity;

import com.example.sl.domain.dto.ClubNewsDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Table(name = "clubnews_board_table")
@EntityListeners(AuditingEntityListener.class)
public class ClubNewsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String boardWriter;

    @Column
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 1000)
    private String boardContents;

    @Column
    private int boardHits;

    @Column
    private String fileName; // 파일 이름 추가

    @Column
    private String filePath; // 파일 경로 추가

    public static ClubNewsEntity toSaveEntity(ClubNewsDTO boardDTO) {
        ClubNewsEntity boardEntity = new ClubNewsEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileName(boardDTO.getFileName()); // 파일 이름 설정
        boardEntity.setFilePath(boardDTO.getFilePath()); // 파일 경로 설정
        return boardEntity;
    }

    public static ClubNewsEntity toUpdateEntity(ClubNewsDTO boardDTO) {
        ClubNewsEntity boardEntity = new ClubNewsEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        boardEntity.setFileName(boardDTO.getFileName()); // 파일 이름 설정
        boardEntity.setFilePath(boardDTO.getFilePath()); // 파일 경로 설정
        return boardEntity;
    }
}
