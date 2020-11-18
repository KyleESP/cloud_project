package com.grouped.cloudserver.controllers;

import com.grouped.cloudserver.models.User;
import com.grouped.cloudserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableSpringDataWebSupport
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    User getUserById(@PathVariable("id") String idUser) {
        return userService.getUser(idUser);
    }

    @GetMapping(params = {"page"})
    List<User> getUsers(@RequestParam("page") int N) {
        return userService.getUsers(PageRequest.of(N, 100)).getContent();
    }

    @GetMapping()
    List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    ResponseEntity<Object> newUser(@RequestBody User newUser){
        userService.addUser(newUser);
        return ResponseEntity.created(null).body(newUser);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@PathVariable("id") String idUser, @RequestBody User updatedUser){
        userService.updateUser(idUser, updatedUser);
        //getUserById(updatedUser.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    ResponseEntity<List<User>> updateAllUsers(@RequestBody List<User> updatedUsers){
        deleteAllUsers();
        userService.addUsers(updatedUsers);
        return ResponseEntity.created(null).body(updatedUsers);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") String idUser){
        userService.deleteUser(idUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    ResponseEntity<?> deleteAllUsers(){
        userService.deleteAllUsers();
        return ResponseEntity.ok().build();
    }
}
