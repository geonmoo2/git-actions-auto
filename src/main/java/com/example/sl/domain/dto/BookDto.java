package com.example.sl.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookDto {
    private Long bookid;
    private Long seatid;
    private String seat;
    private String name;
    private String gameinfoId;
    private String gameName;  // 게임 이름 추가

    private LocalDateTime date;
    private String bookstatus;
    private String payid;
    private String mainZone;
    private String zone;
    private String impUid;

    private BigDecimal totalPrice;
}
