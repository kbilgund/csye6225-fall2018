package com.demo.dao;

import com.demo.entity.Attachments;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttachmentsRepository extends CrudRepository<Attachments, String> {


    List<Attachments> findByUuid(String uuid);

    Attachments findById(int id);
}

