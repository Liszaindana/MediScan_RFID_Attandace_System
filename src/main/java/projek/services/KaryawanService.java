package projek.services;

import projek.GUI.AdminPage;
import projek.DAO.GenericDAO;
import projek.object.Karyawan;
import com.mongodb.client.model.Filters;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.bson.conversions.Bson;

public class KaryawanService {

    // Inisialisasi GenericDAO khusus untuk entitas Karyawan
    // Menggunakan koleksi "karyawan" dan referensi Class Karyawan [3]
    private final GenericDAO<Karyawan> DAO;

    public KaryawanService() {
        this.DAO = new GenericDAO<>("karyawan", Karyawan.class);
    }

    /**
     * 1.CREATE: Fungsi untuk menyimpan data karyawan baru ke MongoDB [2], [3]
     *
     * @param karyawanBaru
     */
    public void tambahKaryawan(Karyawan karyawanBaru) {
        DAO.save(karyawanBaru); // Memanggil insertOne melalui GenericDAO [3]
    }

    public void tambahKaryawan(String uidRfid, String idKaryawan, String namaLengkap, String departemen) {
        Karyawan karyawanBaru = new Karyawan(uidRfid, idKaryawan, namaLengkap, departemen);
        DAO.save(karyawanBaru); // Memanggil insertOne melalui GenericDAO [3]
    }

    /**
     * 2. READ (All): Fungsi untuk mengambil semua data karyawan [5], [6]
     */
    public void tampilkanDaftarKaryawan() {
        List<Karyawan> daftar = DAO.findAll();
        System.out.println("--- Daftar Karyawan Bank ---");
        for (Karyawan k : daftar) {
            System.out.println(k.toString()); // Menggunakan format toString di sumber [7]
        }
    }

    /**
     * 2.READ (All): Fungsi untuk mengambil semua data karyawan [5], [6]
     *
     * @param panelTarget
     * @param key
     */
    public void tampilKaryawan(JPanel panelTarget, String key) {
        //1. 
        // Menampilkan data berdasarkan request
        // key "null/kosong" = get all data
        // key "filled" = get specific data

        List<Karyawan> daftarKaryawan;
        if (key.isEmpty()) {
            //Mengambil data dari database menggunakan GenericDAO
            daftarKaryawan = DAO.findAll();
        } else {
            //Mengambil data dari database menggunakan GenericDAO
            //berdasarkan kata kunci yang diketik
            daftarKaryawan = cariKaryawan(key);
        }
        // 2. Membersihkan panel target utama sebelum memuat data baru
        panelTarget.removeAll();

        // Mengubah layout panel target menjadi BorderLayout
        panelTarget.setLayout(new BorderLayout());
        // Mengatur warna background utama menjadi abu-abu terang modern (Slate 100)
        panelTarget.setBackground(new Color(241, 245, 249));

        // Membuat panel grid khusus untuk menampung kotak/card
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        gridPanel.setOpaque(false); // Transparan agar warna abu-abu panelTarget terlihat
        gridPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Memberi jarak dari tepi layar

        // 3. Iterasi data dan menambahkannya ke panel grid
        try {
            for (Karyawan k : daftarKaryawan) {
                // Membuat panel 'Card' (box putih) untuk 1 karyawan
                // Layout 4 baris 1 kolom
                JPanel cardPanel = new JPanel(new GridLayout(4, 1, 0, 5));
                cardPanel.setBackground(Color.WHITE); // Warna background putih bersih

                // Memberikan garis tepi tipis abu-abu (rounded) dan padding ke dalam
                cardPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true), // Slate 200
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));

                // Membuat Label Nama (Bold, Slate 900)
                JLabel lblNama = new JLabel("Nama: " + k.getNamaLengkap());
                lblNama.setForeground(new Color(15, 23, 42));
                lblNama.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

                // Membuat Label ID Karyawan (Slate 600)
                JLabel lblIDK = new JLabel("ID Karyawan: " + k.getIdKaryawan());
                lblIDK.setForeground(new Color(71, 85, 105));
                lblIDK.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

                // Membuat Label Departemen (Slate 600)
                JLabel lblDept = new JLabel("Departemen: " + k.getDepartemen());
                lblDept.setForeground(new Color(71, 85, 105));
                lblDept.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

                // Membuat panel kontrol 1 baris 2 kolom, berisi tombol edit dan hapus
                JPanel controlPanel = new JPanel(new GridLayout(1, 2, 10, 0));
                controlPanel.setBackground(Color.WHITE);

