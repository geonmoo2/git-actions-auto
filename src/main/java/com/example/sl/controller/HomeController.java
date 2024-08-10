package com.example.sl.controller;

import com.example.sl.domain.dto.ClubNewsDTO;
import com.example.sl.domain.dto.FanBoardDTO;
import com.example.sl.domain.dto.FaqBoardDTO;
import com.example.sl.domain.dto.ImageDto;
import com.example.sl.domain.service.*;
import com.example.sl.entity.ImageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private FanBoardService boardService;

    @Autowired
    private ClubNewsService clubNewsService;

    @Autowired
    private ImageService imageService;
    @Autowired
    private FaqBoardService faqBoardService;

    @GetMapping("/samsung")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<FanBoardDTO> boardList = boardService.findAll();
        List<ClubNewsDTO> boardList2 = clubNewsService.findAll();
        List<FaqBoardDTO> boardList3 = faqBoardService.findAll();

        List<ImageDto> imageList = imageService.findAll();



        List<FanBoardDTO> latestFanBoard = boardList.stream()
                .sorted(Comparator.comparing(FanBoardDTO::getBoardCreatedTime).reversed())
                .limit(3)
                .collect(Collectors.toList());

        List<FaqBoardDTO> latestFaqBoard = boardList3.stream()
                .sorted(Comparator.comparing(FaqBoardDTO::getBoardCreatedTime).reversed())
                .limit(3)
                .collect(Collectors.toList());

        // club news 최신 7개 선택
        List<ClubNewsDTO> latestClubNews = boardList2.stream()
                .sorted(Comparator.comparing(ClubNewsDTO::getBoardCreatedTime).reversed())
                .limit(7)
                .collect(Collectors.toList());

        List<ImageDto> latestimages = imageList.stream()
                .sorted(Comparator.comparing(ImageDto::getId).reversed()) // id 기준으로 내림차순 정렬
                .limit(7) // 최대 7개 요소 선택
                .map(el ->{
                    el.setImage64Based(  Base64.getEncoder().encodeToString(el.getData())   );
                    return el;
                })
                .collect(Collectors.toList());

        model.addAttribute("latestFanBoard", latestFanBoard);
        model.addAttribute("latestFaqBoard", latestFaqBoard);
        model.addAttribute("latestClubNews", latestClubNews);
        model.addAttribute("latestimages", latestimages);

        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
//            model.addAttribute("userDetails",userDetails);
        }

        System.out.println("USERDETAILS : " + userDetails);
        return "samsung";
    }


}