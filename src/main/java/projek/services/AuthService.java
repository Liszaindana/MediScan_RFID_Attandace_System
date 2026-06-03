package projek.services;

import projek.object.User;
import projek.DAO.GenericDAO;
import projek.GUI.AdminPage; // Halaman tujuan
import projek.GUI.LoginPage;
import projek.util.SecurityUtils;
import com.mongodb.client.model.Filters;
import java.awt.Frame;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;

/**
 *
 * @author mnish
 */
public class AuthService {

    // Inisialisasi DAO untuk koleksi "users" [8]
    private final GenericDAO<User> userDAO = new GenericDAO<>("users", User.class);

    /**
     * Melakukan proses login dengan memvalidasi kredensial (Sub-CPMK 4) [5].
     *
     * @param username
     * @param plainPassword
     * @param loginPage
     */
    public void login(String username, String plainPassword, LoginPage loginPage) {
        // 1. Mengubah password input menjadi hash SHA-256 untuk keamanan [2]
        String hashedInput = SecurityUtils.getHash(plainPassword, SecurityUtils.SHA_256);

        // 2. Mencari user di database berdasarkan username DAN password hash [7, 9]
        User user = userDAO.findOne(Filters.and(
                Filters.eq("username", username),
                Filters.eq("password", hashedInput)
        ));

        // 3. Validasi hasil pencarian
        if (user != null) {
            // Update waktu login terakhir
            user.setLastLogin(LocalDateTime.now());
            userDAO.update(Filters.eq("username", username), user);

            // Berhasil: Masuk ke Halaman Admin
            JOptionPane.showMessageDialog(null, "Selamat Datang, " + user.getFullname());
            AdminPage admPage = new AdminPage();
            admPage.setLocationRelativeTo(null); 
            admPage.setVisible(true);
            admPage.setExtendedState(Frame.MAXIMIZED_BOTH); 
            loginPage.setVisible(false); 
        } else {
            // Gagal: Notifikasi Error
            JOptionPane.showMessageDialog(null,
                    "Username atau Password Salah!",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metode untuk menambahkan user/admin baru ke database. Implementasi sesuai
     * target SPRINT 3 untuk pengamanan kredensial [2].
     *
     * @param fullname Nama lengkap user
     * @param username Username untuk login
     * @param plainPassword Password mentah (akan di-hash otomatis)
     */
    public void registerUser(String fullname, String username, String plainPassword) {
        // 1. Proses Hashing: Mengamankan password mentah menggunakan SHA-256 [1]
        String hashedPassword = SecurityUtils.getHash(plainPassword, SecurityUtils.SHA_256);

        // 2. Instansiasi Objek: Membuat objek User baru dengan password yang sudah di-hash
        // lastLogin disetel null karena user baru belum pernah masuk sistem
        User newUser = new User(fullname, username, hashedPassword, null);

        // 3. Operasi Create: Menyimpan dokumen user ke koleksi MongoDB melalui GenericDAO [3], [4]
        try {
            userDAO.save(newUser); // Memanggil insertOne melalui GenericDAO [5]
        } catch (Exception e) {
            // Standar Debugging: Mengidentifikasi error log secara mandiri [6]
            JOptionPane.showMessageDialog(null, "Gagal mendaftarkan user: " + e.getMessage());
        }
    }
}
