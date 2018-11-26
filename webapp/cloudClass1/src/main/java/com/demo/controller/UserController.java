package com.demo.controller;

import com.demo.dao.UserDao;
import com.demo.dao.UserRepository;
import com.demo.entity.User;
import com.demo.service.UserService;
import com.demo.storage.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.timgroup.statsd.StatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClient;




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

   // private static final StatsDClient statsd = new NonBlockingStatsDClient("my.prefix", "statsd-host", 8125);



    @RequestMapping(method = RequestMethod.GET )
    public ResponseEntity<?> getAllUsers(){

        LocalDateTime currentTime = LocalDateTime.now();

     //   return String.valueOf(currentTime);

        FileSystemStorageService.testupload();
        return ResponseEntity.ok(currentTime);
    }

    @RequestMapping(value="/health",method = RequestMethod.GET )
    public ResponseEntity<?> health(){

        LocalDateTime currentTime = LocalDateTime.now();

        FileSystemStorageService.testupload();
        return ResponseEntity.ok(currentTime);
    }

    @RequestMapping(value="/register",method = RequestMethod.POST )
    public String createUser(@RequestBody User user) {
        statsCollection.statsd.incrementCounter("register");

        System.out.println("debug"+user.getName());
        if(userRepository.existsById(user.getName()) == true){
            System.out.println("User exists");
            return "{  \"response\" : \"User already exists!!!\" }";

        }
        else {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            User user_1 = new User(user.getName(), encodedPassword);
            userRepository.save(user_1);
            return "{ \"email\" : \"user.getName()\", \"response\" : \"User Added\" }";
        }


    }

}
