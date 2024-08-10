package com.example.sl.domain.dto;

import com.example.sl.entity.FanCommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
public class FanCommentDTO {

    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;

    public static FanCommentDTO toCommentDTO(FanCommentEntity commentEntity, Long boardId) {
        FanCommentDTO commentDTO = new FanCommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentCreatedTime(commentEntity.getCreatedTime());
        commentDTO.setBoardId(boardId);
        return commentDTO;
    }

    // 날짜와 시간을 'yyyy-MM-dd HH:mm' 형식으로 변환하는 메서드
    public String getFormattedCommentCreatedTime() {
        if (commentCreatedTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return commentCreatedTime.format(formatter);
        }
        return "";
    }
}
