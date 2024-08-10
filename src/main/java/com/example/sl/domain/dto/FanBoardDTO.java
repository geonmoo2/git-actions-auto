package com.example.sl.domain.dto;

import com.example.sl.entity.FanBoardEntity;
import com.example.sl.entity.FanCommentEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FanBoardDTO {

    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;
    private List<FanCommentEntity> comments;  // 댓글 리스트 추가

    public FanBoardDTO(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static FanBoardDTO toBoardDTO(FanBoardEntity boardEntity) {
        FanBoardDTO boardDTO = new FanBoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        boardDTO.setComments(boardEntity.getComments());  // 댓글 리스트 설정
        return boardDTO;
    }

    // 추가: 날짜와 시간을 변환하는 메서드
    public String getFormattedBoardCreatedTime() {
        if (boardCreatedTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return boardCreatedTime.format(formatter);
        }
        return "";
    }

    public String getFormattedBoardUpdatedTime() {
        if (boardUpdatedTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return boardUpdatedTime.format(formatter);
        }
        return "";
    }
}
