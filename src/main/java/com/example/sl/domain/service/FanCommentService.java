package com.example.sl.domain.service;


import com.example.sl.domain.dto.FanCommentDTO;
import com.example.sl.repository.FanBoardRepository;
import com.example.sl.repository.FanCommentRepository;
import com.example.sl.entity.FanBoardEntity;
import com.example.sl.entity.FanCommentEntity;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FanCommentService {

    private final FanCommentRepository fanCommentRepository;
    private final FanBoardRepository fanBoardRepository;

    public Long save(FanCommentDTO commentDTO) {
        Optional<FanBoardEntity> optionalFanBoardEntity = fanBoardRepository.findById(commentDTO.getBoardId());
        if (optionalFanBoardEntity.isPresent()) {
            FanBoardEntity fanBoardEntity = optionalFanBoardEntity.get();
            FanCommentEntity fanCommentEntity = FanCommentEntity.toSaveEntity(commentDTO, fanBoardEntity);
            return fanCommentRepository.save(fanCommentEntity).getId();
        } else {
            return null;
        }
    }

    public List<FanCommentDTO> findAll(Long boardId) {
        FanBoardEntity fanBoardEntity = fanBoardRepository.findById(boardId).orElseThrow();
        List<FanCommentEntity> fanCommentEntityList = fanCommentRepository.findAllByFanBoardEntityOrderByIdDesc(fanBoardEntity);
        List<FanCommentDTO> commentDTOList = new ArrayList<>();
        for (FanCommentEntity fanCommentEntity : fanCommentEntityList) {
            FanCommentDTO commentDTO = FanCommentDTO.toCommentDTO(fanCommentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    public boolean delete(Long id) {
        if (fanCommentRepository.existsById(id)) {
            fanCommentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}