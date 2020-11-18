package com.grouped.cloudserver.services;

import com.grouped.cloudserver.exceptions.ResourceNotFoundException;
import com.grouped.cloudserver.models.User;
import com.grouped.cloudserver.repositories.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<User> getUsers(Pageable PageRequest) {
        return userRepository.findAll(PageRequest);
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
           if(newUser.getId() == null || "".equals(newUser.getId())) {
                newUser.setId(UUID.randomUUID().toString());
            }
        }
        userRepository.saveAll(newUsers);
    }

    public void updateUser(String id, User user){
        User oldUser = getUser(id);
        user.setId(id);
        if(user.getFirstName() == null) user.setFirstName(oldUser.getFirstName());
        if(user.getLastName() == null)  user.setLastName(oldUser.getLastName());
        if(user.getBirthDay() == null)  user.setBirthDay(oldUser.getBirthDay());
        if(user.getPosition() == null)  user.setPosition(oldUser.getPosition());
        userRepository.save(user);
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

}
