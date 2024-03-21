package com.wbstream.whiteboardstream.service;

import com.wbstream.whiteboardstream.pojo.Board;
import com.wbstream.whiteboardstream.pojo.BoardUsers;
import com.wbstream.whiteboardstream.pojo.User;
import com.wbstream.whiteboardstream.repo.BoardRepo;
import com.wbstream.whiteboardstream.repo.BoardUsersRepo;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BoardService {

    @Autowired
    BoardRepo boardRepo;
    @Autowired
    BoardUsersRepo boardUsersRepo;

    @Transactional
    public Long createBoard(String name, User u){
        Board b = new Board();
        b.setName(name);
        b.setCreatedBy(u);
        Board createdBoard = boardRepo.save(b);
        BoardUsers users = new BoardUsers();
        users.setBoardId(createdBoard.getId());
        users.setUserId(u.getId());
        boardUsersRepo.save(users);
        return createdBoard.getId();
    }

//    public Long addUserToBoard(Long boardId, Long userId){
//        BoardUsers users = new BoardUsers(boardId, userId);
//        BoardUsers createdEntry = boardUsersRepo.save(users);
//        return createdEntry.getId();
//    }


    public List<Long> addUsersToBoard(Long boardId, List<Long> userIds){
        List<BoardUsers> list = new ArrayList<>();

        List<Long> newUserIds = getNewUserIdsForBoard(boardId, userIds);
        System.out.println("new user Ids for board");
        System.out.println(newUserIds);
        for(Long userId : newUserIds){
            list.add(new BoardUsers(boardId, userId));
        }
        List<Long> resList = new ArrayList();
        if(!list.isEmpty()){
            List<BoardUsers> res = boardUsersRepo.saveAll(list);
            for(BoardUsers entry : res){
                resList.add(entry.getId());
            }
        }
        return resList;
    }

    public void removeUsersFromBoard(Long boardId, List<Long> userIds){
        boardUsersRepo.deleteUsersFromBoard(boardId, userIds);
    }

    private List<Long> getNewUserIdsForBoard(Long boardId, List<Long> userIds){
        List<User> list = boardUsersRepo.findUsersByBoardId(boardId);
        Set<Long> userIdSet = new HashSet<>();
        for(User u : list){
            userIdSet.add(u.getId());
        }
        List<Long> resIds = new ArrayList<>();

        for(Long id : userIds){
            if(!userIdSet.contains(id)){
                resIds.add(id);
            }
        }
        return resIds;
    }

    @Transactional
    public void deleteBoard(Long id){
        boardUsersRepo.deleteAllUsersFromBoard(id);
        boardRepo.deleteById(id);
    }

    public List<User> getBoardUsers(Long boardId){
        return boardUsersRepo.findUsersByBoardId(boardId);
    }

    public List<Board> getAllBoards(){
        return boardRepo.findAll();
    }
}
