package projek.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardPage extends javax.swing.JFrame {

    public DashboardPage() {
        // Jangan panggil initComponents() agar NetBeans tidak menimpa desain
        initCustomComponents(); 
        setLocationRelativeTo(null);
    }

    private void initCustomComponents() {
        // Main Container
        JPanel mainContainer = new JPanel(new BorderLayout());
        
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(68, 114, 196));
        header.setPreferredSize(new Dimension(0, 100));
        header.setBorder(new EmptyBorder(0, 30, 0, 30));
        
        JLabel title = new JLabel("MediScan RFID Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);

        // Content (Tombol Utama)
        JPanel content = new JPanel(new GridLayout(1, 2, 40, 40));
        content.setBorder(new EmptyBorder(60, 60, 60, 60));
        content.setBackground(Color.WHITE);

        // Tombol Admin
        JButton btnAdmin = new JButton("<html><center><font size='6'><b>KELOLA KARYAWAN</b></font><br>Manajemen Data & RFID</center></html>");
        btnAdmin.setBackground(new Color(237, 125, 49));
        btnAdmin.setForeground(Color.WHITE);
        btnAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdmin.setFocusPainted(false);
        btnAdmin.addActionListener(e -> {
            System.out.println("Klik Admin terdeteksi!");
            new AdminPage().setVisible(true);
        });

        // Tombol Absensi
        JButton btnAbsen = new JButton("<html><center><font size='6'><b>MODE ABSENSI</b></font><br>Mulai Scan RFID</center></html>");
        btnAbsen.setBackground(new Color(68, 114, 196));
        btnAbsen.setForeground(Color.WHITE);
        btnAbsen.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAbsen.setFocusPainted(false);
        btnAbsen.addActionListener(e -> {
            System.out.println("Klik Absen terdeteksi!");
            new AttendancePage().setVisible(true);
        });

        content.add(btnAdmin);
        content.add(btnAbsen);

        mainContainer.add(header, BorderLayout.NORTH);
        mainContainer.add(content, BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(mainContainer);
        setSize(1000, 650);
        setTitle("MediScan Dashboard");
    }


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new DashboardPage().setVisible(true);
        });
    }
}