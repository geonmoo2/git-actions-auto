package com.example.sl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatid;

    @Column(name = "seat_number")
    private String seatNumber;

    private String zone;
    private boolean reserved;
    private BigDecimal price;

    @Column(name = "main_zone")
    private String mainZone;

    private Long gameInfoId; // 게임 정보 ID 추가
}
