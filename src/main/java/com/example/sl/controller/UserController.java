package com.example.sl.controller;

import com.example.sl.domain.dto.UserAdultJoinRequest;
import com.example.sl.domain.dto.UserChildJoinRequest;
import com.example.sl.domain.service.UserService;
import com.example.sl.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    private int a;

    @GetMapping("/join_start")
    public String join_start() {
        return "user/join_start";
    }
    @GetMapping("/adult_join")
    public String adult_join() {
        return "user/adult_join";
    }

    @GetMapping("/children_join")
    public String children_join() {
        return "user/children_join";
    }

    @GetMapping("/join_finish")
    public String join_finish() {
        return "user/join_finish";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/adult_join")
    public String handleUserJoinRequest(@ModelAttribute UserAdultJoinRequest requestDto) {
        // 사용자 등록 처리 로직 호출
        boolean result = userService.userAdultJoin(requestDto);

        if(result) {
            return "redirect:/user/join_finish";
        }
        else {
            return "redirect:/user/adult_join";
        }
    }

    @PostMapping("/children_join")
    public String handleUserJoinRequest(@ModelAttribute UserChildJoinRequest requestDto) {
        // 사용자 등록 처리 로직 호출
        boolean result = userService.userChildJoin(requestDto);

        if(result) {
            return "redirect:/user/join_finish";
        }
        else {
            return "redirect:/user/child_join";
        }
    }

    @GetMapping("/profile")
    public String showUserProfile(Authentication authentication, Model model) {
        String userName = authentication.getName();
        User user = userService.getUserInfo(userName);
        model.addAttribute("user", user);
        return "user/profile";
    }

    // 아이디 찾기 페이지를 반환하는 메서드
    @GetMapping("/find_username")
    public String showFindUsernamePage() {
        return "user/find_username";
    }

    // 아이디 찾기 요청을 처리하는 메서드
    @PostMapping("/find_username")
    public String findUsername(@RequestParam("realname") String realname,
                               @RequestParam("birth") String birth,
                               @RequestParam("email") String email,
                               Model model) {
        try {
            LocalDate birthDate = LocalDate.parse(birth); // 입력된 생년월일을 LocalDate로 변환
            String username = userService.findUsername(realname, birthDate, email);

            if (username != null) {
                model.addAttribute("message", "Your username is: " + username);
            } else {
                model.addAttribute("errorMessage", "회원정보가 일치하지 않습니다. ");
            }
        } catch (DateTimeParseException e) {
            model.addAttribute("errorMessage", "생년월일 입력방식이 잘못되었습니다. YYYY-MM-DD.");
        }
        return "user/find_username_result";
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal User user) {
        if (user != null) {
            return ResponseEntity.ok(Collections.singletonMap("username", user.getUserName()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
    }
}
