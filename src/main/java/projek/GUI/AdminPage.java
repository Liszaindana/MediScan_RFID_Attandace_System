package projek.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

/**
 * AdminPage – Halaman utama sistem absensi RFID rumah sakit MediScan.
 */
public class AdminPage extends javax.swing.JFrame {

    // === HOSPITAL COLOR PALETTE ===
    private static final Color CLR_HEADER_BG  = new Color(0, 53, 100);   // Header gelap
    private static final Color CLR_ACCENT     = new Color(0, 188, 212);  // Cyan aksen
    private static final Color CLR_BG_LIGHT   = new Color(236, 245, 255); // Background konten
    private static final Color CLR_FOOTER_BG  = new Color(0, 53, 100);
    private static final Color CLR_WHITE      = Color.WHITE;
    private static final Color CLR_TEXT_DIM   = new Color(180, 220, 255);

    public static JPanel appContentPane;

    public AdminPage() {
        setTitle("MediScan RFID – Sistem Absensi Rumah Sakit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // ---------- HEADER ----------
        JPanel header = buildHeader();
        add(header, BorderLayout.NORTH);

        // ---------- SIDEBAR ----------
        JPanel sidebar = new projek.GUI.panel.SidebarMainMenu();
        add(sidebar, BorderLayout.WEST);

        // ---------- CENTER CONTENT ----------
        appContentPane = new JPanel(new BorderLayout());
        appContentPane.setBackground(CLR_BG_LIGHT);
        add(appContentPane, BorderLayout.CENTER);

        // ---------- FOOTER ----------
        JPanel footer = buildFooter();
        add(footer, BorderLayout.SOUTH);

        // Default page
        addContent(new projek.GUI.panel.KaryawanPanel());

        pack();
        setLocationRelativeTo(null);
    }

    // -------------------------------------------------------
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CLR_HEADER_BG);
        header.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 3, 0, CLR_ACCENT),
            new EmptyBorder(10, 16, 10, 16)
        ));
        header.setPreferredSize(new Dimension(0, 75));

        // LEFT — Logo + nama aplikasi
        JPanel leftPanel = new JPanel(new BorderLayout(14, 0));
        leftPanel.setOpaque(false);

        JLabel crossIcon = new JLabel("<html><font color='#00BCD4' size='6'><b>+</b></font></html>");
        crossIcon.setForeground(CLR_ACCENT);

        JPanel titleBlock = new JPanel(new GridLayout(2, 1, 0, 2));
        titleBlock.setOpaque(false);

        JLabel lblTitle = new JLabel("MediScan RFID");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(CLR_WHITE);

        JLabel lblSub = new JLabel("Sistem Absensi Rumah Sakit");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setForeground(CLR_TEXT_DIM);

        titleBlock.add(lblTitle);
        titleBlock.add(lblSub);
        leftPanel.add(crossIcon, BorderLayout.WEST);
        leftPanel.add(titleBlock, BorderLayout.CENTER);

        // RIGHT — Clock + user badge
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 8));
        rightPanel.setOpaque(false);

        JLabel clockLabel = new JLabel();
        clockLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        clockLabel.setForeground(CLR_WHITE);
        startClock(clockLabel);

        JLabel userBadge = new JLabel(" Administrator ");
        userBadge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        userBadge.setForeground(CLR_HEADER_BG);
        userBadge.setBackground(CLR_ACCENT);
        userBadge.setOpaque(true);
        userBadge.setBorder(new EmptyBorder(4, 10, 4, 10));

        rightPanel.add(clockLabel);
        rightPanel.add(userBadge);

        header.add(leftPanel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(CLR_FOOTER_BG);
        footer.setPreferredSize(new Dimension(0, 28));
        footer.setBorder(new MatteBorder(1, 0, 0, 0, CLR_ACCENT));

        JLabel left = new JLabel("  MediScan RFID v1.0  |  Sistem Absensi RFID Rumah Sakit");
        left.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        left.setForeground(CLR_TEXT_DIM);

        JLabel right = new JLabel("Terhubung ke MongoDB  \uD83D\uDFE2  ");
        right.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        right.setForeground(new Color(0, 230, 130));

        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);
        return footer;
    }

    private void startClock(JLabel clockLabel) {
        Timer timer = new Timer(1000, (ActionEvent e) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMM yyyy  |  HH:mm:ss");
            clockLabel.setText(sdf.format(new Date()));
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    public void addContent(JPanel panel) {
        if (appContentPane.getComponentCount() > 0) {
            appContentPane.removeAll();
        }
        appContentPane.add(panel, BorderLayout.CENTER);
        appContentPane.revalidate();
        appContentPane.repaint();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new AdminPage().setVisible(true));
    }
}

