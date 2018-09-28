package com.demo.controller;

import com.demo.dao.UserDao;
import com.demo.dao.UserRepository;
import com.demo.entity.User;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;




import java.time.LocalDateTime;



@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDao userDAO = new UserDao();

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.GET )
    public String getAllUsers(){

        LocalDateTime currentTime = LocalDateTime.now();

        return String.valueOf(currentTime);
    }

    @RequestMapping(value="/register",method = RequestMethod.POST )
    public String createUser(@RequestBody User user) {

        System.out.println("debug"+user.getName());
        if(userRepository.existsById(user.getName()) == true){
            System.out.println("User exists");
            return "User already exists!!!";

        }
        else {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            User user_1 = new User(user.getName(), encodedPassword);
            userRepository.save(user_1);
            return "User Added";
        }


    }

}