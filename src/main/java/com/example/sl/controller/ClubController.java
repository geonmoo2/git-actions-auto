package com.example.sl.controller;

import com.example.sl.domain.dto.BoardDTO;
import com.example.sl.domain.dto.ClubNewsDTO;
import com.example.sl.domain.service.BoardService;
import com.example.sl.domain.service.ClubNewsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/club")
public class ClubController {

    private static final Logger logger = LoggerFactory.getLogger(ClubController.class);

    @Autowired
    private BoardService boardService;
    @Autowired
    private ClubNewsService clubNewsService;


    @GetMapping("/lionsPark")
    public void lionsPark() {
        // 기존 lionsPark 메소드 내용
    }

    @GetMapping("/slhistory")
    public void slhistory() {
        // 기존 slhistory 메소드 내용
    }

    @GetMapping("/slintro")
    public void slintro() {
        // 기존 slintro 메소드 내용
    }

    @GetMapping("/board/paging")
    public String search(@RequestParam(value = "searchField", required = false) String searchField,
                         @RequestParam(value = "query", required = false) String query,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         Model model) {
        Pageable pageable = PageRequest.of(page - 1, 8);
        Page<BoardDTO> boardList;
        if (query != null && !query.isEmpty() && searchField != null && !searchField.isEmpty()) {
            boardList = boardService.search(searchField, query, pageable);
        } else {
            boardList = boardService.paging(pageable);
        }
        model.addAttribute("boardList", boardList);
        model.addAttribute("query", query);
        model.addAttribute("searchField", searchField);
        model.addAttribute("currentPage", page);

        int blockLimit = 10;
        int startPage = Math.max(1, ((int) Math.ceil((double) page / blockLimit)) * blockLimit - (blockLimit - 1));
        int endPage = Math.min(startPage + blockLimit - 1, boardList.getTotalPages());

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "club/board/paging";
    }

    @GetMapping("/board/")
    public String index(Model model) {
        model.addAttribute("boardList", boardService.findAll());
        return "club/board/list";
    }

