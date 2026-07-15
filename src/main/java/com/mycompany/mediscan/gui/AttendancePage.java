package com.mycompany.mediscan.gui;

import com.mycompany.mediscan.gui.panel.Settings;
import com.mycompany.mediscan.objects.Karyawan;
import com.mycompany.mediscan.services.DigitalClockService;
import com.mycompany.mediscan.services.I18nService;
import com.mycompany.mediscan.services.KaryawanService;
import com.mycompany.mediscan.services.LogAbsensiService;
import com.mycompany.mediscan.services.SerialService;
import com.mycompany.mediscan.util.EncryptionUtils;
import com.mycompany.mediscan.util.SecurityUtils;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class AttendancePage extends javax.swing.JFrame {

    private Thread clockThread;
    Thread delayThread;

    // Listener untuk mendeteksi pergantian bahasa secara live
    private final I18nService.I18nChangeListener languageListener = this::applyLanguage;

    public AttendancePage() {
        initComponents();

        initClock(jLabel1);

        // Set status awal dari preferences ke toggle
        String lastStatus = Settings.prefs.get("LAST_STATUS", "IN");
        jLabel7.setStatusByString("OUT".equalsIgnoreCase(lastStatus) ? "Pulang" : "Masuk");
        syncSubmitButton();

        // Sync preference + update button teks saat toggle diklik
        jLabel7.addActionListener(e -> {
            String toggleVal = jLabel7.getStatusString();
            String status = "Pulang".equalsIgnoreCase(toggleVal) ? "OUT" : "IN";
            Settings.prefs.put("LAST_STATUS", status);
            syncSubmitButton();
        });

        // Daftarkan listener i18n agar UI ikut berubah saat bahasa diganti
        I18nService.registerListener(languageListener);
        applyLanguage();

        setupAttendanceWorkflow();
    }

    /**
     * Update warna & teks tombol btnSubmit sesuai status toggle saat ini.
     */
    private void syncSubmitButton() {
        boolean isMasuk = "Masuk".equalsIgnoreCase(jLabel7.getStatusString());
        String labelIn  = I18nService.get("ui.status.in");
        String labelOut = I18nService.get("ui.status.out");
        if (isMasuk) {
            btnSubmit.setText("✓   " + labelIn);
            btnSubmit.setBackground(new Color(25, 135, 84));   // Bootstrap Success
        } else {
            btnSubmit.setText("↩   " + labelOut);
            btnSubmit.setBackground(new Color(220, 53, 69));   // Bootstrap Danger
        }
        btnSubmit.repaint();
    }

    /**
     * Terapkan semua teks dari i18n ke komponen UI.
     */
    private void applyLanguage() {
        jLabel2.setText(I18nService.get("attendance.tapPrompt"));

        jLabel3.setText(I18nService.get("attendance.fullName"));
        jLabel4.setText(I18nService.get("attendance.employeeId"));
        jLabel5.setText(I18nService.get("attendance.department"));
        jLabel6.setText(I18nService.get("attendance.avatar"));

        btnLogout.setText(I18nService.get("ui.sidebar.logout"));
        this.setTitle(I18nService.get("ui.sidebar.kiosk") + " - MediScan");

        // Update label toggle sesuai bahasa aktif
        jLabel7.setLabels(
            I18nService.get("ui.status.in"),
            I18nService.get("ui.status.out")
        );

        // Sinkronkan teks tombol submit ke bahasa aktif
        syncSubmitButton();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        autoScaledLabel1 = new com.mycompany.mediscan.palette.AutoScaledLabel();
        btnLogout = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        rFIDIconPanel1 = new com.mycompany.mediscan.palette.RFIDIconPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new com.mycompany.mediscan.palette.SlidingStatusToggle();
        btnSubmit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // ===== TOP BAR =====
        jPanel1.setBackground(new java.awt.Color(0, 59, 111));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calendar-clock-icon.png")));
        jLabel1.setText("...");

        autoScaledLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logoMediscan.png")));
        autoScaledLabel1.setText(".");

        // Tombol Logout
        btnLogout.setText("Keluar");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> doLogout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(autoScaledLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 300, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(autoScaledLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        // ===== BOTTOM BAR =====
        jPanel2.setBackground(new java.awt.Color(0, 59, 111));
        jPanel2.setPreferredSize(new java.awt.Dimension(1117, 40));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1117, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        // ===== RFID ICON =====
        javax.swing.GroupLayout rFIDIconPanel1Layout = new javax.swing.GroupLayout(rFIDIconPanel1);
        rFIDIconPanel1.setLayout(rFIDIconPanel1Layout);
        rFIDIconPanel1Layout.setHorizontalGroup(
            rFIDIconPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        rFIDIconPanel1Layout.setVerticalGroup(
            rFIDIconPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        // ===== PROMPT LABEL =====
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("...");

        // ===== EMPLOYEE INFO PANEL =====
        jPanel5.setBackground(new java.awt.Color(37, 58, 90));
        jPanel5.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 80, 150), 1, true),
            javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13));
        jLabel3.setForeground(new java.awt.Color(220, 235, 255));
        jLabel3.setText("...");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13));
        jLabel4.setForeground(new java.awt.Color(220, 235, 255));
        jLabel4.setText("...");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 13));
        jLabel5.setForeground(new java.awt.Color(220, 235, 255));
        jLabel5.setText("...");

        jLabel6.setBackground(new java.awt.Color(0, 80, 150));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", Font.PLAIN, 36));
        jLabel6.setText("👤");
        jLabel6.setOpaque(true);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5))
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        // ===== TOGGLE =====
        // Atribut visual lama — painting custom, tidak perlu setBackground/setOpaque

        // ===== SUBMIT BUTTON =====
        btnSubmit.setText("✓   MASUK");
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSubmit.setBackground(new Color(25, 135, 84));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setBorderPainted(false);
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                boolean isMasuk = "Masuk".equalsIgnoreCase(jLabel7.getStatusString());
                btnSubmit.setBackground(isMasuk ? new Color(20, 108, 67) : new Color(176, 42, 55));
            }
            @Override public void mouseExited(MouseEvent e) { syncSubmitButton(); }
        });

        // ===== MAIN CENTER PANEL (jPanel4 inside jPanel3) =====
        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(33, 33, 33))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rFIDIconPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rFIDIconPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(318, 318, 318)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(1131, 625));
        setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new AttendancePage().setVisible(true);
        });
    }

    private com.mycompany.mediscan.palette.AutoScaledLabel autoScaledLabel1;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private com.mycompany.mediscan.palette.SlidingStatusToggle jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTextField1;
    private com.mycompany.mediscan.palette.RFIDIconPanel rFIDIconPanel1;

    private void initClock(JLabel lblJam) {
        DigitalClockService service = new DigitalClockService(lblJam, "EEEE, d MMMM yyyy, HH:mm:ss");
        clockThread = service.getThread();
        clockThread.setName("Thread-Jam-Kiosk");
        clockThread.setDaemon(true);
        clockThread.start();
        System.out.println("Memulai: " + clockThread.getName() + " (Daemon: " + clockThread.isDaemon() + ")");
    }

    private void setupAttendanceWorkflow() {
        KaryawanService krService = new KaryawanService();
        LogAbsensiService logService = new LogAbsensiService();

        // 1. INPUT MANUAL (Ketik UID + tekan ENTER)
        jTextField1.addActionListener(e -> {
            String inputRfid = jTextField1.getText().trim();
            if (!inputRfid.isEmpty()) {
                prosesAbsensi(inputRfid, krService, logService);
                jTextField1.setText("");
            }
        });

        // 2. TOMBOL SUBMIT
        btnSubmit.addActionListener(e -> {
            String inputRfid = jTextField1.getText().trim();
            if (!inputRfid.isEmpty()) {
                prosesAbsensi(inputRfid, krService, logService);
                jTextField1.setText("");
            } else {
                updateLabelWithDelay(jLabel2,
                    "Masukkan UID atau Tap kartu RFID terlebih dahulu!",
                    new java.awt.Color(234, 179, 8));
            }
        });

        // 3. INPUT HARDWARE (RFID Card Tap)
        SerialService.getInstance().addHandler(dataRfid -> {
            prosesAbsensi(dataRfid, krService, logService);
        });
    }

    private void prosesAbsensi(String dataRfid, KaryawanService krService, LogAbsensiService logService) {
        // 1. MATCH: Cari karyawan di database (mendukung plain text dan hashed UID)
        Karyawan k = krService.findByUid(dataRfid);
        boolean isSuccess = (k != null);

        // 2. SAVE: Catat log absensi sesuai status aktif di Kiosk toggle
        String currentStatus = "Pulang".equalsIgnoreCase(jLabel7.getStatusString()) ? "OUT" : "IN";
        String logUid = isSuccess ? k.getUidRfid() : SecurityUtils.getHash(dataRfid, SecurityUtils.SHA_256);
        logService.simpanLog(logUid, currentStatus);

        // 3. NOTIFY: Update GUI secara aman
        SwingUtilities.invokeLater(() -> {
            if (isSuccess) {
                String decryptedId = EncryptionUtils.decrypt(k.getIdKaryawan());
                String displayId = (decryptedId != null) ? decryptedId : k.getIdKaryawan();

                jLabel3.setText(I18nService.get("attendance.fullName") + " " + k.getNamaLengkap());
                jLabel4.setText(I18nService.get("attendance.employeeId") + " " + displayId);
                jLabel5.setText(I18nService.get("attendance.department") + " " + k.getDepartemen());

                updateLabelWithDelay(jLabel2,
                    "✓  " + I18nService.get("attendance.accepted"),
                    new java.awt.Color(40, 167, 69));
            } else {
                updateLabelWithDelay(jLabel2,
                    "✗  " + I18nService.get("attendance.notRegistered"),
                    new java.awt.Color(220, 53, 69));
            }
        });
    }

    private java.awt.Color originalPromptColor = null;

    private void updateLabelWithDelay(JLabel comp, String info, java.awt.Color color) {
        if (originalPromptColor == null) {
            originalPromptColor = comp.getForeground();
        }
        if (delayThread != null && delayThread.isAlive()) {
            delayThread.interrupt();
        }

        delayThread = new Thread(() -> {
            SwingUtilities.invokeLater(() -> {
                comp.setForeground(color);
                comp.setText(info);
            });
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // Dibatalkan karena ada tap baru
            }
            SwingUtilities.invokeLater(() -> {
                comp.setForeground(originalPromptColor);
                comp.setText(I18nService.get("attendance.tapPrompt"));
            });
        });

        delayThread.setName("delayThread");
        delayThread.setDaemon(true);
        delayThread.start();
    }

    private void doLogout() {
        int opsi = JOptionPane.showConfirmDialog(
            this,
            I18nService.get("ui.logout.message"),
            I18nService.get("ui.logout.title"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        if (opsi == JOptionPane.YES_OPTION) {
            dispose();
            new LoginPage().setVisible(true);
        }
    }

    @Override
    public void dispose() {
        I18nService.unregisterListener(languageListener);
        super.dispose();
    }
}
