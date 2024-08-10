package com.example.sl.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter
@Setter
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String password;

    private String role;
    private boolean islocked;
    private String addr;
    private String adultphone;
    private String childphone;
    private LocalDate birth;
    private String email;
    private char gender;
    private String membership;
    private String realname;
    private String provider;
    private String providerId;
}
