package com.smart.entities;

import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Name should not blank!!")
    @Size(min = 2 ,max=20,message = "min 2 and max 20 characters are allowed!!")
    private String name;
    
    @Column(unique = true)
    @Pattern(regexp=".+@.+\\.[a-z]+", message="Invalid email address!")
    private String email;

    @NotBlank(message = "Password should not blank!!")
    private String password;
    private String role;
    private String imageUrl;
    private boolean enabled;
    @Column(length = 500)
    @Size(min = 1,max=500,message = "Tell about yourself!!")
    private String about;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "user")
    private List<Contact> contacts=new ArrayList<>();
    public User(){

    }
    public List<Contact> getContacts() {
        return contacts;
    }
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
    public User( String name, String email, String password, String role, String imageUrl,
            String about) {
      
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.imageUrl = imageUrl;
        this.about = about;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
                + ", imageUrl=" + imageUrl + ", enabled=" + enabled + ", about=" + about + "]";
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }

    
}
