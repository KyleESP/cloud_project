package com.grouped.cloudserver.controllers;

import com.grouped.cloudserver.models.User;
import com.grouped.cloudserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    User getUserById(@Param("id") Integer idUser){return userService.getUser(idUser); }

    @GetMapping()
    List<User> getUsers(){return userService.getUsers();}

}
