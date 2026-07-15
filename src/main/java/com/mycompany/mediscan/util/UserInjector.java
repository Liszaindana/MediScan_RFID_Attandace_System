package com.mycompany.mediscan.util;

import com.mycompany.mediscan.services.AuthService;
public class UserInjector {
    public static void main(String[] args) {
        AuthService userService = new AuthService();
        userService.registerUser("Mediscan", "admin", "admin"); 
    }
}
