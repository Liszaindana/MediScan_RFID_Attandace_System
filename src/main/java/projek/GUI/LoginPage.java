package projek.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginPage extends javax.swing.JFrame {

    public LoginPage() {
        initComponents();
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null); // Menempatkan jendela di tengah layar
    }

    private void initComponents() {
        // Main Background Panel dengan warna Biru Institusi
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(68, 114, 196)); 
        
        // Card Panel (Kotak Putih di tengah)
        JPanel loginCard = new JPanel();
        loginCard.setLayout(new BoxLayout(loginCard, BoxLayout.Y_AXIS));
        loginCard.setBackground(Color.WHITE);
        loginCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(40, 50, 40, 50)
        ));

        // Logo / Title
        JLabel title = new JLabel("MediScan Login");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(68, 114, 196));

        JLabel subtitle = new JLabel("RFID Attendance System");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setForeground(Color.GRAY);

        // Input Fields
        JTextField txtUser = new JTextField();
        txtUser.setPreferredSize(new Dimension(300, 60));
        txtUser.setMaximumSize(new Dimension(300, 60));
        txtUser.setBorder(BorderFactory.createTitledBorder("Username"));

        JPasswordField txtPass = new JPasswordField();
        txtPass.setPreferredSize(new Dimension(300, 60));
        txtPass.setMaximumSize(new Dimension(300, 60));
        txtPass.setBorder(BorderFactory.createTitledBorder("Password"));

        // Login Button
        JButton btnLogin = new JButton("MASUK KE SISTEM");
        btnLogin.setPreferredSize(new Dimension(300, 50));
        btnLogin.setMaximumSize(new Dimension(300, 50));
        btnLogin.setBackground(new Color(237, 125, 49)); // Oranye
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder());
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Action Listener
        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());
            
            // Hardcoded admin login untuk keperluan demo
            if (user.equals("admin") && pass.equals("admin")) {
                new DashboardPage().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Username atau Password salah!", 
                    "Login Gagal", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // Menata komponen ke dalam Card
        loginCard.add(title);
        loginCard.add(subtitle);
        loginCard.add(Box.createRigidArea(new Dimension(0, 30)));
        loginCard.add(txtUser);
        loginCard.add(Box.createRigidArea(new Dimension(0, 15)));
        loginCard.add(txtPass);
        loginCard.add(Box.createRigidArea(new Dimension(0, 30)));
        loginCard.add(btnLogin);

        // Frame Settings
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MediScan - Login");
        add(mainPanel);
        mainPanel.add(loginCard);
        
        setSize(900, 600);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new LoginPage().setVisible(true);
        });
    }
}
