package com.password_managment.utils;

public class Functions {

    public String capitalize(String inputString) {
        if (inputString == null || inputString.isEmpty()) {
            return inputString;
        }

        // Obtener el primer carácter y convertirlo a mayúscula
        char firstLetter = Character.toUpperCase(inputString.charAt(0));

        // Concatenar el primer carácter en mayúscula con el resto de la cadena
        return firstLetter + inputString.substring(1);
    }
}
