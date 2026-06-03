/*
 * LoginPage – Halaman Login Sistem Absensi RFID Rumah Sakit MediScan
 */
package projek.GUI;

import projek.services.AuthService;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * LoginPage dengan tema rumah sakit – biru medis + putih bersih.
 */
public class LoginPage extends javax.swing.JFrame {

    // === HOSPITAL PALETTE ===
    private static final Color CLR_BG_PANEL  = new Color(245, 250, 255);  // Panel form – putih bersih
    private static final Color CLR_ACCENT    = new Color(0, 188, 212);    // Cyan aksen
    private static final Color CLR_PRIMARY   = new Color(0, 83, 130);
    private static final Color CLR_DIM       = new Color(120, 150, 180);
    private static final Color CLR_WHITE     = Color.WHITE;
    private static final Color CLR_ERROR     = new Color(220, 38, 38);
    private static final Color CLR_GRADIENT1 = new Color(0, 53, 100);
    private static final Color CLR_GRADIENT2 = new Color(0, 120, 170);

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnGo;
    private JLabel lblStatus;

    public LoginPage() {
        setTitle("MediScan RFID – Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        setResizable(true);

        // Left panel – branding
        JPanel leftPanel = buildLeftBranding();
        add(leftPanel, BorderLayout.CENTER);

        // Right panel – login form
        JPanel rightPanel = buildLoginForm();
        rightPanel.setPreferredSize(new Dimension(420, 0));
        add(rightPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        if (txtUsername != null) txtUsername.requestFocus();
    }

    // ─────────────────────────────────────────────────────────
    // LEFT BRANDING PANEL
    // ─────────────────────────────────────────────────────────
    private JPanel buildLeftBranding() {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Gradient background
                GradientPaint gp = new GradientPaint(0, 0, CLR_GRADIENT1, getWidth(), getHeight(), CLR_GRADIENT2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Decorative circles
                g2.setColor(new Color(255, 255, 255, 18));
                g2.fillOval(-80, -80, 300, 300);
                g2.fillOval(getWidth() - 200, getHeight() - 200, 350, 350);
                g2.setColor(new Color(0, 188, 212, 25));
                g2.fillOval(getWidth() / 2 - 150, getHeight() / 2 - 150, 300, 300);
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8, 0, 8, 0);

        // Cross icon
        JLabel cross = new JLabel("✚");
        cross.setFont(new Font("Segoe UI Emoji", Font.BOLD, 72));
        cross.setForeground(CLR_ACCENT);
        cross.setHorizontalAlignment(SwingConstants.CENTER);

        // App name
        JLabel appName = new JLabel("MediScan RFID");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 36));
        appName.setForeground(CLR_WHITE);

        // Subtitle
        JLabel subtitle = new JLabel("Sistem Absensi RFID Rumah Sakit");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(180, 220, 255));

        // Divider
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setForeground(CLR_ACCENT);
        sep.setPreferredSize(new Dimension(220, 2));

        // Features
        String[] features = {
            "✓  Absensi RFID Real-time",
            "✓  Data Staf Terintegrasi",
            "✓  Laporan Kehadiran Otomatis",
            "✓  Keamanan Data SHA-256"
        };

        panel.add(cross, gbc);
        panel.add(appName, gbc);
        panel.add(subtitle, gbc);

