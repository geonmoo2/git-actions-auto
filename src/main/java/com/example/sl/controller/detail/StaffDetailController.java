package com.example.sl.controller.detail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/detail/staff")
public class StaffDetailController {

    @GetMapping("/ParkJinman")
    public String ParkJinman() {
        return "detail/staff/ParkJinman";
    }

    @GetMapping("/LeeByungkyu")
    public String LeeByungkyu() {
        return "detail/staff/LeeByungkyu";
    }

    @GetMapping("/ChungMintae")
    public String ChungMintae() {
        return "detail/staff/ChungMintae";
    }

    @GetMapping("/KwonOhjun")
    public String KwonOhjun() {
        return "detail/staff/KwonOhjun";
    }

    @GetMapping("/LeeJinyoung")
    public String LeeJinyoung() {
        return "detail/staff/LeeJinyoung";
    }

    @GetMapping("/BaeYeongSeop")
    public String BaeYeongSeop() {
        return "detail/staff/BaeYeongSeop";
    }

    @GetMapping("/KangMyunggu")
    public String KangMyunggu() {
        return "detail/staff/KangMyunggu";
    }

    @GetMapping("/SonJooin")
    public String SonJooin() {
        return "detail/staff/SonJooin";
    }

    @GetMapping("/ParkChando")
    public String ParkChando() {
        return "detail/staff/ParkChando";
    }

    @GetMapping("/ChongTaehyon")
    public String ChongTaehyon() {
        return "detail/staff/ChongTaehyon";
    }

    @GetMapping("/ChoDongchan")
    public String ChoDongchan() {
        return "detail/staff/ChoDongchan";
    }

    @GetMapping("/ParkHanyi")
    public String ParkHanyi() {
        return "detail/staff/ParkHanyi";
    }

    @GetMapping("/JongHyunwook")
    public String JongHyunwook() {
        return "detail/staff/JongHyunwook";
    }
}