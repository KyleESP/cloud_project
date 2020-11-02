package com.grouped.cloudserver.services;

import com.grouped.cloudserver.exceptions.ResourceNotFoundException;
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

    public void addUser(User newUser){userRepository.save(newUser);}

    public void addUsers(List<User> newUsers){
        for(User newUser : newUsers){
            addUser(newUser);
        }
    }

    public void updateUser(int id, User user){
        if(userRepository.findById(id) == null){throw new ResourceNotFoundException();}
        userRepository.save(user);
    }

    public void deleteUser(Integer idUser){
        if(userRepository.findById(idUser) == null){throw new ResourceNotFoundException();}
        userRepository.deleteById(idUser);
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

}
