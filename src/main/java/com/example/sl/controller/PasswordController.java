package com.example.sl.controller;

import com.example.sl.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class PasswordController {

    private final UserService userService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "user/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String username, @RequestParam String email, Model model) {
        try {
            userService.resetPassword(username, email);
            model.addAttribute("message", "임시 비밀번호가 이메일로 전송되었습니다.");
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "해당 사용자 아이디와 이메일로 등록된 사용자를 찾을 수 없습니다.");
        }
        return "user/forgot-password";
    }

    @GetMapping("/update-password")
    public String showUpdatePasswordForm() {
        return "user/update-password";
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Authentication authentication,
                                 Model model) {
        String username = authentication.getName();

        try {
            userService.updatePassword(username, currentPassword, newPassword, confirmPassword);
            model.addAttribute("success", true); // 비밀번호 변경 성공 시 플래그 설정
            return "user/update-password";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user/update-password";
        }
    }
}
