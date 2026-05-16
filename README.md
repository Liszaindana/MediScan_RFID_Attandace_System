# MediScan - RFID Attendance System 🛡️💳

MediScan adalah sistem absensi modern berbasis Java Desktop yang menggunakan teknologi **RFID (Radio Frequency Identification)** untuk pencatatan kehadiran karyawan secara real-time. Sistem ini dirancang dengan antarmuka (UI) yang premium dan terintegrasi dengan database **MongoDB**.

## 🚀 Fitur Utama
- **Sistem Login Keamanan**: Autentikasi admin untuk mengakses dashboard manajemen.
- **Dashboard Interaktif**: Navigasi pusat yang modern dan bersih.
- **Manajemen Karyawan (Admin)**: 
    - Tambah data karyawan baru.
    - Update informasi karyawan.
    - Hapus data karyawan.
    - Pencarian data karyawan secara instan.
- **Mode Absensi (Real-time Scanning)**: 
    - Standby scanning kartu RFID.
    - Menampilkan foto dan detail karyawan saat kartu di-tap.
    - Dark mode UI untuk kenyamanan operasional.
- **Integrasi Database NoSQL**: Penyimpanan data yang fleksibel dan cepat menggunakan MongoDB.

## 🛠️ Teknologi yang Digunakan
- **Bahasa Pemrograman**: Java (JDK 17+)
- **Framework GUI**: Java Swing (FlatLaf/Nimbus Look & Feel)
- **Database**: MongoDB (NoSQL)
- **Dependency Management**: Maven
- **Hardware Integration**: `jSerialComm` (untuk komunikasi dengan sensor RFID/Arduino)

## 📋 Prasyarat Sistem
Sebelum menjalankan projek ini, pastikan Anda telah menginstal:
1.  **Java Development Kit (JDK)** versi 17 atau lebih baru.
2.  **MongoDB Community Server** (berjalan di `localhost:27017`).
3.  **MongoDB Compass** (opsional, untuk melihat data secara visual).
4.  **Hardware RFID Reader** (misal: MFRC522 terhubung ke Arduino/PC).

## ⚙️ Instalasi & Setup
1.  **Clone atau Download Projek** ke dalam direktori lokal Anda.
2.  **Pastikan MongoDB Berjalan**: 
    - Jalankan service MongoDB Anda.
    - Buat database baru bernama `mediscan`.
3.  **Buka Projek di IDE**:
    - Gunakan **NetBeans** atau **IntelliJ IDEA**.
    - Biarkan Maven mengunduh semua library yang diperlukan (Clean and Build).
4.  **Konfigurasi Koneksi**:
    - Cek file `projek.util.MongoManager` untuk memastikan URI koneksi sudah sesuai.

## 🚀 Cara Menjalankan
1.  Jalankan file `src/main/java/projek/mediscan/MediScan.java` sebagai titik awal (Main Class).
2.  Login menggunakan kredensial default:
    - **Username**: `admin`
    - **Password**: `admin`
3.  Pilih menu **Kelola Karyawan** untuk mengisi data awal, atau **Mode Absensi** untuk mulai scanning.

---

**Developer**: Lisza Indana & Team  
**Versi**: 1.0.0 (Stable)
