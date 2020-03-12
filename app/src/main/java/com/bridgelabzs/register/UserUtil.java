package com.bridgelabzs.register;

public class UserUtil {

    private static UserUtil instance = null;
    private UserUtil() {

    }

    public synchronized static UserUtil getInstance() {
        if(instance == null) {
            instance = new UserUtil();
        }
        return instance;
    }

    String namePattern = "^[A-Z]{1}[a-z]{2,}$";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

    public String isNameValid(String name) {
        if (name.isEmpty()) {
            return "Field can't be empty";
        } else if (!name.matches(namePattern)) {
            return "Please enter valid name";
        } else {
            return null;
        }
    }

    public String isEmailValid(String email){
        if(email.isEmpty()){
            return "Field can't be empty";
        }else if(!email.matches(emailPattern)){
            return "Please enter valid email address";
        }else{
            return null;
        }
    }

    public  String isPasswordValid(String password){
        if(password.isEmpty()){
            return "Field can't be empty";
        }else if(!password.matches(passwordPattern)){
            return "Please enter valid password";
        }else{
            return null;
        }
    }
    /*

    public String isPasswordMatches(String matchPassword, String password){
        if(matchPassword.isEmpty()){
            return "Field can't be empty";
        }else if(!matchPassword.equals(password)){
            return "Password does not match";
        }else{
            return null;
        }
    }

     */
}
