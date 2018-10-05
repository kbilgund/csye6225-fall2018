package com.demo.dao;

import com.demo.entity.Transactions;
import com.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transactions, String> {


    List<Transactions> findByUser(User user);

    //List<Transactions> find(String uuid);

    Transactions findByUuid(String uuid);

    Transactions findByUuidAndUser(String uuid,User user);

}

