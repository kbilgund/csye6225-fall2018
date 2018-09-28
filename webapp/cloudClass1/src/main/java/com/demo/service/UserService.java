package com.demo.service;

import com.demo.dao.UserDao;
import com.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public Collection<User> getAllUsers(){
        return userDao.getAllUsers();
    }
}
