# 🏥 MediScan - RFID Hospital Attendance System 🛡️💳

MediScan adalah sistem presensi pintar modern berbasis Java Desktop yang dirancang khusus untuk lingkungan operasional Rumah Sakit. Sistem ini mengintegrasikan teknologi **RFID (Radio Frequency Identification)** secara real-time dengan basis data **MongoDB NoSQL**, dibungkus dalam antarmuka antarmuka yang sangat premium, adaptif, dan berstandar rumah sakit tingkat tinggi.

---

## 🎨 Modern Hospital UI & Design System

Aplikasi ini telah dimodernisasi secara menyeluruh menggunakan prinsip **Design Thinking** untuk memberikan kenyamanan maksimal bagi staf administrasi rumah sakit dan menghindari kelelahan mata (*visual fatigue*):
*   **Medical Color Palette**: Dominasi warna **Medical Blue (`#0078AE`)** yang melambangkan keandalan medis, **Teal/Green (`#00A88F`)** sebagai representasi kesehatan, serta latar belakang netral **Soft Gray/Blue (`#F5F8FC`)** untuk estetika yang sejuk.
*   **Breathing Space Layout**: Penerapan sistem tata letak (*padding*) longgar `16px` di sekeliling daftar komponen dan margin `15px` di antara kartu karyawan untuk kemudahan interaksi.
*   **Rounded Design**: Kotak isian (*input fields*) dan kartu-kartu informasi karyawan menggunakan garis tepi halus melingkar (*rounded border*) dengan bayangan transisi yang elegan.
*   **Dynamic Interactive Cards**: Setiap daftar karyawan ditampilkan dalam bentuk kartu (*card*) putih minimalis yang dilengkapi efek hover dinamis saat kursor melintas.
*   **Disabled Guard Pattern**: Tombol **Update** terkunci secara default dan baru akan aktif ketika tombol **Edit** pada salah satu kartu karyawan diklik demi mencegah modifikasi data yang tidak disengaja.

---

## ⚙️ Generic Programming Architecture (Base & Generic DAO)

MediScan dibangun dengan arsitektur berstandar industri menggunakan **Generic Programming** pada lapisan akses data (*Data Access Object*). Ini memberikan tingkat modularitas yang sangat tinggi:

*   **Interface Generik (`BaseDAO<T>`)**: Mendeklarasikan kontrak CRUD dasar yang dapat digunakan kembali untuk objek data apa pun (`Karyawan`, `User`, dll.) tanpa keterikatan tipe data tertentu.
*   **Kelas Generik (`GenericDAO<T>`)**: Implementasi tunggal yang mengontrol integrasi driver MongoDB Sync. Otomatis memetakan dokumen BSON menjadi objek Plain Old Java Object (POJO) berdasarkan parameter kelas `<T>`.
*   **Manfaat Utama**:
    *   **DRY (Don't Repeat Yourself)**: Menghilangkan penulisan ratusan baris kode DAO yang berulang.
    *   **Type Safety**: Menjamin keamanan tipe data pada tingkat kompilasi.
    *   **Skalabilitas Tinggi**: Menambahkan entitas baru hanya memerlukan pembuatan POJO tanpa memodifikasi logika database.

---

## 🚀 Fitur Utama

1.  **Sistem Login Keamanan Terintegrasi**: Autentikasi berlapis yang aman dengan *fallback login* jika koneksi database terputus.
2.  **Dashboard Utama (RS Theme)**: Pusat navigasi interaktif dengan card menu besar beranimasi hover dan penunjuk tanggal lokal otomatis.
3.  **Manajemen Karyawan (CRUD)**:
    *   **Create**: Perekaman UID kartu RFID dan data diri karyawan secara cepat.
    *   **Read**: Pencarian instan asinkron berbasis ketikan karakter (*real-time typing search*) yang ditampilkan dalam grid responsif.
    *   **Update**: Pembaruan data karyawan yang aman dan responsif.
    *   **Delete**: Penghapusan data dengan konfirmasi yang ramah pengguna.
4.  **Mode Absensi (Real-time RFID Scanner)**:
    *   Menggunakan pustaka `jSerialComm` untuk membaca port COM sensor RFID Arduino.
    *   Penanda status dinamis: **Menunggu Kartu** (Amber), **Hadir** (Teal/Green), dan **Tidak Dikenal** (Merah).
    *   Menampilkan nama, ID, departemen, serta waktu presensi secara instan saat kartu di-tap.

---

## 🛠️ Teknologi yang Digunakan

*   **Bahasa Pemrograman**: Java (JDK 17+)
*   **Framework GUI**: Java Swing
*   **Database**: MongoDB NoSQL (MongoDB Driver Sync v5.0.0)
*   **Konektivitas Hardware**: `jSerialComm` (Komunikasi serial port RFID Reader)
*   **Manajemen Proyek**: Maven

---

## 📋 Prasyarat Sistem

Sebelum menjalankan aplikasi, pastikan Anda telah menyiapkan prasyarat berikut:
1.  **Java Development Kit (JDK)** versi 17 atau yang lebih baru.
2.  **MongoDB Community Server** yang berjalan aktif di port default `localhost:27017`.
3.  **Database & Koleksi MongoDB**:
    *   Buat database bernama: `mediscan`
    *   Buat koleksi bernama: `karyawan` dan `users`
4.  **Sensor RFID Reader** (Opsional untuk testing hardware, jika tidak ada, input serial dapat disimulasikan).

---

## ⚙️ Setup & Instalasi

1.  **Clone / Unduh Repositori** ini ke dalam komputer Anda.
2.  **Buka Projek dengan IDE Pilihan** (NetBeans, Eclipse, IntelliJ IDEA, atau VS Code).
3.  **Build Projek dengan Maven**:
    *   Maven akan mengunduh otomatis semua dependensi seperti `mongodb-driver-sync` dan `jSerialComm`.
4.  **Konfigurasi Koneksi Database**:
    *   Konfigurasi URI database terletak pada berkas `projek.util.MongoManager.java`.

---

## 🏃 Cara Menjalankan Aplikasi

1.  Jalankan kelas utama: `src/main/java/projek/mediscan/MediScan.java` sebagai entry-point aplikasi.
2.  Aplikasi akan memuat layar **Login** premium.
3.  Gunakan kredensial akses administrator default:
    *   **Username**: `admin`
    *   **Password**: `admin`
4.  Setelah masuk, Anda dapat mengelola data karyawan rumah sakit pada menu **Kelola Karyawan** atau memulai pencatatan presensi pada menu **Mode Absensi**.

---

**Developer**: Lisza Indana & Team  
**Versi**: 1.1.0 (Hospital-Grade Stable)
