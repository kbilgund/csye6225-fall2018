package com.demo.controller;



import java.io.IOException;

import java.util.List;

import com.demo.dao.AttachmentsRepository;
import com.demo.dao.TransactionRepository;
import com.demo.entity.Attachments;
import com.demo.entity.Transactions;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


import com.demo.storage.StorageFileNotFoundException;
import com.demo.storage.StorageService;

@Controller
@RequestMapping("/transaction")
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    private AttachmentsRepository attachmentsRepository;


    @Autowired
    private TransactionRepository transactionRepository;


    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }



    @GetMapping(value="{uuid}/attachments",produces={ "application/json"})
    public ResponseEntity<?>  serveFile(@PathVariable String uuid) {

        List<Attachments> attachmentsList = attachmentsRepository.findByUuid(uuid);

        return ResponseEntity.ok(attachmentsList);
    }

    @PostMapping("{uuid}/attachments")
    public ResponseEntity<?>  handleFileUpload(@PathVariable String uuid,@RequestParam("file") MultipartFile file) {
        System.out.println("in post file");

        Attachments newAttachment = new Attachments();


        Transactions transactions = transactionRepository.findByUuid(uuid);



        String pathOfFile = storageService.store(file);
       // redirectAttributes.addFlashAttribute("message","You successfully uploaded " + file.getOriginalFilename() + "!");
        System.out.println("saved locally");
        String name = file.getOriginalFilename();
     //   URL url = getClass().getResource(name);
        System.out.println(name);
       // File file_1 = new File(url.getPath());
        //String fileLink = s3upload.uploadTest(file_1, name);

        newAttachment.setLink(pathOfFile);
        newAttachment.setUuid(transactions.getUuid());
        attachmentsRepository.save(newAttachment);


        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.TEXT_PLAIN)
                .body(name);
    }



    @RequestMapping(value="{uuid}/attachments/{idAttachment}",method = RequestMethod.DELETE)
    public ResponseEntity<?>  deleteAttachment(@PathVariable("uuid") String uuid,@PathVariable("idAttachment") int idAttachment) {

        Attachments deleteAttachment = attachmentsRepository.findById(idAttachment);

        System.out.println("debug controller "+deleteAttachment.getLink());

        Transactions transactions = new Transactions();
        transactions.setUuid(uuid);

        Attachments attachments = new Attachments();
        attachments.setId(idAttachment);


        attachmentsRepository.delete(attachments);

        storageService.deleteFile(deleteAttachment.getLink());


        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.TEXT_PLAIN)
                .body("deleted");


    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