        JPanel sepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 4));
        sepWrapper.setOpaque(false);
        sepWrapper.add(sep);
        panel.add(sepWrapper, gbc);

        for (String f : features) {
            JLabel feat = new JLabel(f);
            feat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            feat.setForeground(new Color(200, 235, 255));
            panel.add(feat, gbc);
        }

        // Footer
        gbc.insets = new Insets(30, 0, 4, 0);
        JLabel footer = new JLabel("© 2025 MediScan RS – All rights reserved");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footer.setForeground(new Color(140, 180, 210));
        panel.add(footer, gbc);

        return panel;
    }

    // ─────────────────────────────────────────────────────────
    // RIGHT LOGIN FORM PANEL
    // ─────────────────────────────────────────────────────────
    private JPanel buildLoginForm() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(CLR_BG_PANEL);
        outer.setBorder(new MatteBorder(0, 1, 0, 0, new Color(200, 220, 240)));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(320, 420));

        // Header
        JLabel icon = new JLabel("🏥");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        icon.setAlignmentX(CENTER_ALIGNMENT);

        JLabel title = new JLabel("Selamat Datang");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(CLR_PRIMARY);
        title.setAlignmentX(CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Masuk ke Sistem MediScan");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(CLR_DIM);
        sub.setAlignmentX(CENTER_ALIGNMENT);

        card.add(icon);
        card.add(Box.createVerticalStrut(8));
        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(28));

        // Separator
        JSeparator sep = new JSeparator();
        sep.setForeground(CLR_ACCENT);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        card.add(sep);
        card.add(Box.createVerticalStrut(20));

        // Username field
        card.add(buildInputLabel("👤  Username"));
        card.add(Box.createVerticalStrut(6));
        txtUsername = new JTextField();
        styleInput(txtUsername, "Masukkan username...");
        card.add(txtUsername);
        card.add(Box.createVerticalStrut(14));

        // Password field
        card.add(buildInputLabel("🔒  Password"));
        card.add(Box.createVerticalStrut(6));
        txtPassword = new JPasswordField();
        styleInput(txtPassword, "Masukkan password...");
        card.add(txtPassword);
        card.add(Box.createVerticalStrut(6));

        // Status label
        lblStatus = new JLabel(" ");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblStatus.setForeground(CLR_ERROR);
        lblStatus.setAlignmentX(LEFT_ALIGNMENT);
        card.add(lblStatus);
        card.add(Box.createVerticalStrut(16));

        // Login button
        btnGo = buildLoginButton();
        card.add(btnGo);

        outer.add(card);
        return outer;
    }

    private JLabel buildInputLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(CLR_DIM);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }

    private void styleInput(JComponent field, String placeholder) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 220, 240), 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setAlignmentX(LEFT_ALIGNMENT);
        if (field instanceof JTextField) {
            ((JTextField) field).putClientProperty("JTextField.placeholderText", placeholder);
        }
        // Focus styling
        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(CLR_ACCENT, 2, true), new EmptyBorder(7, 11, 7, 11)));
            }
            @Override public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(200, 220, 240), 1, true), new EmptyBorder(8, 12, 8, 12)));
            }
        });
    }

    private JButton buildLoginButton() {
        JButton btn = new JButton("Masuk  →") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, CLR_PRIMARY, getWidth(), getHeight(), CLR_GRADIENT2);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(CLR_WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(CLR_GRADIENT2); }
            @Override public void mouseExited(MouseEvent e)  { btn.setBackground(CLR_PRIMARY); }
        });
        btn.addActionListener(e -> doLogin());

        // Enter key on password field
        txtPassword.addActionListener(e -> doLogin());
        txtUsername.addActionListener(e -> txtPassword.requestFocus());

        return btn;
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        lblStatus.setText(" ");

        if (username.isEmpty()) {
            lblStatus.setText("⚠ Username tidak boleh kosong.");
            txtUsername.requestFocus(); return;
        }
        if (password.isEmpty()) {
            lblStatus.setText("⚠ Password tidak boleh kosong.");
            txtPassword.requestFocus(); return;
        }

        btnGo.setEnabled(false);
        btnGo.setText("Memverifikasi...");
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override protected Void doInBackground() {
                AuthService svc = new AuthService();
                svc.login(username, password, LoginPage.this);
                return null;
            }
            @Override protected void done() {
                btnGo.setEnabled(true);
                btnGo.setText("Masuk  →");
            }
        };
        worker.execute();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) { /* ignore */ }
        java.awt.EventQueue.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
