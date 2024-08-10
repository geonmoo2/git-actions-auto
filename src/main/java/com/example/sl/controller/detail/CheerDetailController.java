package com.example.sl.controller.detail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/detail/cheer")
public class CheerDetailController {

    @GetMapping("/KimSangheon")
    public String KimSangheon() {
        return "detail/cheer/KimSangheon";
    }

    @GetMapping("/LeeSujin")
    public String LeeSujin() {
        return "detail/cheer/LeeSujin";
    }

    @GetMapping("/KimHayeon")
    public String KimHayeon() {
        return "detail/cheer/KimHayeon";
    }

    @GetMapping("/JeongYumi")
    public String JeongYumi() {
        return "detail/cheer/JeongYumi";
    }

    @GetMapping("/KimGahyun")
    public String KimGahyun() {
        return "detail/cheer/KimGahyun";
    }

    @GetMapping("/LeeSohyeon")
    public String LeeSohyeon() {
        return "detail/cheer/LeeSohyeon";
    }

    @GetMapping("/KimMiso")
    public String KimMiso() {
        return "detail/cheer/KimMiso";
    }

    @GetMapping("/KwonGayoung")
    public String KwonGayoung() {
        return "detail/cheer/KwonGayoung";
    }

    @GetMapping("/KimYujung")
    public String KimYujung() {
        return "detail/cheer/KimYujung";
    }

    @GetMapping("/SungHyoryun")
    public String SungHyoryun() {
        return "detail/cheer/SungHyoryun";
    }

    @GetMapping("/SongYeeun")
    public String SongYeeun() {
        return "detail/cheer/SongYeeun";
    }

    @GetMapping("/ChaHyomin")
    public String ChaHyomin() {
        return "detail/cheer/ChaHyomin";
    }


}
