package com.bridgelabzs.common;

public class ValidationHelper {

    public static boolean validateDescription(String description)
    {
        if(description.isEmpty()){
            return false;
        }
        else
        {
            return true;
        }
    }

    public static boolean validateTitle(String title)
    {
        if(title.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
}
