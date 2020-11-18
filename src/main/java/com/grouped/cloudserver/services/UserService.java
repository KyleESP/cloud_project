package com.grouped.cloudserver.services;

import com.grouped.cloudserver.exceptions.ResourceNotFoundException;
import com.grouped.cloudserver.models.User;
import com.grouped.cloudserver.repositories.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return user.get();
    }

    public void addUser(User newUser) {
        if(newUser.getId() == null || "".equals(newUser.getId())) {
            newUser.setId(UUID.randomUUID().toString());
        }
        userRepository.save(newUser);
    }

    public void addUsers(List<User> newUsers){
        for(User newUser : newUsers){
            addUser(newUser);
        }
    }

    public void updateUser(String id, User user){
        getUser(id); // to check that the user exists
        userRepository.save(user);
    }

    public void deleteUser(String id){
        getUser(id); // to check that the user exists
        userRepository.deleteById(id);
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

}
