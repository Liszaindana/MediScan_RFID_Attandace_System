package projek.GUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardPage extends javax.swing.JFrame {

    // ─── Hospital Color Palette ───────────────────────────────────────────────
    private static final Color CLR_PRIMARY    = new Color(0, 120, 174);   // Medical Blue
    private static final Color CLR_PRIMARY_DK = new Color(0, 91, 132);   // Hover blue
    private static final Color CLR_SECONDARY  = new Color(0, 168, 143);  // Teal/Green (health)
    private static final Color CLR_SEC_DK     = new Color(0, 130, 110);
    private static final Color CLR_BG         = new Color(245, 248, 252); // Light neutral bg
    private static final Color CLR_CARD       = new Color(255, 255, 255);
    private static final Color CLR_BORDER     = new Color(219, 229, 239);
    private static final Color CLR_TEXT_H     = new Color(15, 40, 70);   // dark navy
    private static final Color CLR_TEXT_M     = new Color(71, 100, 130);  // muted
    private static final Color CLR_WHITE      = Color.WHITE;

    public DashboardPage() {
        initCustomComponents();
        setLocationRelativeTo(null);
    }

    private void initCustomComponents() {
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(CLR_BG);

        // ── HEADER ────────────────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CLR_PRIMARY);
        header.setPreferredSize(new Dimension(0, 90));
        header.setBorder(new EmptyBorder(0, 32, 0, 32));

        // Left: Logo + Title
        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 22));
        headerLeft.setOpaque(false);

        // Hospital cross icon (text-based)
        JLabel lblIcon = new JLabel("✚");
        lblIcon.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblIcon.setForeground(new Color(255, 255, 255, 200));

        JPanel titleGroup = new JPanel(new GridLayout(2, 1, 0, 0));
        titleGroup.setOpaque(false);

        JLabel lblTitle = new JLabel("MediScan RFID System");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(CLR_WHITE);

        JLabel lblSub = new JLabel("Sistem Manajemen Absensi Rumah Sakit");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(180, 215, 240));

        titleGroup.add(lblTitle);
        titleGroup.add(lblSub);
        headerLeft.add(lblIcon);
        headerLeft.add(titleGroup);

        // Right: Date
        JLabel lblDate = new JLabel(LocalDate.now().format(
            DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.forLanguageTag("id-ID"))));
        lblDate.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDate.setForeground(new Color(180, 215, 240));

        header.add(headerLeft, BorderLayout.WEST);
        header.add(lblDate, BorderLayout.EAST);

        // ── CONTENT AREA ──────────────────────────────────────────────────────
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(CLR_BG);
        content.setBorder(new EmptyBorder(40, 60, 40, 60));

        // Welcome Text
        JPanel welcomePanel = new JPanel(new GridLayout(2, 1, 0, 6));
        welcomePanel.setOpaque(false);
        welcomePanel.setBorder(new EmptyBorder(0, 0, 30, 0));

        JLabel lblWelcome = new JLabel("Selamat Datang, Admin!");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblWelcome.setForeground(CLR_TEXT_H);

        JLabel lblDesc = new JLabel("Pilih menu yang ingin diakses");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDesc.setForeground(CLR_TEXT_M);

        welcomePanel.add(lblWelcome);
        welcomePanel.add(lblDesc);

        // Cards Panel
        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 24, 0));
        cardsPanel.setOpaque(false);

        // ── Card: Kelola Karyawan ──
        JPanel cardAdmin = createDashboardCard(
            "👤", "KELOLA KARYAWAN",
            "Manajemen Data & RFID",
            "Tambah, edit, dan hapus data karyawan rumah sakit serta konfigurasi RFID mereka.",
            CLR_PRIMARY, CLR_PRIMARY_DK
        );
        cardAdmin.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                new AdminPage().setVisible(true);
            }
        });

        // ── Card: Mode Absensi ──
        JPanel cardAbsen = createDashboardCard(
            "🏥", "MODE ABSENSI",
            "Mulai Scan RFID Sekarang",
            "Aktifkan mode absensi untuk memulai pencatatan kehadiran staf menggunakan kartu RFID.",
            CLR_SECONDARY, CLR_SEC_DK
        );
        cardAbsen.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                new AttendancePage().setVisible(true);
            }
        });

        cardsPanel.add(cardAdmin);
        cardsPanel.add(cardAbsen);

        content.add(welcomePanel, BorderLayout.NORTH);
        content.add(cardsPanel, BorderLayout.CENTER);

        // ── FOOTER ────────────────────────────────────────────────────────────
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(CLR_CARD);
        footer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, CLR_BORDER),
            new EmptyBorder(10, 32, 10, 32)
        ));

        JLabel lblFooter = new JLabel("© 2025 MediScan RFID Attendance System  •  Rumah Sakit");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(CLR_TEXT_M);
        footer.add(lblFooter, BorderLayout.WEST);

        mainContainer.add(header, BorderLayout.NORTH);
        mainContainer.add(content, BorderLayout.CENTER);
        mainContainer.add(footer, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MediScan — Dashboard Rumah Sakit");
        add(mainContainer);
        setSize(1000, 620);
    }

    /**
     * Creates a modern clickable card for the dashboard.
     */
    private JPanel createDashboardCard(String icon, String title, String subtitle,
                                        String desc, Color colorNormal, Color colorHover) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(CLR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CLR_BORDER, 1, true),
            new EmptyBorder(28, 28, 28, 28)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Top colored accent stripe
        JPanel stripe = new JPanel();
        stripe.setBackground(colorNormal);
        stripe.setPreferredSize(new Dimension(0, 6));
        stripe.setBorder(null);

        // Icon + Title row
        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        topRow.setOpaque(false);
        topRow.setBorder(new EmptyBorder(10, 0, 4, 0));

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));

        JPanel textGroup = new JPanel(new GridLayout(2, 1, 0, 2));
        textGroup.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitle.setForeground(CLR_TEXT_H);

        JLabel lblSub = new JLabel(subtitle);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(colorNormal);

        textGroup.add(lblTitle);
        textGroup.add(lblSub);
        topRow.add(lblIcon);
        topRow.add(textGroup);

        // Description
        JLabel lblDesc = new JLabel("<html><p style='width:280px;'>" + desc + "</p></html>");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDesc.setForeground(CLR_TEXT_M);

        // Arrow button
        JPanel arrowRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        arrowRow.setOpaque(false);
        JLabel lblArrow = new JLabel("Buka →");
        lblArrow.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblArrow.setForeground(colorNormal);
        arrowRow.add(lblArrow);

        card.add(stripe, BorderLayout.NORTH);
        card.add(topRow, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 8));
        bottomPanel.setOpaque(false);
        bottomPanel.add(lblDesc, BorderLayout.CENTER);
        bottomPanel.add(arrowRow, BorderLayout.SOUTH);
        card.add(bottomPanel, BorderLayout.SOUTH);

        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(240, 248, 255));
                stripe.setBackground(colorHover);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(colorNormal, 2, true),
                    new EmptyBorder(28, 28, 28, 28)
                ));
            }
            @Override public void mouseExited(MouseEvent e) {
                card.setBackground(CLR_CARD);
                stripe.setBackground(colorNormal);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(CLR_BORDER, 1, true),
                    new EmptyBorder(28, 28, 28, 28)
                ));
            }
        });

        return card;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new DashboardPage().setVisible(true));
    }
}