package com.smart.entities;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "CONTACT")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cId;

    @NotBlank(message = "Name should not blank!!")
    private String name;

    private String email;
    private String secondName;
    private String work;
    private String phone;
    private String image;

    private String description;

    @ManyToOne
    @Autowired
    private User user;

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
         super.equals(obj);
         return this.cId==((Contact)obj).getcId();
    }

    @Override
    public String toString() {
        return "Contact [cId=" + cId + ", name=" + name + ", email=" + email + ", secondName=" + secondName + ", work="
                + work + ", phone=" + phone + ", image=" + image + ", description=" + description + ", user=" + user
                + "]";
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