    @GetMapping("/board/save")
    public String saveForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "club/board/save";
    }

    @PostMapping("/board/save")
    public String save(@ModelAttribute BoardDTO boardDTO, @RequestParam("file") MultipartFile file) throws IOException {
        // 현재 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // boardWriter 설정
        boardDTO.setBoardWriter(username);

        boardService.save(boardDTO, file);
        return "redirect:/club/board/paging";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable("id") Long id, Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        boardService.updateHits(id); // 조회수 증가 메소드 호출
        BoardDTO boardDTO = boardService.findById(id);
        if (boardDTO != null) {
            model.addAttribute("board", boardDTO);
            model.addAttribute("currentPage", page);

            Optional<BoardDTO> previousBoard = boardService.findFirstByIdLessThanOrderByIdDesc(id);
            previousBoard.ifPresent(value -> model.addAttribute("previousBoard", value));

            Optional<BoardDTO> nextBoard = boardService.findFirstByIdGreaterThanOrderByIdAsc(id);
            nextBoard.ifPresent(value -> model.addAttribute("nextBoard", value));

            return "club/board/detail";
        } else {
            return "redirect:/club/board/paging?page=" + page;
        }
    }

    @GetMapping("/board/update/{id}")
    public String updateBoard(@PathVariable("id") Long id, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        try {
            BoardDTO boardDTO = boardService.findById(id);
            if (boardDTO != null) {
                model.addAttribute("boardUpdate", boardDTO); // 수정된 부분
                model.addAttribute("currentPage", page);

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String username = authentication.getName();
                model.addAttribute("username", username);

                Optional<BoardDTO> previousBoard = boardService.findFirstByIdLessThanOrderByIdDesc(id);
                previousBoard.ifPresent(value -> model.addAttribute("previousBoard", value));

                Optional<BoardDTO> nextBoard = boardService.findFirstByIdGreaterThanOrderByIdAsc(id);
                nextBoard.ifPresent(value -> model.addAttribute("nextBoard", value));

                return "club/board/update"; // 수정된 부분
            } else {
                return "redirect:/board/paging?page=" + page;
            }
        } catch (Exception e) {
            // 예외 로깅
            log.error("Error updating board with id " + id, e);
            return "error/500"; // 사용자 정의 오류 페이지로 리다이렉트
        }
    }

    @PostMapping("/board/update")
    public String update(@ModelAttribute BoardDTO boardDTO, @RequestParam("file") MultipartFile file, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage) throws IOException {
        // 현재 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // boardWriter 설정
        boardDTO.setBoardWriter(username);

        BoardDTO updatedBoard = boardService.update(boardDTO, file);
        return "redirect:/club/board/" + updatedBoard.getId() + "?page=" + currentPage;
    }

    @GetMapping("/board/delete/{id}")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", defaultValue = "1") int page) {
        boardService.delete(id);
        return "redirect:/club/board/paging?page=" + page;
    }

    @GetMapping("/board/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) {
        try {
            // 파일 이름을 URL 디코딩
            fileName = URLDecoder.decode(fileName, "UTF-8");
            Path filePath = Paths.get("C:/uploads/").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
                return ResponseEntity.ok()
                        .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(resource.getFilename(), "UTF-8").replaceAll("\\+", "%20") + "\"")
                        .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                        .header(HttpHeaders.PRAGMA, "no-cache")
                        .header(HttpHeaders.EXPIRES, "0")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException ex) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/clubnews/paging")
    public String clubNewsPaging(@RequestParam(value = "searchField", required = false) String searchField,
                                 @RequestParam(value = "query", required = false) String query,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 Model model) {
        logger.debug("Entering clubNewsPaging method with searchField: {}, query: {}, page: {}", searchField, query, page);
        Pageable pageable = PageRequest.of(page - 1, 8);
        Page<ClubNewsDTO> boardList;
        if (query != null && !query.isEmpty() && searchField != null && !searchField.isEmpty()) {
            boardList = clubNewsService.search(searchField, query, pageable);
        } else {
            boardList = clubNewsService.paging(pageable);
        }
        model.addAttribute("boardList", boardList);
        model.addAttribute("query", query);
        model.addAttribute("searchField", searchField);
        model.addAttribute("currentPage", page);

        int blockLimit = 10;
        int startPage = Math.max(1, ((int) Math.ceil((double) page / blockLimit)) * blockLimit - (blockLimit - 1));
        int endPage = Math.min(startPage + blockLimit - 1, boardList.getTotalPages());

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "club/clubnews/paging";
    }

    @GetMapping("/clubnews/")
    public String clubNewsIndex(Model model) {
        model.addAttribute("boardList", clubNewsService.findAll());
        return "club/clubnews/list";
    }

    @GetMapping("/clubnews/save")
    public String clubNewsSaveForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "club/clubnews/save";
    }

    @PostMapping("/clubnews/save")
    public String clubNewsSave(@ModelAttribute ClubNewsDTO boardDTO, @RequestParam("file") MultipartFile file) throws IOException {
        // 현재 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // boardWriter 설정
        boardDTO.setBoardWriter(username);

        clubNewsService.save(boardDTO, file);
        return "redirect:/club/clubnews/paging";
    }

    @GetMapping("/clubnews/{id}")
    public String clubNewsFindById(
            @PathVariable("id") Long id,
            Model model,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {

        clubNewsService.updateHits(id); // 조회수 증가 메소드 호출
        ClubNewsDTO boardDTO = clubNewsService.findById(id);
        if (boardDTO != null) {
            model.addAttribute("board", boardDTO);
            model.addAttribute("currentPage", page);

            Optional<ClubNewsDTO> previousBoard = clubNewsService.findFirstByIdLessThanOrderByIdDesc(id);
            previousBoard.ifPresent(value -> model.addAttribute("previousBoard", value));

            Optional<ClubNewsDTO> nextBoard = clubNewsService.findFirstByIdGreaterThanOrderByIdAsc(id);
            nextBoard.ifPresent(value -> model.addAttribute("nextBoard", value));

            return "club/clubnews/detail";
        } else {
            return "redirect:/club/clubnews/paging?page=" + page;
        }
    }

    @GetMapping("/clubnews/update/{id}")
    public String clubNewsUpdateForm(@PathVariable("id") Long id, @RequestParam(value = "page", defaultValue = "1") int currentPage, Model model) {
        ClubNewsDTO boardDTO = clubNewsService.findById(id);
        if (boardDTO != null) {
            model.addAttribute("boardUpdate", boardDTO);
            model.addAttribute("currentPage", currentPage);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            model.addAttribute("username", username);

            return "club/clubnews/update";
        } else {
            return "redirect:/club/clubnews/paging?page=" + currentPage;
        }
    }

    @PostMapping("/clubnews/update")
    public String clubNewsUpdate(@ModelAttribute ClubNewsDTO boardDTO, @RequestParam("file") MultipartFile file, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage) throws IOException {
        // 현재 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // boardWriter 설정
        boardDTO.setBoardWriter(username);

        ClubNewsDTO updatedBoard = clubNewsService.update(boardDTO, file);
        return "redirect:/club/clubnews/" + updatedBoard.getId() + "?page=" + currentPage;
    }

    @GetMapping("/clubnews/delete/{id}")
    public String clubNewsDelete(@PathVariable("id") Long id, @RequestParam(value = "page", defaultValue = "1") int page) {
        clubNewsService.delete(id);
        return "redirect:/club/clubnews/paging?page=" + page;
    }

    @GetMapping("/clubnews/download/{fileName:.+}")
    public ResponseEntity<Resource> clubNewsDownloadFile(@PathVariable("fileName") String fileName) {
        try {
            Path filePath = Paths.get("C:/uploads/").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
                return ResponseEntity.ok()
                        .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(resource.getFilename(), "UTF-8").replaceAll("\\+", "%20") + "\"")
                        .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                        .header(HttpHeaders.PRAGMA, "no-cache")
                        .header(HttpHeaders.EXPIRES, "0")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException ex) {
            return ResponseEntity.status(500).build();
        }
    }
}
