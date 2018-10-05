package com.demo.dao;

import com.demo.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



@Repository
public class UserDao {

    EntityManager entityManager;

    private  static Map<Integer, User> Users;


    static{
        Users = new HashMap<Integer, User>(){

            {
                put(1,new User(1,"first","awekjr"));
                put(2,new User(1,"second","awekjr"));
                put(3,new User(1,"third","awekjr"));
            }
        };

    }



    public Collection<User> getAllUsers(){
        return this.Users.values();
    }

    public User create(User user) {

        entityManager.merge(user);
        entityManager.getTransaction().commit();
        return user;
    }
}
