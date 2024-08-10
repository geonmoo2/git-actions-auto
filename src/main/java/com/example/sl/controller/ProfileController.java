package com.example.sl.controller;

import com.example.sl.domain.dto.UserProfileUpdateRequest;
import com.example.sl.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping("/update-profile")
    public String showUpdateProfileForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("user", userService.getUserInfo(username));
        return "user/update-profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(UserProfileUpdateRequest userProfileUpdateRequest, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            userService.updateUserProfile(username, userProfileUpdateRequest);
            model.addAttribute("message", "프로필이 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            model.addAttribute("error", "프로필 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
        model.addAttribute("user", userService.getUserInfo(username));
        return "user/update-profile";
    }
}
