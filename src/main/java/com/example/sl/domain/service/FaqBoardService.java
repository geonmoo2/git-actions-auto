package com.example.sl.domain.service;

import com.example.sl.domain.dto.FaqBoardDTO;
import com.example.sl.repository.FaqBoardRepository;
import com.example.sl.entity.FaqBoardEntity;

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
public class FaqBoardService {

    private static final Logger logger = LoggerFactory.getLogger(FaqBoardService.class);

    private final FaqBoardRepository boardRepository;

    private final String uploadPath = "C:/uploads/";

    public void save(FaqBoardDTO boardDTO, MultipartFile file) throws IOException {
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

        FaqBoardEntity boardEntity = FaqBoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
    }

    public Page<FaqBoardDTO> paging(Pageable pageable) {
        Page<FaqBoardEntity> boardEntities = boardRepository.findAllByOrderByIdDesc(pageable);
        return boardEntities.map(FaqBoardDTO::toFaqBoardDTO);
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public FaqBoardDTO findById(Long id) {
        Optional<FaqBoardEntity> optionalBoardEntity = boardRepository.findById(id);
        return optionalBoardEntity.map(FaqBoardDTO::toFaqBoardDTO).orElse(null);
    }

    @Transactional
    public FaqBoardDTO update(FaqBoardDTO boardDTO, MultipartFile file) throws IOException {
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

        FaqBoardEntity boardEntity = FaqBoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return FaqBoardDTO.toFaqBoardDTO(boardEntity);
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<FaqBoardDTO> search(String searchField, String query, Pageable pageable) {
        Page<FaqBoardEntity> boardEntities;
        switch (searchField) {
            case "id":
                try {
                    Long id = Long.parseLong(query);
                    Optional<FaqBoardEntity> optionalBoardEntity = boardRepository.findById(id);
                    List<FaqBoardEntity> boardEntityList = optionalBoardEntity.map(List::of).orElseGet(List::of);
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
        return boardEntities.map(FaqBoardDTO::toFaqBoardDTO);
    }

    public List<FaqBoardDTO> findAll() {
        List<FaqBoardEntity> boardEntities = boardRepository.findAll();
        return boardEntities.stream()
                .map(FaqBoardDTO::toFaqBoardDTO)
                .collect(Collectors.toList());
    }

    public Optional<FaqBoardDTO> findFirstByIdLessThanOrderByIdDesc(Long id) {
        return boardRepository.findFirstByIdLessThanOrderByIdDesc(id).map(FaqBoardDTO::toFaqBoardDTO);
    }

    public Optional<FaqBoardDTO> findFirstByIdGreaterThanOrderByIdAsc(Long id) {
        return boardRepository.findFirstByIdGreaterThanOrderByIdAsc(id).map(FaqBoardDTO::toFaqBoardDTO);
    }
}