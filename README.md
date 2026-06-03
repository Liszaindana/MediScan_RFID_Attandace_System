# 🏥 MediScan - RFID Hospital Attendance System 🛡️💳

MediScan adalah sistem presensi pintar modern berbasis Java Desktop yang dirancang khusus untuk lingkungan operasional Rumah Sakit. Sistem ini mengintegrasikan teknologi **RFID (Radio Frequency Identification)** secara real-time dengan basis data **MongoDB NoSQL**, dibungkus dalam antarmuka antarmuka yang sangat premium, adaptif, dan berstandar rumah sakit tingkat tinggi.

---

## 🎨 Modern Hospital UI & Design System

Aplikasi ini telah dimodernisasi secara menyeluruh menggunakan prinsip **Design Thinking** untuk memberikan kenyamanan maksimal bagi staf administrasi rumah sakit dan menghindari kelelahan mata (*visual fatigue*):
*   **Medical Color Palette**: Dominasi warna **Hospital Blue (`#005382`)** yang melambangkan keandalan medis, **Teal/Cyan Accent (`#00BCD4`)** sebagai representasi teknologi kesehatan modern, serta latar belakang netral **Soft Blue (`#ECF5FF`)** untuk estetika yang sejuk.
*   **Programmatic Vector Icons**: Semua ikon navigasi di sidebar digambar secara langsung (rendering vector anti-alias Java 2D) sehingga tampil tajam di resolusi apa pun dan 100% kompatibel di sistem operasi Windows tanpa error font square `□`.
*   **Breathing Space Layout**: Penerapan sistem tata letak (*padding*) longgar di sekeliling daftar komponen dan margin terstruktur untuk memudahkan navigasi.
*   **Dynamic Interactive Cards & Forms**: Input form bergaya modern di sebelah kiri dengan highlight border berwarna cyan ketika aktif, serta daftar staf medis di kanan yang terstruktur rapi.
*   **Real-time Digital Clock**: Dilengkapi penunjuk waktu real-time digital presisi tinggi di bagian header utama untuk mencatat momen presensi secara visual.
*   **Dynamic Status Bar**: Indikator statistik yang diperbarui secara langsung di bagian bawah tabel untuk menampilkan total karyawan terdaftar, serta indikator status koneksi MongoDB.

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

## 🔒 Keamanan & Enkripsi Hashing (SHA-256)

Aplikasi menerapkan pengamanan kredensial terenkripsi satu arah menggunakan algoritma **SHA-256** untuk mematuhi standar privasi data rumah sakit:

### 1. Struktur Komponen Keamanan
*   **`projek.util.SecurityUtils`**: Kelas utilitas yang menyediakan metode `getHash(input, algorithm)`. Berfungsi mengonversi string password mentah menjadi representasi hash hex 64-karakter yang tidak dapat dikembalikan ke bentuk semula.
*   **`projek.services.AuthService`**: Mengontrol logika login dan registrasi.
    *   **Saat Registrasi (`registerUser`)**: Password mentah dari form pendaftaran di-hash terlebih dahulu menggunakan `SecurityUtils.getHash(password, "SHA-256")` sebelum disimpan ke database.
    *   **Saat Login (`login`)**: Password yang dimasukkan oleh pengguna di-hash, lalu dicocokkan dengan hash yang ada di MongoDB melalui query `Filters.and(Filters.eq("username", username), Filters.eq("password", hashedInput))`.
*   **`projek.object.User`**: POJO (Plain Old Java Object) representasi entitas pengguna yang menjaga nilai password selalu dalam bentuk hash terenkapsulasi.
*   **`projek.util.TesKoneksi`**: Secara otomatis menginjeksi akun `admin` default ke database dengan password ter-hash (`8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918`) apabila koleksi `users` terdeteksi kosong saat inisialisasi awal.

### 2. Cara Verifikasi Hashing Sukses
*   **Melalui MongoDB Compass / Shell**:
    1. Hubungkan ke database MongoDB Anda (URI: `mongodb://localhost:27017`).
    2. Buka database `mediscan` dan masuk ke koleksi `users`.
    3. Anda akan melihat data user `admin` tersimpan dengan field `"password": "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918"`. Password mentah tidak pernah disimpan langsung.

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
**Versi**: 1.2.0 (Hospital-Grade Vector Stable)
