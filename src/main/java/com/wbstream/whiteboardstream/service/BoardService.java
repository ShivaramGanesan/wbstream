package com.wbstream.whiteboardstream.service;

import com.wbstream.whiteboardstream.pojo.Board;
import com.wbstream.whiteboardstream.pojo.User;
import com.wbstream.whiteboardstream.repo.BoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    BoardRepo boardRepo;

    public Long createBoard(String name, User u){
        Board b = new Board();
        b.setName(name);
        b.setCreatedBy(u);
        Board createdBoard = boardRepo.save(b);
        return createdBoard.getId();
    }

    public void deleteBoard(Long id){
        boardRepo.deleteById(id);
    }
}
