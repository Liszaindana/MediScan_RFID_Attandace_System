package com.mycompany.mediscan.services;

import com.mycompany.mediscan.dao.GenericDAO;
import com.mycompany.mediscan.objects.LogAbsensi;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class LogAbsensiService {
    // Inisialisasi DAO untuk koleksi "log_absensi" [7]
    private final GenericDAO<LogAbsensi> logDAO = new GenericDAO<>("log_absensi", LogAbsensi.class);
    
    /**
     * Menyimpan log absensi baru ke database.
     * @param hashedUid UID RFID yang sudah di-hash SHA-256
     * @param status Status absensi ("IN" / "OUT")
     */
    public void simpanLog(String hashedUid, String status) {
        LogAbsensi log = new LogAbsensi(
            UUID.randomUUID().toString(), 
            hashedUid, 
            LocalDateTime.now(), 
            status
        );
        logDAO.save(log); // Menyimpan ke MongoDB [7]
    }

    /**
     * Mengambil seluruh data log absensi dari database.
     * @return List semua LogAbsensi
     */
    public List<LogAbsensi> getAllLog() {
        return logDAO.findAll();
    }
}
