package com.bridgelabzs.login.model;

public interface DatabaseManager {

    public boolean addUser(User user);
    public boolean authenticateUser(String email, String password);
}
