package com.wbstream.whiteboardstream.controller;

import com.wbstream.whiteboardstream.aspect.BoardAspects;
import com.wbstream.whiteboardstream.exceptions.APIException;
import com.wbstream.whiteboardstream.pojo.Board;
import com.wbstream.whiteboardstream.pojo.User;
import com.wbstream.whiteboardstream.service.BoardService;
import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

public class BoardController {

    @Autowired
    BoardAspects boardAspect;

    @Autowired
    BoardService boardService;

    @PostMapping(value = "/board")
    public String createBoard(@RequestBody String body) throws APIException {
        User u = boardAspect.getUser();
        JSONObject reqBody = boardAspect.getReqBody();
        boardService.createBoard(reqBody.optString("name"), u);
        return "board created";

    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> handleApiException(APIException ae){
        return new ResponseEntity<>(ae.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/board/{id}")
    public String deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
        return "board deleted successfully";
    }

    @PostMapping("/board/{id}/users")
    public String addUsersToBoard(@PathVariable(name = "id") Long boardId, @RequestBody String requestBody) throws APIException{
        if(requestBody == null || requestBody.isEmpty()){
            throw new APIException("user details missing");
        }
        List<Long> userIds = new ArrayList<>();
        JSONArray array = new JSONArray(requestBody);
        for(int i=0;i<array.length();i++){
            JSONObject obj = array.optJSONObject(i);
            userIds.add(obj.optLong("id"));
        }
        boardService.addUsersToBoard(boardId, userIds);
        return "users added to board";
    }

    @DeleteMapping("/board/{id}/users")
    public String removeUsersFromBoard(@PathVariable(name = "id") Long boardId, String requestBody){
        List<Long> userIds = new ArrayList<>();
        JSONArray array = new JSONArray(requestBody);
        for(int i=0;i<array.length();i++){
            JSONObject obj = array.optJSONObject(i);
            userIds.add(obj.optLong("id"));
        }
        boardService.removeUsersFromBoard(boardId, userIds);
        return "users removed from board";
    }

    @GetMapping("/board/{id}/users")
    public List<User> getBoardUsers(@PathVariable(name = "id") Long boardId){
        return boardService.getBoardUsers(boardId);
    }

    //TODO
    //get all boards for current user

    //TODO remove getAllBoards later
    @GetMapping("/boards")
    public List<Board> getAllBoards(){
        return boardService.getAllBoards();
    }


}
