package projek.GUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AttendancePage extends javax.swing.JFrame {

    // ─── Hospital Color Palette ───────────────────────────────────────────────
    private static final Color CLR_PRIMARY    = new Color(0, 120, 174);   // Medical Blue
    private static final Color CLR_SUCCESS    = new Color(0, 168, 143);   // Teal-Green
    private static final Color CLR_BG         = new Color(245, 248, 252);
    private static final Color CLR_CARD       = new Color(255, 255, 255);
    private static final Color CLR_BORDER     = new Color(219, 229, 239);
    private static final Color CLR_TEXT_H     = new Color(15, 40, 70);
    private static final Color CLR_TEXT_M     = new Color(71, 100, 130);
    private static final Color CLR_WAITING    = new Color(251, 191, 36);  // Amber — waiting state
    private static final Color CLR_WHITE      = Color.WHITE;

    // ─── Live Data Labels (public so SerialService can update them) ──────────
    public static JLabel lblStatusTop;
    public static JLabel lblName;
    public static JLabel lblID;
    public static JLabel lblDept;
    public static JLabel lblTime;
    public static JLabel lblStatusBadge;
    public static JPanel infoCard;

    public AttendancePage() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CLR_BG);

        // ── HEADER ────────────────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CLR_PRIMARY);
        header.setPreferredSize(new Dimension(0, 80));
        header.setBorder(new EmptyBorder(0, 32, 0, 32));

        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 18));
        headerLeft.setOpaque(false);

        JLabel lblIcon = new JLabel("✚");
        lblIcon.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblIcon.setForeground(new Color(255, 255, 255, 200));

        JPanel headerTitles = new JPanel(new GridLayout(2, 1, 0, 0));
        headerTitles.setOpaque(false);

        JLabel lblHeaderTitle = new JLabel("Mode Absensi RFID");
        lblHeaderTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHeaderTitle.setForeground(CLR_WHITE);

        JLabel lblHeaderSub = new JLabel("Sistem Presensi Karyawan Rumah Sakit");
        lblHeaderSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHeaderSub.setForeground(new Color(180, 215, 240));

        headerTitles.add(lblHeaderTitle);
        headerTitles.add(lblHeaderSub);
        headerLeft.add(lblIcon);
        headerLeft.add(headerTitles);
        header.add(headerLeft, BorderLayout.WEST);

        // ── STATUS BANNER ─────────────────────────────────────────────────────
        JPanel statusBanner = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        statusBanner.setBackground(new Color(255, 251, 235)); // amber-50
        statusBanner.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(253, 230, 138)));

        lblStatusTop = new JLabel("⏳  Menunggu kartu RFID ditempelkan...");
        lblStatusTop.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblStatusTop.setForeground(new Color(146, 64, 14)); // amber-800
        statusBanner.add(lblStatusTop);

        // ── MAIN CONTENT ──────────────────────────────────────────────────────
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(CLR_BG);
        content.setBorder(new EmptyBorder(28, 40, 20, 40));

        // ── INFO CARD ──────────────────────────────────────────────────────────
        infoCard = new JPanel(new BorderLayout(0, 0));
        infoCard.setBackground(CLR_CARD);
        infoCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CLR_BORDER, 1, true),
            new EmptyBorder(0, 0, 0, 0)
        ));
        infoCard.setPreferredSize(new Dimension(820, 360));

        // Left accent strip
        JPanel accentStrip = new JPanel();
        accentStrip.setBackground(CLR_PRIMARY);
        accentStrip.setPreferredSize(new Dimension(8, 0));

        // Photo placeholder
        JPanel photoPanel = new JPanel(new BorderLayout());
        photoPanel.setPreferredSize(new Dimension(200, 360));
        photoPanel.setBackground(new Color(240, 245, 250));
        photoPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, CLR_BORDER));

        JLabel lblPhotoIcon = new JLabel("👤");
        lblPhotoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        lblPhotoIcon.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblPhotoHint = new JLabel("Foto Karyawan");
        lblPhotoHint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblPhotoHint.setForeground(CLR_TEXT_M);
        lblPhotoHint.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel photoCenterPanel = new JPanel(new GridLayout(2, 1, 0, 6));
        photoCenterPanel.setOpaque(false);
        photoCenterPanel.add(lblPhotoIcon);
        photoCenterPanel.add(lblPhotoHint);
        photoPanel.add(photoCenterPanel, BorderLayout.CENTER);

        // Data Panel
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setOpaque(false);
        dataPanel.setBorder(new EmptyBorder(28, 28, 28, 28));

        // Status badge row
        JPanel badgeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        badgeRow.setOpaque(false);
        lblStatusBadge = new JLabel("  MENUNGGU  ");
        lblStatusBadge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblStatusBadge.setForeground(CLR_WHITE);
        lblStatusBadge.setBackground(CLR_WAITING);
        lblStatusBadge.setOpaque(true);
        lblStatusBadge.setBorder(new EmptyBorder(3, 10, 3, 10));
        badgeRow.add(lblStatusBadge);

        // Time
        lblTime = new JLabel("--:--:--   " +
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.forLanguageTag("id-ID"))));
        lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTime.setForeground(CLR_TEXT_M);

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(CLR_BORDER);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        // Name
        lblName = new JLabel("Nama Karyawan:  —");
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblName.setForeground(CLR_TEXT_H);

        // ID
        lblID = new JLabel("ID Karyawan:  —");
        lblID.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblID.setForeground(CLR_TEXT_M);

        // Dept
        lblDept = new JLabel("Departemen:  —");
        lblDept.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDept.setForeground(CLR_TEXT_M);

        dataPanel.add(badgeRow);
        dataPanel.add(Box.createVerticalStrut(6));
        dataPanel.add(lblTime);
        dataPanel.add(Box.createVerticalStrut(14));
        dataPanel.add(sep);
        dataPanel.add(Box.createVerticalStrut(16));
        dataPanel.add(lblName);
        dataPanel.add(Box.createVerticalStrut(10));
        dataPanel.add(lblID);
        dataPanel.add(Box.createVerticalStrut(6));
        dataPanel.add(lblDept);

        infoCard.add(accentStrip, BorderLayout.WEST);
        infoCard.add(photoPanel, BorderLayout.LINE_START);
        infoCard.add(dataPanel, BorderLayout.CENTER);

        content.add(infoCard);

        // ── INSTRUCTION PANEL ─────────────────────────────────────────────────
        JPanel instructionPanel = new JPanel();
        instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
        instructionPanel.setBackground(CLR_BG);
        instructionPanel.setBorder(new EmptyBorder(16, 40, 0, 40));

        JPanel rfidIndicator = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        rfidIndicator.setBackground(new Color(237, 245, 255));
        rfidIndicator.setBorder(BorderFactory.createLineBorder(new Color(191, 219, 254), 1, true));

        JLabel rfidIcon = new JLabel("📡");
        rfidIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));

        JLabel rfidText = new JLabel("Tempelkan kartu RFID Anda ke reader — sistem akan otomatis memproses data kehadiran");
        rfidText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rfidText.setForeground(new Color(30, 64, 175));

        rfidIndicator.add(rfidIcon);
        rfidIndicator.add(rfidText);
        instructionPanel.add(rfidIndicator);

        // ── FOOTER / BACK BUTTON ──────────────────────────────────────────────
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(CLR_CARD);
        footer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, CLR_BORDER),
            new EmptyBorder(12, 32, 12, 32)
        ));

        JLabel lblFooter = new JLabel("© 2025 MediScan RFID Attendance System  •  Rumah Sakit");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(CLR_TEXT_M);

        JButton btnBack = createButton("← Kembali ke Dashboard", CLR_TEXT_M, new Color(241, 245, 249));
        btnBack.addActionListener(e -> dispose());

        footer.add(lblFooter, BorderLayout.WEST);
        footer.add(btnBack, BorderLayout.EAST);

        // ── ASSEMBLE ──────────────────────────────────────────────────────────
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(CLR_BG);
        centerWrapper.add(statusBanner, BorderLayout.NORTH);
        centerWrapper.add(content, BorderLayout.CENTER);
        centerWrapper.add(instructionPanel, BorderLayout.SOUTH);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MediScan — Mode Absensi RFID");
        add(mainPanel);
        setSize(1100, 620);
    }

    /**
     * Helper: creates a flat styled button.
     */
    private JButton createButton(String text, Color fg, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CLR_BORDER, 1, true),
            new EmptyBorder(8, 20, 8, 20)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setBackground(bg.darker());
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

    /**
     * Called by SerialService to update the UI when an RFID card is scanned.
     * Keeps all original logic intact.
     */
    public static void updateDisplay(String nama, String id, String dept, boolean berhasil) {
        String waktu = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String tanggal = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.forLanguageTag("id-ID")));

        SwingUtilities.invokeLater(() -> {
            lblName.setText("Nama Karyawan:  " + nama);
            lblID.setText("ID Karyawan:  " + id);
            lblDept.setText("Departemen:  " + dept);
            lblTime.setText(waktu + "   " + tanggal);

            if (berhasil) {
                lblStatusTop.setText("✅  Absensi berhasil dicatat untuk: " + nama);
                lblStatusTop.setForeground(new Color(6, 78, 59));
                lblStatusTop.getParent().setBackground(new Color(240, 253, 244));
                ((JPanel) lblStatusTop.getParent()).setBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(167, 243, 208)));
                lblStatusBadge.setText("  HADIR  ");
                lblStatusBadge.setBackground(CLR_SUCCESS);
                infoCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(CLR_SUCCESS, 2, true),
                    new EmptyBorder(0, 0, 0, 0)
                ));
            } else {
                lblStatusTop.setText("❌  Kartu tidak dikenal. Silakan hubungi administrator.");
                lblStatusTop.setForeground(new Color(127, 29, 29));
                lblStatusTop.getParent().setBackground(new Color(254, 242, 242));
                lblStatusBadge.setText("  TIDAK DIKENAL  ");
                lblStatusBadge.setBackground(new Color(239, 68, 68));
            }
        });
    }



    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new AttendancePage().setVisible(true));
    }
}
