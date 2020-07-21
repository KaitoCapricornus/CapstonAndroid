package com.example.capstonandroid.entity;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String pass;
    private String email;
    private String phone;
    private String dob;

    public String getUsername() {
        return username;
    }

    public void setUser_name(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }





    public User(String username, String pass, String email, String phone, String dob) {
        this.username = username;
        this.pass = pass;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
    }
}
