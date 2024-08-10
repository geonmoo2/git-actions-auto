package com.example.sl.entity;


import com.example.sl.domain.dto.FanBoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "fan_board_table")
public class FanBoardEntity extends FanBaseEntity {

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

    @OneToMany(mappedBy = "fanBoardEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FanCommentEntity> comments = new ArrayList<>();

    public static FanBoardEntity toSaveEntity(FanBoardDTO boardDTO) {
        FanBoardEntity boardEntity = new FanBoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        return boardEntity;
    }

    public static FanBoardEntity toUpdateEntity(FanBoardDTO boardDTO) {
        FanBoardEntity boardEntity = new FanBoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        boardEntity.setComments(boardDTO.getComments());
        return boardEntity;
    }
}
