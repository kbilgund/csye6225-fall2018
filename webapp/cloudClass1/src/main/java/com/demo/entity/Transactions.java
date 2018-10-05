package com.demo.entity;



import javax.persistence.*;

@Entity
public class Transactions {



    @Id
    @Column(nullable = false)
    private String uuid;

    private String description;

    private String category;

    @ManyToOne
    @JoinColumn(name="name")
    private User user;


    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String merchant;
    private float amount;
    private String date;






    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public Transactions(String uuid, String description, String category) {
        this.uuid = uuid;
        this.description = description;
        this.category = category;
    }

    public Transactions(){}

}
