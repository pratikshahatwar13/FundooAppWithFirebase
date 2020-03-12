package com.bridgelabzs.login;

import android.content.Context;

import com.bridgelabzs.login.model.User;
import com.bridgelabzs.login.model.UserDatabaseManager;

public class ViewModel {
    String email;
    String password;
    UserDatabaseManager userDbManager;


    public ViewModel(Context context) {
        userDbManager = new UserDatabaseManager(context);
    }

    public ViewModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean addUser(User user) {
        return userDbManager.addUser(user);
    }

    public boolean authenticateUser(String email, String password) {
        return userDbManager.authenticateUser(email, password);
    }
}
