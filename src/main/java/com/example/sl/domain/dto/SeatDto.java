package com.example.sl.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {
    private Long seatid;
    private String seat_number;
    private String zone; // 구역 필드 추가
    private boolean reserved;
    private BigDecimal price;
    private String mainZone;

}