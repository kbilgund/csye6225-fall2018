package com.demo.controller;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.Topic;
import com.demo.dao.UserRepository;
import com.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reset")
public class UserReset {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value="",method = RequestMethod.POST )
    public String createUser(@RequestBody User user) {

        statsCollection.statsd.incrementCounter("user.reset");


        AmazonSNS snsClient = AmazonSNSClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .build();

        System.out.println(snsClient.listTopics());

        List<Topic> allTopics = snsClient.listTopics().getTopics();


        String topicARN="";

        for (Topic t:allTopics) {

            if(t.toString().contains("csye6225-fall18")) {
                System.out.println("found!!!" + t.toString());
                topicARN = t.getTopicArn();
            }
        }

        String msg = "{\"user\":\""+user.getName()+"\"}";
        PublishRequest publishRequest = new PublishRequest(topicARN, msg);
        PublishResult publishResult = snsClient.publish(publishRequest);

        System.out.println("debug "+publishResult.getMessageId());

        return "{  \"response\" : \"password email sent\" }";
    }




}
