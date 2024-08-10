package com.example.sl.domain.service;


import com.example.sl.domain.dto.BoardDTO;
import com.example.sl.repository.BoardRepository;
import com.example.sl.entity.BoardEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    private final BoardRepository boardRepository;

    private final String uploadPath = "C:/uploads/";

    public void save(BoardDTO boardDTO, MultipartFile file) throws IOException {
        logger.debug("Entering save method with boardDTO: {}", boardDTO);

        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String filePath = uploadPath + fileName;

            // 디렉토리가 존재하지 않으면 생성합니다.
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            file.transferTo(new File(filePath));
            boardDTO.setFileName(fileName);
            boardDTO.setFilePath(filePath);
        }

        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        Page<BoardEntity> boardEntities = boardRepository.findAllByOrderByIdDesc(pageable);
        return boardEntities.map(BoardDTO::toBoardDTO);
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        return optionalBoardEntity.map(BoardDTO::toBoardDTO).orElse(null);
    }

    @Transactional
    public BoardDTO update(BoardDTO boardDTO, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String filePath = uploadPath + fileName;

            // 디렉토리가 존재하지 않으면 생성합니다.
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            file.transferTo(new File(filePath));
            boardDTO.setFileName(fileName);
            boardDTO.setFilePath(filePath);
        }

        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return BoardDTO.toBoardDTO(boardEntity);
    }

    @Transactional
    public void delete(Long id) {
        // 댓글 삭제 로직 제거
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> search(String searchField, String query, Pageable pageable) {
        Page<BoardEntity> boardEntities;
        switch (searchField) {
            case "id":
                try {
                    Long id = Long.parseLong(query);
                    Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
                    List<BoardEntity> boardEntityList = optionalBoardEntity.map(List::of).orElseGet(List::of);
                    boardEntities = new PageImpl<>(boardEntityList, pageable, boardEntityList.size());
                } catch (NumberFormatException e) {
                    boardEntities = Page.empty(pageable);
                }
                break;
            case "title":
                boardEntities = boardRepository.findByBoardTitleContainingOrderByIdDesc(query, pageable);
                break;
            case "writer":
                boardEntities = boardRepository.findByBoardWriterContainingOrderByIdDesc(query, pageable);
                break;
            case "content":
                boardEntities = boardRepository.findByBoardContentsContainingOrderByIdDesc(query, pageable);
                break;
            default:
                boardEntities = Page.empty(pageable);
                break;
        }
        return boardEntities.map(BoardDTO::toBoardDTO);
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        return boardEntities.stream()
                .map(BoardDTO::toBoardDTO)
                .collect(Collectors.toList());
    }

    // 이전 게시글 찾기
    public Optional<BoardDTO> findFirstByIdLessThanOrderByIdDesc(Long id) {
        return boardRepository.findFirstByIdLessThanOrderByIdDesc(id).map(BoardDTO::toBoardDTO);
    }

    // 다음 게시글 찾기
    public Optional<BoardDTO> findFirstByIdGreaterThanOrderByIdAsc(Long id) {
        return boardRepository.findFirstByIdGreaterThanOrderByIdAsc(id).map(BoardDTO::toBoardDTO);
    }
}
