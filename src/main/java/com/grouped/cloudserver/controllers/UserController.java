package com.grouped.cloudserver.controllers;

import com.grouped.cloudserver.models.User;
import com.grouped.cloudserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    User getUserById(@PathVariable("id") String idUser){return userService.getUser(idUser); }

    @GetMapping()
    List<User> getUsers(){return userService.getUsers();}

    @PostMapping
    ResponseEntity<Object> newUser(@RequestBody User newUser){
        userService.addUser(newUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity updateUser(@PathVariable("id") String idUser, @RequestBody User updatedUser){
        userService.updateUser(idUser, updatedUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    ResponseEntity updateAllUsers(@RequestBody List<User> updatedUsers){
        userService.deleteAllUsers();
        userService.addUsers(updatedUsers);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteUser(@PathVariable("id") String idUser){
        userService.deleteUser(idUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    ResponseEntity deleteAllUsers(){
        userService.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }

}
