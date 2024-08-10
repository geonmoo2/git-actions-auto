package com.example.sl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Signature {
    @Id
    @Column(name="signkey",nullable = false,length=3072)
    private Byte[] keybyte;
    private LocalDate date;
}
