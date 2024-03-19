package com.wbstream.whiteboardstream.controller;

import com.wbstream.whiteboardstream.pojo.User;
import com.wbstream.whiteboardstream.repo.UserRepo;
import org.json.HTTP;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Long id){
        Optional<User> user = userRepo.findById(id);
        return new ResponseEntity<>(user.orElse(null), HttpStatus.OK);
//        return user.orElse(null);
    }

    @PostMapping(value = "/user")
    public ResponseEntity<Map> createUser(@RequestParam(name = "name") String name){
        User u = userRepo.save(new User(name));
        JSONObject res = new JSONObject();
        res.put("id", u.getId());
        res.put("name", u.getName());
        System.out.println(res.toString());
        return new ResponseEntity<>(res.toMap(), HttpStatus.CREATED);
    }

    @DeleteMapping(value="/user/{id}")
    public String deleteUser(@PathVariable  Long id){
        userRepo.deleteById(id);
        return "user deleted";
    }
}
