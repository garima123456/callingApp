package com.example.callingapp.Models;

public class User {

    public String name,email,password,phone,userId;
    public User(String name, String email, String phone,String password, String userId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone=phone;
        this.userId = userId;
    }
     public User(){}


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
