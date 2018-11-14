package com.demo.AWSStorage;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.File;

public class s3upload {

    public static String uploadTest(File file,String name){

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_2)
                .build();

        s3client.putObject(
                "attachmentcsye6225",
                name,
                file
        );

       String fileLink =  s3client.getUrl("attachmentcsye6225", name).toExternalForm();


       return fileLink;



    }


    public static int deleteTest(){



        return 0;
    }


}

