package com.example.sl.domain.dto;

import com.example.sl.entity.FaqBoardEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FaqBoardDTO {

    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;
    private String fileName; // 파일 이름 추가
    private String filePath; // 파일 경로 추가

    public FaqBoardDTO(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static FaqBoardDTO toFaqBoardDTO(FaqBoardEntity boardEntity) {
        FaqBoardDTO boardDTO = new FaqBoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        boardDTO.setFileName(boardEntity.getFileName()); // 파일 이름 설정
        boardDTO.setFilePath(boardEntity.getFilePath()); // 파일 경로 설정
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
