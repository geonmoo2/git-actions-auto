package com.example.sl.controller.detail.hitter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/detail/hitter/catcher")
public class CatcherController {

    @GetMapping("/KangMinho")
    public String KangMinho() {
        return "detail/hitter/catcher/KangMinho";
    }

    @GetMapping("/KimJaeseong")
    public String KimJaeseong() {
        return "detail/hitter/catcher/KimJaeseong";
    }

    @GetMapping("/LeeByungheon")
    public String LeeByungheon() {
        return "detail/hitter/catcher/LeeByungheon";
    }

    @GetMapping("/KimDohwan")
    public String KimDohwan() {
        return "detail/hitter/catcher/KimDohwan";
    }


}