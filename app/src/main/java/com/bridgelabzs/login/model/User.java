package com.bridgelabzs.login.model;

public class User {
    private  String firstName;
    private  String lastName;
    private String emailId;
    private String password;

    public User(){

    }

    public User(String firstName, String lastName, String email , String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

