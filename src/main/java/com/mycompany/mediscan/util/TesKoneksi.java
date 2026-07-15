package com.mycompany.mediscan.util;

import com.mycompany.mediscan.util.MongoManager;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class TesKoneksi {
    public static void main(String[] args) {
        try {
            System.out.println("Sedang mencoba menghubungkan ke database...");
            
            // 1. Memanggil koneksi melalui MongoManager
            MongoDatabase database = MongoManager.getDatabase();
            
            // 2. Melakukan perintah "ping" untuk verifikasi koneksi ke server
            // Ini adalah standar teknis untuk memastikan handshake berhasil [1].
            Document ping = new Document("ping", 1);
            database.runCommand(ping);
            
            System.out.println("=========================================");
            System.out.println("STATUS: KONEKSI BERHASIL!");
            System.out.println("Terhubung ke Database: " + database.getName());
            System.out.println("=========================================");
            
            // 3. Opsional: Menampilkan daftar koleksi yang tersedia
            System.out.println("Daftar Koleksi di " + database.getName() + ":");
            for (String name : database.listCollectionNames()) {
                System.out.println("- " + name);
            }

        } catch (Exception e) {
            // Standar Debugging: Membaca log exception secara mandiri [3, 4].
            System.err.println("=========================================");
            System.err.println("STATUS: KONEKSI GAGAL!");
            System.err.println("Pesan Error: " + e.getMessage());
            System.err.println("=========================================");
        }
    }
}
