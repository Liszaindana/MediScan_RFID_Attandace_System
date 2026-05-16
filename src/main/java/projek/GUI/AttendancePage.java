package projek.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AttendancePage extends javax.swing.JFrame {

    public AttendancePage() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Main Dark Background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(33, 37, 41)); // Dark Theme ala aplikasi scanning

        // Header Status
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(68, 114, 196));
        header.setPreferredSize(new Dimension(0, 80));
        
        JLabel lblStatus = new JLabel("SILAKAN TAP KARTU RFID ANDA", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblStatus.setForeground(Color.WHITE);
        header.add(lblStatus, BorderLayout.CENTER);

        // Content Area (Employee Info)
        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Info Card
        JPanel infoCard = new JPanel(new BorderLayout(20, 20));
        infoCard.setBackground(new Color(45, 50, 55));
        infoCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 75, 80), 2),
            new EmptyBorder(30, 30, 30, 30)
        ));
        infoCard.setPreferredSize(new Dimension(800, 400));

        // Placeholder Foto
        JPanel photoPanel = new JPanel(new BorderLayout());
        photoPanel.setPreferredSize(new Dimension(250, 300));
        photoPanel.setBackground(new Color(60, 65, 70));
        photoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        JLabel lblPhoto = new JLabel("FOTO KARYAWAN", SwingConstants.CENTER);
        lblPhoto.setForeground(Color.LIGHT_GRAY);
        photoPanel.add(lblPhoto, BorderLayout.CENTER);

        // Data Labels
        JPanel dataPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        dataPanel.setOpaque(false);

        JLabel lblName = createDataLabel("Nama : -", 24, Color.ORANGE);
        JLabel lblID = createDataLabel("ID : -", 18, Color.WHITE);
        JLabel lblDept = createDataLabel("Dept : -", 18, Color.WHITE);
        JLabel lblTime = createDataLabel("Waktu : --:--:--", 16, Color.LIGHT_GRAY);

        dataPanel.add(lblName);
        dataPanel.add(lblID);
        dataPanel.add(lblDept);
        dataPanel.add(lblTime);

        infoCard.add(photoPanel, BorderLayout.WEST);
        infoCard.add(dataPanel, BorderLayout.CENTER);

        // Action Buttons (Bottom)
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setOpaque(false);
        
        JButton btnBack = new JButton("KEMBALI KE DASHBOARD");
        btnBack.setBackground(new Color(237, 125, 49));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> {
            dispose(); // Kembali ke Dashboard (yang tetap terbuka)
        });
        footer.add(btnBack);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(content, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        content.add(infoCard);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MediScan Attendance Mode");
        add(mainPanel);
        setSize(1100, 750);
    }

    private JLabel createDataLabel(String text, int size, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, size));
        lbl.setForeground(color);
        return lbl;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new AttendancePage().setVisible(true);
        });
    }
}
