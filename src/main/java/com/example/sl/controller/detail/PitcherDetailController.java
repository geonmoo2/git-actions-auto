package com.example.sl.controller.detail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/detail/pitcher")
public class PitcherDetailController {

    @GetMapping("/OhSeunghwan")
    public String OhSeunghwan() {
        return "detail/pitcher/OhSeunghwan";
    }

    @GetMapping("/LimChangmin")
    public String LimChangmin() {
        return "detail/pitcher/LimChangmin";
    }

    @GetMapping("/BaekJunghyun")
    public String BaekJunghyun() {
        return "detail/pitcher/BaekJunghyun";
    }

    @GetMapping("/KimDaewoo")
    public String KimDaewoo() {
        return "detail/pitcher/KimDaewoo";
    }

    @GetMapping("/ChoiSunghoon")
    public String ChoiSunghoon() {
        return "detail/pitcher/ChoiSunghoon";
    }

    @GetMapping("/KimJaeyun")
    public String KimJaeyun() {
        return "detail/pitcher/KimJaeyun";
    }

    @GetMapping("/RightSeunghyun")
    public String RightSeunghyun() {
        return "detail/pitcher/RightSeunghyun";
    }

    @GetMapping("/KimTaehun")
    public String KimTaehun() {
        return "detail/pitcher/KimTaehun";
    }

    @GetMapping("/ConnorSeabold")
    public String ConnorSeabold() {
        return "detail/pitcher/ConnorSeabold";
    }

    @GetMapping("/DenyiReyes")
    public String DenyiReyes() {
        return "detail/pitcher/DenyiReyes";
    }

    @GetMapping("/ChoiJigwang")
    public String ChoiJigwang() {
        return "detail/pitcher/ChoiJigwang";
    }

    @GetMapping("/ChoiHaneul")
    public String ChoiHaneul() {
        return "detail/pitcher/ChoiHaneul";
    }

    @GetMapping("/WonTaein")
    public String WonTaein() {
        return "detail/pitcher/WonTaein";
    }

    @GetMapping("/LeeSeungmin")
    public String LeeSeungmin() {
        return "detail/pitcher/LeeSeungmin";
    }

    @GetMapping("/LeftSeunghyun")
    public String LeftSeunghyun() {
        return "detail/pitcher/LeftSeunghyun";
    }

    @GetMapping("/LeeHoseong")
    public String LeeHoseong() {
        return "detail/pitcher/LeeHoseong";
    }
    @GetMapping("/YookSunyeop")
    public String YookSunyeop() {
        return "detail/pitcher/YookSunyeop";
    }


}