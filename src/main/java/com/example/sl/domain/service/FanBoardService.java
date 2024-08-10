package com.example.sl.domain.service;


import com.example.sl.domain.dto.FanBoardDTO;
import com.example.sl.repository.FanBoardRepository;
import com.example.sl.entity.FanBoardEntity;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FanBoardService {

    private static final Logger logger = LoggerFactory.getLogger(FanBoardService.class);

    private final FanBoardRepository boardRepository;

    public void save(FanBoardDTO boardDTO) {
        logger.debug("Entering save method with boardDTO: {}", boardDTO);
        FanBoardEntity boardEntity = FanBoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
    }

    public List<FanBoardDTO> findAll() {
        logger.debug("Entering findAll method");
        List<FanBoardEntity> boardEntityList = boardRepository.findAll();
        List<FanBoardDTO> boardDTOList = new ArrayList<>();
        for (FanBoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(FanBoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    public Page<FanBoardDTO> search(String searchField, String query, Pageable pageable) {
        logger.debug("Entering search method with searchField: {}, query: {}", searchField, query);
        Page<FanBoardEntity> boardEntities;
        switch (searchField) {
            case "id":
                try {
                    Long id = Long.parseLong(query);
                    Optional<FanBoardEntity> optionalBoardEntity = boardRepository.findById(id);
                    if (optionalBoardEntity.isPresent()) {
                        boardEntities = new PageImpl<>(Collections.singletonList(optionalBoardEntity.get()), pageable, 1);
                    } else {
                        boardEntities = Page.empty();
                    }
                } catch (NumberFormatException e) {
                    boardEntities = Page.empty();
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
                boardEntities = Page.empty();
                break;
        }
        return boardEntities.map(FanBoardDTO::toBoardDTO);
    }

    public Page<FanBoardDTO> paging(Pageable pageable) {
        logger.debug("Entering paging method with pageable: {}", pageable);
        Page<FanBoardEntity> boardEntities = boardRepository.findAllByOrderByIdDesc(pageable);
        return boardEntities.map(FanBoardDTO::toBoardDTO);
    }

    @Transactional
    public void updateHits(Long id) {
        logger.debug("Entering updateHits method with id: {}", id);
        boardRepository.updateHits(id);
    }

    @Transactional
    public FanBoardDTO findById(Long id) {
        logger.debug("Entering findById method with id: {}", id);
        Optional<FanBoardEntity> optionalBoardEntity = boardRepository.findById(id);
        return optionalBoardEntity.map(FanBoardDTO::toBoardDTO).orElse(null);
    }

    @Transactional
    public FanBoardDTO update(FanBoardDTO boardDTO) {
        logger.debug("Entering update method with boardDTO: {}", boardDTO);
        Optional<FanBoardEntity> optionalBoardEntity = boardRepository.findById(boardDTO.getId());
        if (optionalBoardEntity.isPresent()) {
            FanBoardEntity boardEntity = optionalBoardEntity.get();

            // 기존 댓글을 유지하거나 새로운 댓글 리스트 설정
            if (boardDTO.getComments() != null) {
                boardEntity.getComments().clear();
                boardEntity.getComments().addAll(boardDTO.getComments());
            }

            boardEntity.setBoardTitle(boardDTO.getBoardTitle());
            boardEntity.setBoardContents(boardDTO.getBoardContents());
            boardEntity.setBoardHits(boardDTO.getBoardHits());

            boardRepository.save(boardEntity);
            return FanBoardDTO.toBoardDTO(boardEntity);
        } else {
            throw new IllegalArgumentException("Board with ID " + boardDTO.getId() + " not found.");
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.debug("Entering delete method with id: {}", id);
        boardRepository.deleteById(id); // 게시글 삭제
    }
}
