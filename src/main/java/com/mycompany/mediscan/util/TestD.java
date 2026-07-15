package com.mycompany.mediscan.util;
public class TestD {   
    public static void main(String[] args) {
        String pwd = EncryptionUtils
                .encrypt("123");
        System.out.println(pwd);
    }
}
