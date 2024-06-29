package com.password_managment.utils;

public class Functions {

    public String capitalize(String inputString) {
        if (inputString == null || inputString.isEmpty()) {
            return inputString;
        }

        char firstLetter = Character.toUpperCase(inputString.charAt(0));
        return firstLetter + inputString.substring(1);
    }
}
