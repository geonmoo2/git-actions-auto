package com.example.sl.controller;

import com.example.sl.domain.dto.FanBoardDTO;
import com.example.sl.domain.dto.FanCommentDTO;
import com.example.sl.domain.dto.FaqBoardDTO;
import com.example.sl.domain.service.FanBoardService;
import com.example.sl.domain.service.FanCommentService;
import com.example.sl.domain.service.FaqBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/fan")
public class FanBoardController {

    private static final Logger logger = LoggerFactory.getLogger(FanBoardController.class);
    private final FanBoardService boardService;
    private final FanCommentService commentService;
    private final FaqBoardService faqBoardService;

    @GetMapping("/board/fanpaging")
    public String fanPaging(@RequestParam(value = "searchField", required = false) String searchField,
                            @RequestParam(value = "query", required = false) String query,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            Model model) {
        logger.debug("Entering fanPaging method with searchField: {}, query: {}, page: {}", searchField, query, page);
        Pageable pageable = PageRequest.of(page - 1, 8);
        Page<FanBoardDTO> boardList;
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

        return "fan/board/fanpaging";
    }

    @GetMapping("/board/fanlist")
    public String fanList(Model model) {
        List<FanBoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "fan/board/fanlist";
    }

    @GetMapping("/board/fansave")
    public String fanSaveForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "fan/board/fansave";
    }

    @PostMapping("/board/fansave")
    public String fanSave(@ModelAttribute FanBoardDTO boardDTO) {
        logger.debug("Entering fanSave method with boardDTO: {}", boardDTO);
        boardService.save(boardDTO);
        return "redirect:/fan/board/fanpaging";
    }

    @GetMapping("/board/fandetail/{id}")
    public String fanDetail(@PathVariable("id") Long id, Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        logger.debug("Entering fanDetail method with id: {}, page: {}", id, page);
        boardService.updateHits(id);
        FanBoardDTO boardDTO = boardService.findById(id);

        /* 댓글 목록 가져오기 */
        List<FanCommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);

        if (boardDTO != null) {
            model.addAttribute("board", boardDTO);
            model.addAttribute("currentPage", page);
            return "fan/board/fandetail";
        } else {
            return "redirect:/fan/board/fanpaging?page=" + page;
        }
    }

    @GetMapping("/board/fanupdate/{id}")
    public String fanUpdateForm(@PathVariable("id") Long id,
                                @RequestParam(value = "page", defaultValue = "1") int currentPage,
                                Model model) {
        logger.debug("Entering fanUpdateForm method with id: {}, currentPage: {}", id, currentPage);
        FanBoardDTO boardDTO = boardService.findById(id);
        if (boardDTO != null) {
            model.addAttribute("boardUpdate", boardDTO);
            model.addAttribute("currentPage", currentPage);
            return "fan/board/fanupdate";
        } else {
            return "redirect:/fan/board/fanpaging?page=" + currentPage;
        }
    }

    @PostMapping("/board/fanupdate")
    public String fanUpdate(@ModelAttribute FanBoardDTO boardDTO, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model) {
        logger.debug("Entering fanUpdate method with boardDTO: {}", boardDTO);
        FanBoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "redirect:/fan/board/fandetail/" + boardDTO.getId() + "?page=" + currentPage;
    }

    @GetMapping("/board/fandelete/{id}")
    public String fanDelete(@PathVariable("id") Long id, @RequestParam(value = "page", defaultValue = "1") int page) {
        logger.debug("Entering fanDelete method with id: {}", id);
        try {
            boardService.delete(id);
        } catch (Exception e) {
            logger.error("Error deleting board with id: {}", id, e);
        }
        return "redirect:/fan/board/fanpaging?page=" + page;
    }

    @GetMapping("/board/fandownload/{fileName:.+}")
    public ResponseEntity<Resource> fanDownloadFile(@PathVariable("fileName") String fileName) {
        try {
            Path filePath = Paths.get("C:/uploads/").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                String contentType = Files.probeContentType(filePath);
                return ResponseEntity.ok()
                        .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException ex) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/comment/save")
    public ResponseEntity<?> saveComment(@ModelAttribute FanCommentDTO commentDTO) {
        Long saveResult = commentService.save(commentDTO);

        if (saveResult != null) {
            List<FanCommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/comment/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
        boolean deleteResult = commentService.delete(id);

        if (deleteResult) {
            return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("댓글 삭제 실패", HttpStatus.NOT_FOUND);
        }
    }

    // FaqBoardController 시작

    @GetMapping("/faqboard/paging")
    public String faqPaging(@RequestParam(value = "searchField", required = false) String searchField,
                            @RequestParam(value = "query", required = false) String query,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            Model model) {
        logger.debug("Entering faqPaging method with searchField: {}, query: {}, page: {}", searchField, query, page);
        Pageable pageable = PageRequest.of(page - 1, 8);
        Page<FaqBoardDTO> boardList;
        if (query != null && !query.isEmpty() && searchField != null && !searchField.isEmpty()) {
            boardList = faqBoardService.search(searchField, query, pageable);
        } else {
            boardList = faqBoardService.paging(pageable);
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

        return "fan/faqboard/paging";
    }

    @GetMapping("/faqboard/")
    public String faqIndex(Model model) {
        model.addAttribute("boardList", faqBoardService.findAll());
        return "fan/faqboard/list";
    }

    @GetMapping("/faqboard/save")
    public String faqSaveForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "fan/faqboard/save";
    }

    @PostMapping("/faqboard/save")
    public String faqSave(@ModelAttribute FaqBoardDTO boardDTO, @RequestParam("file") MultipartFile file) throws IOException {
        // 현재 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // boardWriter 설정
        boardDTO.setBoardWriter(username);

        faqBoardService.save(boardDTO, file);
        return "redirect:/fan/faqboard/paging";
    }

    @GetMapping("/faqboard/{id}")
    public String faqFindById(
            @PathVariable("id") Long id,
            Model model,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        faqBoardService.updateHits(id); // 조회수 증가 메소드 호출

        FaqBoardDTO boardDTO = faqBoardService.findById(id);
        if (boardDTO != null) {
            model.addAttribute("board", boardDTO);
            model.addAttribute("currentPage", page);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                model.addAttribute("username", username);
            }

            Optional<FaqBoardDTO> previousBoard = faqBoardService.findFirstByIdLessThanOrderByIdDesc(id);
            previousBoard.ifPresent(value -> model.addAttribute("previousBoard", value));

            Optional<FaqBoardDTO> nextBoard = faqBoardService.findFirstByIdGreaterThanOrderByIdAsc(id);
            nextBoard.ifPresent(value -> model.addAttribute("nextBoard", value));

            return "fan/faqboard/detail";
        } else {
            return "redirect:/fan/faqboard/paging?page=" + page;
        }
    }

    @GetMapping("/faqboard/update/{id}")
    public String faqUpdateForm(@PathVariable("id") Long id, @RequestParam(value = "page", defaultValue = "1") int currentPage, Model model) {
        FaqBoardDTO boardDTO = faqBoardService.findById(id);
        if (boardDTO != null) {
            model.addAttribute("boardUpdate", boardDTO);
            model.addAttribute("currentPage", currentPage);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            model.addAttribute("username", username);

            return "fan/faqboard/update";
        } else {
            return "redirect:/fan/faqboard/paging?page=" + currentPage;
        }
    }

    @PostMapping("/faqboard/update")
    public String faqUpdate(@ModelAttribute FaqBoardDTO boardDTO, @RequestParam("file") MultipartFile file, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage) throws IOException {
        // 현재 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // boardWriter 설정
        boardDTO.setBoardWriter(username);

        FaqBoardDTO updatedBoard = faqBoardService.update(boardDTO, file);
        return "redirect:/fan/faqboard/" + updatedBoard.getId() + "?page=" + currentPage;
    }

    @GetMapping("/faqboard/delete/{id}")
    public String faqDelete(@PathVariable("id") Long id, @RequestParam(value = "page", defaultValue = "1") int page) {
        faqBoardService.delete(id);
        return "redirect:/fan/faqboard/paging?page=" + page;
    }

    @GetMapping("/faqboard/download/{fileName:.+}")
    public ResponseEntity<Resource> faqDownloadFile(@PathVariable("fileName") String fileName) {
        try {
            Path filePath = Paths.get("C:/uploads/").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                String contentType = Files.probeContentType(filePath);
                return ResponseEntity.ok()
                        .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException ex) {
            return ResponseEntity.status(500).build();
        }
    }
}