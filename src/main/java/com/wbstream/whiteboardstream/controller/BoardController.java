package com.wbstream.whiteboardstream.controller;

import com.wbstream.whiteboardstream.aspect.BoardAspects;
import com.wbstream.whiteboardstream.exceptions.APIException;
import com.wbstream.whiteboardstream.pojo.User;
import com.wbstream.whiteboardstream.service.BoardService;
import org.json.HTTP;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
