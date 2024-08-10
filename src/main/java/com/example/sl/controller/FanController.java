package com.example.sl.controller;

import com.example.sl.domain.dto.FanBoardDTO;
import com.example.sl.domain.service.FanBoardService;
import com.example.sl.domain.service.StoryService;

import com.example.sl.entity.StoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/fan")
public class FanController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private FanBoardService boardService;

    @GetMapping("/fan_cheerdetails")
    public String fancm(){
        return "fan/fan_cheerdetails";
    }

    @GetMapping("/fan_cheermain")
    public String fancd(){
        return "fan/fan_cheermain";
    }

    @GetMapping("/fan_main")
    public String fanm(@RequestParam(value = "searchField", required = false) String searchField,
                       @RequestParam(value = "query", required = false) String query,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       Model model) {
        Pageable pageable = PageRequest.of(page - 1, 8);
        Page<FanBoardDTO> boardList;
        if (query != null && !query.isEmpty() && searchField != null && !searchField.isEmpty()) {
            boardList = boardService.search(searchField, query, pageable);
        } else {
            boardList = boardService.paging(pageable);
        }

        List<FanBoardDTO> latestFanBoard = boardList.stream()
                .sorted(Comparator.comparing(FanBoardDTO::getBoardCreatedTime).reversed())
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("boardList", latestFanBoard);
        model.addAttribute("query", query);
        model.addAttribute("searchField", searchField);
        model.addAttribute("currentPage", page);

        int blockLimit = 10;
        int startPage = Math.max(1, ((int) Math.ceil((double) page / blockLimit)) * blockLimit - (blockLimit - 1));
        int endPage = Math.min(startPage + blockLimit - 1, boardList.getTotalPages());

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "fan/fan_main";
    }
}
