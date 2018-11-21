package com.demo.controller;

import com.demo.dao.TransactionRepository;
import com.demo.entity.Transactions;
import com.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/transaction")
public class TransactionController {


    @Autowired
    private TransactionRepository transactionRepository;




    @RequestMapping(method = RequestMethod.GET,produces = { "application/json"})
    public ResponseEntity<?> getAllTransaction(){

        // which user is requesting
        statsCollection.statsd.incrementCounter("transaction.get");


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        List<Transactions> allTransactions = transactionRepository.findByUser(new User(name,""));



        return ResponseEntity.ok(allTransactions);
    }


    @RequestMapping(method = RequestMethod.POST,produces = { "application/json"} )
    public ResponseEntity<?>  createTransaction(@RequestBody Transactions transactions) {

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        statsCollection.statsd.incrementCounter("transaction.post");


        transactions.setUuid(String.valueOf(UUID.randomUUID()));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        transactions.setUser(new User(name,""));

        transactions.setDate(dateFormat.format(date));

        transactionRepository.save(transactions);


        String temp_uuid = transactions.getUuid();

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.TEXT_PLAIN)
                .body("{  \"response\" : \""+temp_uuid+"\" }");


    }


    @RequestMapping(value="{uuid}",method = RequestMethod.PUT )
    public ResponseEntity<?>  putTransaction(@PathVariable("uuid") String uuid, @RequestBody Transactions transactions) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        statsCollection.statsd.incrementCounter("transaction.put");


        Transactions test = transactionRepository.findByUuidAndUser(uuid,new User(name,""));

        if(test == null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("{  \"response\" : \"not_authorized\" }");
        }

        System.out.println(uuid);
        transactions.setUuid(uuid);
        transactions.setUser(new User(name,""));
        transactionRepository.save(transactions);

        return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("{  \"response\" : \"modified\" }");


    }


    @RequestMapping(value="{uuid}",method = RequestMethod.DELETE)
    public ResponseEntity<?>  deleteTransaction(@PathVariable("uuid") String uuid) {

        Transactions transactions = new Transactions();
        transactions.setUuid(uuid);


        statsCollection.statsd.incrementCounter("transaction.delete");

        transactionRepository.delete(transactions);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.TEXT_PLAIN)
                .body("{  \"response\" : \"deleted\" }");


    }


}
