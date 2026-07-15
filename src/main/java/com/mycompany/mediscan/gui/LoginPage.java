package com.mycompany.mediscan.gui;

import com.mycompany.mediscan.services.AuthService;
import com.mycompany.mediscan.services.I18nService;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginPage extends javax.swing.JFrame {

    private final I18nService.I18nChangeListener languageListener = this::applyLanguage;

    // Right panel components
    private JLabel lblWelcome;
    private JLabel lblSubtitle;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton jButton1;

    public LoginPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setTitle("Masuk - MediScan");
        setResizable(false);

        buildUI();
        txtUsername.requestFocus();

        applyLanguage();
        I18nService.registerListener(languageListener);
    }

    // =========================================================
    // BUILD UI
    // =========================================================
    private void buildUI() {
        // Root: horizontal split panel
        JPanel root = new JPanel(new GridLayout(1, 2));
        root.setPreferredSize(new Dimension(1100, 680));
        setContentPane(root);

        // ===== LEFT PANEL: Full-image =====
        JPanel leftPanel = new JPanel(new BorderLayout()) {
            private BufferedImage bgImage;
            {
                try {
                    bgImage = ImageIO.read(getClass().getResource("/images/bg.png"));
                } catch (Exception e) {
                    bgImage = null;
                }
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);

                    // Dark overlay supaya ada kontras
                    g2.setColor(new Color(0, 40, 80, 110));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        leftPanel.setOpaque(true);
        leftPanel.setBackground(new Color(0, 59, 111));

        // Branding overlay di kiri bawah
        JPanel brandOverlay = new JPanel(new BorderLayout());
        brandOverlay.setOpaque(false);
        brandOverlay.setBorder(new EmptyBorder(0, 28, 30, 28));

        JLabel lblBrandTitle = new JLabel("MediScan");
        lblBrandTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblBrandTitle.setForeground(Color.WHITE);

        JLabel lblBrandSub = new JLabel("Medical RFID Attendance System");
        lblBrandSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblBrandSub.setForeground(new Color(200, 225, 255));

        JPanel brandText = new JPanel();
        brandText.setOpaque(false);
        brandText.setLayout(new BoxLayout(brandText, BoxLayout.Y_AXIS));
        brandText.add(lblBrandTitle);
        brandText.add(Box.createVerticalStrut(4));
        brandText.add(lblBrandSub);

        brandOverlay.add(brandText, BorderLayout.SOUTH);
        leftPanel.add(brandOverlay, BorderLayout.CENTER);

        root.add(leftPanel);

        // ===== RIGHT PANEL: Login Form =====
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);

        JPanel formCard = buildFormCard();
        rightPanel.add(formCard);

        root.add(rightPanel);
    }

    private JPanel buildFormCard() {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(380, 460));

        // --- Logo ---
        JLabel logoLabel = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/logoMediscan.png"));
            Image scaled = logoIcon.getImage().getScaledInstance(180, 70, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            logoLabel.setText("MediScan");
            logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            logoLabel.setForeground(new Color(0, 59, 111));
        }
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(logoLabel);
        card.add(Box.createVerticalStrut(20));

        // --- Welcome Title ---
        lblWelcome = new JLabel("Selamat Datang");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblWelcome.setForeground(new Color(15, 23, 42));
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblWelcome);
        card.add(Box.createVerticalStrut(6));

        lblSubtitle = new JLabel("Silahkan masuk untuk melanjutkan");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(100, 116, 139));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblSubtitle);
        card.add(Box.createVerticalStrut(32));

        // --- Username Field ---
        lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUsername.setForeground(new Color(51, 65, 85));
        lblUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblUsername);
        card.add(Box.createVerticalStrut(6));

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1, true),
            new EmptyBorder(8, 14, 8, 14)
        ));
        txtUsername.setBackground(new Color(248, 250, 252));
        txtUsername.setForeground(new Color(15, 23, 42));
        addFocusBorder(txtUsername);
        txtUsername.addActionListener(e -> txtPassword.requestFocus());
        card.add(txtUsername);
        card.add(Box.createVerticalStrut(16));

        // --- Password Field ---
        lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPassword.setForeground(new Color(51, 65, 85));
        lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblPassword);
        card.add(Box.createVerticalStrut(6));

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1, true),
            new EmptyBorder(8, 14, 8, 14)
        ));
        txtPassword.setBackground(new Color(248, 250, 252));
        addFocusBorder(txtPassword);
        txtPassword.addActionListener(e -> doLogin());
        card.add(txtPassword);
        card.add(Box.createVerticalStrut(28));

        // --- Login Button ---
        jButton1 = new JButton("Masuk") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        jButton1.setFont(new Font("Segoe UI", Font.BOLD, 15));
        jButton1.setForeground(Color.WHITE);
        jButton1.setBackground(new Color(0, 59, 111));
        jButton1.setFocusPainted(false);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        jButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton1.addActionListener(e -> doLogin());
        jButton1.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                jButton1.setBackground(new Color(0, 80, 150));
            }
            @Override public void mouseExited(MouseEvent e) {
                jButton1.setBackground(new Color(0, 59, 111));
            }
        });
        card.add(jButton1);
        card.add(Box.createVerticalStrut(24));

        // --- Footer ---
        JLabel lblFooter = new JLabel("© 2026 MediScan. All rights reserved.");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(new Color(148, 163, 184));
        lblFooter.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblFooter);

        return card;
    }

    /** Highlight border biru saat field difokus */
    private void addFocusBorder(JComponent field) {
        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 59, 111), 2, true),
                    new EmptyBorder(7, 13, 7, 13)
                ));
            }
            @Override public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(203, 213, 225), 1, true),
                    new EmptyBorder(8, 14, 8, 14)
                ));
            }
        });
    }

    // =========================================================
    // APPLY LANGUAGE (i18n)
    // =========================================================
    private void applyLanguage() {
        this.setTitle(I18nService.get("ui.login.go") + " - MediScan");
        if (lblUsername != null) lblUsername.setText(I18nService.get("ui.login.username"));
        if (lblPassword != null) lblPassword.setText(I18nService.get("ui.login.password"));
        if (jButton1 != null)    jButton1.setText(I18nService.get("ui.login.go"));
    }

    // =========================================================
    // LOGIN LOGIC
    // =========================================================
    private void doLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, I18nService.get("ui.login.empty"));
        } else {
            AuthService userService = new AuthService();
            userService.login(username, password, this);
        }
    }

    @Override
    public void dispose() {
        I18nService.unregisterListener(languageListener);
        super.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
