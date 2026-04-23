/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projek.object;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author mnish
 */
/**
 * Mengelola koneksi database NoSQL (Pertemuan 5)
 */
public class MongoManager {
    private static MongoClient mongoClient;
    private static final String DATABASE_NAME = "bank_absensi";

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            // Menggunakan default connection string untuk MongoDB versi 5.0.0
            mongoClient = MongoClients.create("mongodb://localhost:27017");
        }
        return mongoClient.getDatabase(DATABASE_NAME);
    }
}