package com.example.sl.controller.detail.hitter;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/detail/hitter/outfielder")
public class OutfielderController {

    @GetMapping("/KimHeongon")
    public String KimHeongon() {
        return "detail/hitter/outfielder/KimHeongon";
    }

    @GetMapping("/KimDongyub")
    public String KimDongyub() {
        return "detail/hitter/outfielder/KimDongyub";
    }

    @GetMapping("/KooJawook")
    public String KooJawook() {
        return "detail/hitter/outfielder/KooJawook";
    }

    @GetMapping("/LeeSunggyu")
    public String LeeSunggyu() {
        return "detail/hitter/outfielder/LeeSunggyu";
    }

    @GetMapping("/KimTaehoon")
    public String KimTaehoon() {
        return "detail/hitter/outfielder/KimTaehoon";
    }

    @GetMapping("/KimSeongyoon")
    public String KimSeongyoon() {
        return "detail/hitter/outfielder/KimSeongyoon";
    }

    @GetMapping("/YoonJeongbin")
    public String YoonJeongbin() {
        return "detail/hitter/outfielder/YoonJeongbin";
    }
    @GetMapping("/KimJichan")
    public String KimJichan() {
        return "detail/hitter/outfielder/KimJichan";
    }

    @GetMapping("/KimHyeonjoon")
    public String KimHyeonjoon() {
        return "detail/hitter/outfielder/KimHyeonjoon";
    }
}
