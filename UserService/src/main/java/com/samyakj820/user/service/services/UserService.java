package com.samyakj820.user.service.services;

import com.samyakj820.user.service.entities.User;

import java.util.List;

public interface UserService {

    //create user
    User saveUser(User user);

    //get all users
    List<User> getAllUser();

    //get single user by userId
    User getUser(String userId);

    //delete user
    void deleteUser(String userId);

    //update User
    User updateUser(User user);

}
