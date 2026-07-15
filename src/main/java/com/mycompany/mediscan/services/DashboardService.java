package com.mycompany.mediscan.services;

import com.mycompany.mediscan.dao.GenericDAO;
import com.mycompany.mediscan.objects.Karyawan;
import com.mycompany.mediscan.objects.LogAbsensi;
import java.time.LocalDate;
import java.util.List;

public class DashboardService {

    private final GenericDAO<Karyawan> daoKaryawan;
    private final GenericDAO<LogAbsensi> daoLog;

    public DashboardService() {
        daoKaryawan = new GenericDAO<>("karyawan", Karyawan.class);
        daoLog = new GenericDAO<>("log_absensi", LogAbsensi.class);
    }

    // Jumlah seluruh karyawan
    public int getJumlahKaryawan() {
        return daoKaryawan.findAll().size();
    }

    // Jumlah absensi hari ini (on-time)
    public int getTotalHadirHariIni() {
        java.util.Set<String> uniqueEmployees = new java.util.HashSet<>();
        LocalDate today = LocalDate.now();
        List<LogAbsensi> logs = daoLog.findAll();
        for (LogAbsensi log : logs) {
            if (log.getWaktuTap() != null &&
                log.getWaktuTap().toLocalDate().equals(today)) {
                
                String status = log.getStatus();
                if ("HADIR".equalsIgnoreCase(status)) {
                    uniqueEmployees.add(log.getUidRfid());
                } else if ("IN".equalsIgnoreCase(status)) {
                    // Batas masuk normal: sebelum pukul 08:00
                    if (log.getWaktuTap().getHour() < 8) {
                        uniqueEmployees.add(log.getUidRfid());
                    }
                }
            }
        }
        return uniqueEmployees.size();
    }

    // Jumlah terlambat
    public int getJumlahTerlambat() {
        java.util.Set<String> uniqueEmployees = new java.util.HashSet<>();
        LocalDate today = LocalDate.now();
        List<LogAbsensi> logs = daoLog.findAll();
        for (LogAbsensi log : logs) {
            if (log.getWaktuTap() != null &&
                log.getWaktuTap().toLocalDate().equals(today)) {
                
                String status = log.getStatus();
                if ("TERLAMBAT".equalsIgnoreCase(status)) {
                    uniqueEmployees.add(log.getUidRfid());
                } else if ("IN".equalsIgnoreCase(status)) {
                    // Terlambat jika tap IN di atas atau sama dengan pukul 08:00
                    if (log.getWaktuTap().getHour() >= 8) {
                        uniqueEmployees.add(log.getUidRfid());
                    }
                }
            }
        }
        // Jika karyawan sudah terhitung hadir (on-time) di hari yang sama, hapus dari status terlambat
        java.util.Set<String> hadirEmployees = new java.util.HashSet<>();
        for (LogAbsensi log : logs) {
            if (log.getWaktuTap() != null &&
                log.getWaktuTap().toLocalDate().equals(today)) {
                String status = log.getStatus();
                if ("HADIR".equalsIgnoreCase(status) || 
                    ("IN".equalsIgnoreCase(status) && log.getWaktuTap().getHour() < 8)) {
                    hadirEmployees.add(log.getUidRfid());
                }
            }
        }
        uniqueEmployees.removeAll(hadirEmployees);
        return uniqueEmployees.size();
    }
    
    // Jumlah karyawan yang belum presensi hari ini
    public int getBelumPresensi() {
        int jumlahKaryawan = getJumlahKaryawan();
        int hadir = getTotalHadirHariIni();
        int terlambat = getJumlahTerlambat();
        return Math.max(0, jumlahKaryawan - hadir - terlambat);
    }
}
