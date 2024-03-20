package com.wbstream.whiteboardstream.aspect;

import com.wbstream.whiteboardstream.exceptions.APIException;
import com.wbstream.whiteboardstream.pojo.User;
import com.wbstream.whiteboardstream.repo.UserRepo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Aspect
public class BoardAspects {

    @Autowired
    UserRepo userRepo;

    User user;
    JSONObject reqBody;

    public User getUser() {
        return this.user;
    }

    public JSONObject getReqBody(){
        return this.reqBody;
    }

    @Before("execution(* com.wbstream.whiteboardstream.controller.BoardController.createBoard(..)) && args(requestBody)")
    public void validateBoardCreation(String requestBody) throws Throwable {

        JSONObject reqBody = new JSONObject(requestBody);
        if(reqBody.isEmpty()){
            throw new APIException("Request body is empty");
        }
        if(reqBody.optString("user").isEmpty()){
            throw new APIException("user details missing to create a board");
        }
        if(reqBody.optString("name").isEmpty()){
            throw new APIException("Board name missing");
        }
        Long userId = reqBody.optLong("user");
        Optional<User> u = userRepo.findById(userId);
        if(!u.isPresent()){
            throw new APIException("user not found");
        }
        this.user = u.get();
        this.reqBody = reqBody;
    }
}
