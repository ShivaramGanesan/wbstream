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
import java.util.Map;

@RestController

public class BoardController {

    @Autowired
    BoardAspects boardAspect;

    @Autowired
    BoardService boardService;

    @PostMapping(value = "/board")
    public ResponseEntity<Map> createBoard(@RequestBody String body) throws APIException {
        User u = boardAspect.getUser();
        JSONObject reqBody = boardAspect.getReqBody();
        Long boardId = boardService.createBoard(reqBody.optString("name"), u);

        JSONObject res = new JSONObject().put("id", boardId).put("message", "board created successfully").put("status", "success");
        return new ResponseEntity<>(res.toMap(), HttpStatus.CREATED);

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
    public ResponseEntity<Map> addUsersToBoard(@PathVariable(name = "id") Long boardId, @RequestBody String requestBody) throws APIException{
        if(requestBody == null || requestBody.isEmpty()){
            throw new APIException("user details missing");
        }
        List<Long> userIds = new ArrayList<>();
        JSONArray array = new JSONArray(requestBody);
        for(int i=0;i<array.length();i++){
            JSONObject obj = array.optJSONObject(i);
            userIds.add(obj.optLong("id"));
        }
        List<Long> data = boardService.addUsersToBoard(boardId, userIds);
        JSONObject res = new JSONObject();
        res.put("status", "success");
        res.put("data", data.toArray());
        return new ResponseEntity<>(res.toMap(), HttpStatus.OK);
    }

    @DeleteMapping("/board/{id}/users")
    public ResponseEntity<Map> removeUsersFromBoard(@PathVariable(name = "id") Long boardId, @RequestBody String requestBody){
        List<Long> userIds = new ArrayList<>();
        JSONArray array = new JSONArray(requestBody);
        for(int i=0;i<array.length();i++){
            JSONObject obj = array.optJSONObject(i);
            userIds.add(obj.optLong("id"));
        }
        boardService.removeUsersFromBoard(boardId, userIds);
        JSONObject res = new JSONObject();
        res.put("status", "success").put("message", "users removed from board");
        return new ResponseEntity<>(res.toMap(), HttpStatus.OK);
//        return "users removed from board";
    }

    @GetMapping("/board/{id}/users")
    public ResponseEntity<List<User>> getBoardUsers(@PathVariable(name = "id") Long boardId){
        return new ResponseEntity<>(boardService.getBoardUsers(boardId), HttpStatus.OK);
    }

    //TODO
    //get all boards for current user

    //TODO remove getAllBoards later
    @GetMapping("/boards")
    public List<Board> getAllBoards(){
        return boardService.getAllBoards();
    }


}
