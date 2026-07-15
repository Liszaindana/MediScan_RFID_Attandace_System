package com.mycompany.mediscan.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class EncryptionUtils {
    private static final String ALGORITHM = "AES";
    
    // Kunci rahasia (harus 16 karakter untuk AES-128)
    // Dalam industri nyata, kunci ini disimpan di environment variable atau KeyVault
    private static final String KEY = System.getProperty("KEY", "MediscanKey12345");
    private static final byte[] SECRET_KEY = KEY.getBytes(); 
    
    /**
     * Mengubah teks biasa menjadi teks tersandi (Enkripsi).
     * @param value
     * @return 
     */
    public static String encrypt(String value) {
        try {
            SecretKeySpec spec = new SecretKeySpec(SECRET_KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, spec);

            byte[] encryptedBytes = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (InvalidKeyException | NoSuchAlgorithmException | 
                BadPaddingException | IllegalBlockSizeException | 
                NoSuchPaddingException e) {
            System.err.println("Error saat enkripsi: " + e.getMessage());
            return null;
        }
    }

    /**
     * Mengubah teks tersandi kembali ke teks asli (Dekripsi).
     * @param encryptedValue
     * @return 
     */
    public static String decrypt(String encryptedValue) {
        try {
            SecretKeySpec spec = new SecretKeySpec(SECRET_KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, spec);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedValue);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException |
                IllegalBlockSizeException | NoSuchPaddingException e) {
            System.err.println("Error saat dekripsi: " + e.getMessage());
            return null;
        }
    }
    
}
