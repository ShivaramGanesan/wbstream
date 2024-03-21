package com.wbstream.whiteboardstream.pojo;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import org.springframework.stereotype.Component;

import java.util.List;

@Entity
@Component
@Table(name = "boardusers")
public class BoardUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


//    User user;
    Long userId;
    Long boardId;

    public BoardUsers() {
    }

    public BoardUsers(Long boardId, Long userId) {
        this.boardId = boardId;
        this.userId = userId;
    }

//    Board board;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }
}
