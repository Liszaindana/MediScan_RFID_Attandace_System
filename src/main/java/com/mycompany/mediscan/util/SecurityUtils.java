/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mediscan.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



/**
 *
 * @author sy
 */
public class SecurityUtils {
    // Konstanta pilihan algoritma SHA yang tersedia di JCA [3]
    public static final String SHA_1 = "SHA-1";
    public static final String SHA_224 = "SHA-224";
    public static final String SHA_256 = "SHA-256";
    public static final String SHA_384 = "SHA-384";
    public static final String SHA_512 = "SHA-512";
    
    /**
     * Method untuk menghasilkan nilai hash dari sebuah teks berdasarkan algoritma pilihan.
     * 
     * @param input Teks mentah (password/UID RFID)
     * @param algorithm Pilihan algoritma (gunakan konstanta statis di atas)
     * @return String hasil hash dalam format Hexadecimal
     */
    public static String getHash(String input, String algorithm) {
        try {
            // Mengambil instance MessageDigest berdasarkan algoritma yang dipilih
            MessageDigest md = MessageDigest.getInstance(algorithm);
            
            // Proses hashing
            byte[] hashBytes = md.digest(input.getBytes());
            
            // Konversi byte array ke Hexadecimal String
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            // Log error jika algoritma tidak terdaftar di runtime JCA [4]
            System.err.println("Kesalahan: Algoritma " + algorithm + " tidak didukung. " + e.getMessage());
            return null;
        }
    }
    
}