                JButton tombolEdit = new JButton("Edit");
                tombolEdit.setBackground(new Color(0, 120, 174)); // Medical Blue
                tombolEdit.setForeground(Color.WHITE);
                tombolEdit.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
                tombolEdit.setFocusPainted(false);
                tombolEdit.setBorderPainted(false);
                tombolEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolEdit.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        tombolEdit.setBackground(new Color(0, 91, 132)); // Darker medical blue
                    }
                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        tombolEdit.setBackground(new Color(0, 120, 174));
                    }
                });
                tombolEdit.addActionListener((ActionEvent e) -> {
                    AdminPage.txtUID.setText(k.getUidRfid());
                    AdminPage.txtKRID.setText(k.getIdKaryawan());
                    AdminPage.txtKRID.setEnabled(false); 
                    AdminPage.txtKRName.setText(k.getNamaLengkap());
                    AdminPage.txtKRDept.setSelectedItem(k.getDepartemen());
                    AdminPage.btnUpdate.setEnabled(true);
                    AdminPage.btnSave.setEnabled(false); 
                });

                JButton tombolDelete = new JButton("Delete");
                tombolDelete.setBackground(new Color(239, 68, 68)); // Red 500
                tombolDelete.setForeground(Color.WHITE);
                tombolDelete.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
                tombolDelete.setFocusPainted(false);
                tombolDelete.setBorderPainted(false);
                tombolDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolDelete.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        tombolDelete.setBackground(new Color(185, 28, 28)); // Darker red
                    }
                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        tombolDelete.setBackground(new Color(239, 68, 68));
                    }
                });
                tombolDelete.addActionListener((ActionEvent e) -> {
                    Object[] options = {"Ya, Hapus", "Batal"};
                    int choice = JOptionPane.showOptionDialog(
                            null, // Parent component
                            "Apakah Anda ingin menghapus data "+k.getNamaLengkap()+"?", // Message
                            "Konfirmasi Pengelolaan", // Title
                            JOptionPane.YES_NO_OPTION, // Option type
                            JOptionPane.QUESTION_MESSAGE, // Message type
                            null, // Custom icon (null uses default)
                            options, // The array of custom button text
                            options[0] // Default button focused
                    );

                    switch (choice) {
                        case JOptionPane.YES_OPTION -> hapusKaryawan(k.getIdKaryawan());
                        case JOptionPane.NO_OPTION -> System.out.println("User memilih: Batal");
                        default -> {
                        }
                    }
                });

                controlPanel.add(tombolEdit);
                controlPanel.add(tombolDelete);

                // Memasukkan label ke dalam cardPanel (box putih)
                cardPanel.add(lblNama);
                cardPanel.add(lblIDK);
                cardPanel.add(lblDept);
                cardPanel.add(controlPanel);

                // Memasukkan cardPanel utuh ke dalam gridPanel
                gridPanel.add(cardPanel);
            }

            // Memasukkan gridPanel ke bagian ATAS (NORTH) dari panel target.
            panelTarget.add(gridPanel, BorderLayout.NORTH);

            // 4. Me-refresh panel agar perubahan muncul di GUI
            panelTarget.revalidate();
            panelTarget.repaint();
        } catch (Exception e) {
        }
    }

    /**
     * 3.READ (One): Mencari satu karyawan spesifik berdasarkan UID RFID [5],
     * [6] Sangat krusial untuk alur Tap Kartu pada Pertemuan 14 [8].
     *
     * @param key
     * @return
     */
    public List<Karyawan> cariKaryawan(String key) {
        List<Bson> filters = new ArrayList<>();
        // Get all fields from the Karyawan class
        for (Field field : Karyawan.class.getDeclaredFields()) {
            // Skip the uidRfid field and non-string fields if necessary
            if (field.getName().equals("uidRfid")) {
                continue;
            }
            filters.add(Filters.regex(field.getName(), key, "i"));
        }
        // Search and return Karyawan objects directly
        List<Karyawan> results = DAO.findMany(Filters.or(filters));
        return results;
    }

    /**
     * 4.UPDATE: Memperbarui data karyawan menggunakan filter Bson [5], [6]
     *
     * @param newK
     */
    public void updateKaryawan(Karyawan newK) {
        Bson filter = Filters.eq("idKaryawan", newK.getIdKaryawan());
        Karyawan k = DAO.findOne(filter);
        if (k != null) {
            DAO.update(filter, newK);
            AdminPage.showData("");
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
        }
    }

    /**
     * 5.DELETE: Menghapus data karyawan dari database [5], [6]
     *
     * @param idK
     */
    public void hapusKaryawan(String idK) {
        Bson filter = Filters.eq("idKaryawan", idK);
        DAO.delete(filter); // Menggunakan deleteOne [6]
        AdminPage.showData("");
        JOptionPane.showMessageDialog(null, "Data karyawan berhasil dihapus.");
    }
}
