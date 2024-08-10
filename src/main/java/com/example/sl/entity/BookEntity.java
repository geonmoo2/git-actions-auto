package com.example.sl.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookid;
    private Long seatid;
    private String seat;
    private String name;
    private String gameinfo;
    private String gameName;  // 게임 이름 추가

    private LocalDateTime date;
    private String bookstatus;
    private String payid;
    private String impUid;
    private String mainZone;
    private String zone;

    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
