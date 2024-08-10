package com.example.sl.entity;


import com.example.sl.domain.dto.FanCommentDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "fan_comment_table")
public class FanCommentEntity extends FanBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private FanBoardEntity fanBoardEntity;

    public static FanCommentEntity toSaveEntity(FanCommentDTO commentDTO, FanBoardEntity fanBoardEntity) {
        FanCommentEntity fanCommentEntity = new FanCommentEntity();
        fanCommentEntity.setCommentWriter(commentDTO.getCommentWriter());
        fanCommentEntity.setCommentContents(commentDTO.getCommentContents());
        fanCommentEntity.setFanBoardEntity(fanBoardEntity);
        return fanCommentEntity;
    }
}
