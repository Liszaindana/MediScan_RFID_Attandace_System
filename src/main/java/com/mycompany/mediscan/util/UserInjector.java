/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mediscan.util;

import com.mycompany.mediscan.services.AuthService;

/**
 *
 * @author sy
 */
public class UserInjector {
    public static void main(String[] args) {
        AuthService userService = new AuthService();
        userService.registerUser("Mediscan", "admin", "admin"); 
    }
}
