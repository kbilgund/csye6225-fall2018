package com.demo.entity;

import javax.persistence.*;

@Entity
public class Attachments {




    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;



    private String link;

    private String uuid;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Attachments(String link, String uuid) {
        this.link = link;
        this.uuid = uuid;
    }

    public Attachments() {

    }
}
