package com.example.sl.controller.detail.hitter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/detail/hitter/infielder")
public class InfielderController {

    @GetMapping("/ParkByungho")
    public String ParkByungho() {
        return "detail/hitter/infielder/ParkByungho";
    }

    @GetMapping("/JeonByeongwoo")
    public String JeonByeongwoo() {
        return "detail/hitter/infielder/JeonByeongwoo";
    }

    @GetMapping("/AhnJoohyung")
    public String AhnJoohyung() {
        return "detail/hitter/infielder/AhnJoohyung";
    }

    @GetMapping("/RyuJihyuk")
    public String RyuJihyuk() {
        return "detail/hitter/infielder/RyuJihyuk";
    }
    @GetMapping("/DavidMacKinnon")
    public String DavidMacKinnon() {
        return "detail/hitter/infielder/DavidMacKinnon";
    }

    @GetMapping("/KimDongjin")
    public String KimDongjin() {
        return "detail/hitter/infielder/KimDongjin";
    }

    @GetMapping("/LeeChangyong")
    public String LeeChangyong() {
        return "detail/hitter/infielder/LeeChangyong";
    }

    @GetMapping("/LeeJaehyeon")
    public String LeeJaehyeon() {
        return "detail/hitter/infielder/LeeJaehyeon";
    }

    @GetMapping("/KimYoungwoong")
    public String KimYoungwoong() {
        return "detail/hitter/infielder/KimYoungwoong";
    }

}