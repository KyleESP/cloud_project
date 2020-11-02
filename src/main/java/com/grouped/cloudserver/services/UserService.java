package com.grouped.cloudserver.services;

import com.grouped.cloudserver.models.User;
import com.grouped.cloudserver.repositories.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){return userRepository.findAll();}

    public User getUser(Integer id){return userRepository.findById(id).get();}



}
