package projek.util;

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
            
            // 3. Memasukkan data dummy agar database muncul di Compass
            System.out.println("Mencoba memasukkan data dummy...");
            database.getCollection("test_koneksi").insertOne(new Document("status", "aktif").append("pesan", "Database berhasil dibuat!"));
            System.out.println("Data dummy berhasil dimasukkan.");

            // 3b. Mendaftarkan user default jika belum ada
            Document adminFilter = new Document("username", "admin");
            long adminCount = database.getCollection("users").countDocuments(adminFilter);
            if (adminCount == 0) {
                System.out.println("User 'admin' belum ada. Membuat user default...");
                Document adminUser = new Document()
                        .append("fullname", "Administrator")
                        .append("username", "admin")
                        .append("password", "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918") // Hash SHA-256 dari "admin"
                        .append("lastLogin", null);
                database.getCollection("users").insertOne(adminUser);
                System.out.println("User default 'admin' dengan password 'admin' berhasil didaftarkan!");
            } else {
                System.out.println("User 'admin' sudah terdaftar di database.");
            }

            // 4. Menampilkan daftar koleksi yang tersedia
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
