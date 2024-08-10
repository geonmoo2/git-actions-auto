package com.example.sl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/player")
public class PlayerController {

    @GetMapping("/player_main")
    public String player_main() {
        return "player/player_main";
    }

    @GetMapping("/staff")
    public String staff() {
        return "player/staff";
    }

    @GetMapping("/pitcher")
    public String pitcher() {
        return "player/pitcher";
    }

    @GetMapping("/hitter")
    public String hitter() {
        return "player/hitter";
    }

}
