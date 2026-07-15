package com.mycompany.mediscan.gui.panel;

import com.mycompany.mediscan.services.DashboardService;
import com.mycompany.mediscan.services.I18nService;
import com.mycompany.mediscan.services.LogAbsensiService;
import com.mycompany.mediscan.services.KaryawanService;
import com.mycompany.mediscan.objects.Karyawan;
import com.mycompany.mediscan.objects.LogAbsensi;
import com.mycompany.mediscan.util.EncryptionUtils;
import com.mycompany.mediscan.palette.GradientRoundedPanel;
import com.mycompany.mediscan.palette.RoundedPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class DashboardPanel extends JPanel {

    private final DashboardService service = new DashboardService();
    private final I18nService.I18nChangeListener languageListener = this::applyLanguage;

    private JLabel lblTitleTotal;
    private JLabel lblTitleHadir;
    private JLabel lblTitleTerlambat;
    private JLabel lblTitleBelum;

    private JLabel lblValTotal;
    private JLabel lblValHadir;
    private JLabel lblValTerlambat;
    private JLabel lblValBelum;

    private JLabel lblTitleHeader;
    private JLabel lblSubtitle;
    private JLabel lblTableTitle;
    private JTable tableRecent;
    private JButton btnRefresh;

    public DashboardPanel() {
        initComponents();
        applyLanguage();
        I18nService.registerListener(languageListener);
        refreshData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(240, 242, 245));
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // ================= HEADER PANEL =================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JPanel titleGroup = new JPanel(new GridLayout(2, 1, 0, 5));
        titleGroup.setOpaque(false);
        
        lblTitleHeader = new JLabel("Dashboard", SwingConstants.LEFT);
        lblTitleHeader.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitleHeader.setForeground(new Color(15, 23, 42)); // Slate 900
        
        lblSubtitle = new JLabel("Ringkasan kehadiran karyawan hari ini", SwingConstants.LEFT);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(100, 116, 139)); // Slate 500
        
        titleGroup.add(lblTitleHeader);
        titleGroup.add(lblSubtitle);
        headerPanel.add(titleGroup, BorderLayout.CENTER);

        // Reload/Refresh Button
        btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRefresh.setBackground(new Color(59, 130, 246)); // Blue 500
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnRefresh.addActionListener(e -> refreshData());
        
        headerPanel.add(btnRefresh, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // ================= MAIN CONTAINER PANEL =================
        JPanel mainContainer = new JPanel(new BorderLayout(0, 25));
        mainContainer.setOpaque(false);

        // Cards Panel (Grid Layout, 4 columns)
        JPanel gridPanel = new JPanel(new GridLayout(1, 4, 18, 0));
        gridPanel.setOpaque(false);

        // Card 1: Total Karyawan (Indigo gradient)
        JPanel cardTotal = createCardPanel(
            new Color(99, 102, 241), new Color(79, 70, 229), "👥",
            lblTitleTotal = new JLabel("", SwingConstants.CENTER), 
            lblValTotal = new JLabel("0", SwingConstants.CENTER)
        );
        
        // Card 2: Hadir Hari Ini (Emerald gradient)
        JPanel cardHadir = createCardPanel(
            new Color(52, 211, 153), new Color(16, 185, 129), "✅",
            lblTitleHadir = new JLabel("", SwingConstants.CENTER), 
            lblValHadir = new JLabel("0", SwingConstants.CENTER)
        );

        // Card 3: Terlambat Hari Ini (Amber gradient)
        JPanel cardTerlambat = createCardPanel(
            new Color(251, 191, 36), new Color(245, 158, 11), "⏰",
            lblTitleTerlambat = new JLabel("", SwingConstants.CENTER), 
            lblValTerlambat = new JLabel("0", SwingConstants.CENTER)
        );

        // Card 4: Belum Presensi (Rose/Red gradient)
        JPanel cardBelum = createCardPanel(
            new Color(251, 113, 133), new Color(239, 68, 68), "❌",
            lblTitleBelum = new JLabel("", SwingConstants.CENTER), 
            lblValBelum = new JLabel("0", SwingConstants.CENTER)
        );

        gridPanel.add(cardTotal);
        gridPanel.add(cardHadir);
        gridPanel.add(cardTerlambat);
        gridPanel.add(cardBelum);
        mainContainer.add(gridPanel, BorderLayout.NORTH);

        // ================= RECENT ACTIVITY PANEL =================
        RoundedPanel activityPanel = new RoundedPanel(20, Color.WHITE);
        activityPanel.setLayout(new BorderLayout(0, 15));
        activityPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblTableTitle = new JLabel("Aktivitas Presensi Terbaru (Hari Ini)", SwingConstants.LEFT);
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTableTitle.setForeground(new Color(15, 23, 42)); // Slate 900
        activityPanel.add(lblTableTitle, BorderLayout.NORTH);

        // Table Model
        DefaultTableModel model = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID Karyawan", "Nama Karyawan", "Departemen", "Waktu", "Status"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableRecent = new JTable(model);
        tableRecent.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableRecent.setRowHeight(32);
        tableRecent.setShowGrid(false);
        tableRecent.setIntercellSpacing(new Dimension(0, 0));
        tableRecent.setSelectionBackground(new Color(241, 245, 249));
        tableRecent.setSelectionForeground(new Color(15, 23, 42));

        // Style Table Header
        JTableHeader header = tableRecent.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(248, 250, 252)); // Slate 50
        header.setForeground(new Color(100, 116, 139)); // Slate 500
        header.setPreferredSize(new Dimension(0, 36));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)));

        // Custom Cell Renderer for status alignment and color highlights
        tableRecent.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(241, 245, 249)));
                setHorizontalAlignment(SwingConstants.LEFT);
                setForeground(new Color(51, 65, 85)); // Slate 700

                // If Status Column (column 4)
                if (column == 4 && value != null) {
                    String val = value.toString();
                    if ("MASUK".equalsIgnoreCase(val) || "IN".equalsIgnoreCase(val)) {
                        setForeground(new Color(16, 185, 129)); // Green
                        setFont(new Font("Segoe UI", Font.BOLD, 13));
                    } else {
                        setForeground(new Color(239, 68, 68)); // Red
                        setFont(new Font("Segoe UI", Font.BOLD, 13));
                    }
                } else {
                    setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }

                if (isSelected) {
                    setBackground(new Color(241, 245, 249));
                } else {
                    setBackground(Color.WHITE);
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableRecent);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(241, 245, 249)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        activityPanel.add(scrollPane, BorderLayout.CENTER);

        mainContainer.add(activityPanel, BorderLayout.CENTER);
        add(mainContainer, BorderLayout.CENTER);
    }

    private JPanel createCardPanel(Color startColor, Color endColor, String iconText, JLabel lblTitle, JLabel lblValue) {
        GradientRoundedPanel panel = new GradientRoundedPanel(20, startColor, endColor);
        panel.setLayout(new BorderLayout(15, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Left Icon
        JLabel lblIcon = new JLabel(iconText);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        lblIcon.setForeground(new Color(255, 255, 255, 200));
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblIcon, BorderLayout.WEST);

        // Content
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setOpaque(false);

        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(new Color(241, 245, 249, 210));
        lblTitle.setHorizontalAlignment(SwingConstants.LEFT);

        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblValue.setForeground(Color.WHITE);
        lblValue.setHorizontalAlignment(SwingConstants.LEFT);

        textPanel.add(lblTitle);
        textPanel.add(lblValue);

        panel.add(textPanel, BorderLayout.CENTER);
        return panel;
    }

    public void refreshData() {
        lblValTotal.setText(String.valueOf(service.getJumlahKaryawan()));
        lblValHadir.setText(String.valueOf(service.getTotalHadirHariIni()));
        lblValTerlambat.setText(String.valueOf(service.getJumlahTerlambat()));
        lblValBelum.setText(String.valueOf(service.getBelumPresensi()));
        
        refreshRecentLogs();
    }

    private void refreshRecentLogs() {
        DefaultTableModel model = (DefaultTableModel) tableRecent.getModel();
        model.setRowCount(0);

        LogAbsensiService logService = new LogAbsensiService();
        KaryawanService karyawanService = new KaryawanService();

        List<LogAbsensi> listLog = logService.getAllLog();
        if (listLog == null || listLog.isEmpty()) {
            return;
        }

        // Ambil absensi hari ini saja, urutkan dari terbaru ke terlama
        LocalDate today = LocalDate.now();
        java.util.List<LogAbsensi> todayLogs = new java.util.ArrayList<>();
        for (LogAbsensi log : listLog) {
            if (log.getWaktuTap() != null && log.getWaktuTap().toLocalDate().equals(today)) {
                todayLogs.add(log);
            }
        }
        
        // Urutkan berdasarkan waktuTap descending
        todayLogs.sort((a, b) -> b.getWaktuTap().compareTo(a.getWaktuTap()));

        // Tampilkan maksimal 5 log terbaru
        int count = 0;
        for (LogAbsensi log : todayLogs) {
            if (count >= 5) break;

            String idTampil = "Anonymous";
            String namaTampil = "Guest";
            String deptTampil = "-";
            String hashedUid = log.getUidRfid();

            if (hashedUid != null && !hashedUid.isEmpty()) {
                Karyawan karyawan = karyawanService.findByUid(hashedUid);
                if (karyawan != null) {
                    idTampil = EncryptionUtils.decrypt(karyawan.getIdKaryawan());
                    if (idTampil == null) idTampil = karyawan.getIdKaryawan();
                    namaTampil = karyawan.getNamaLengkap();
                    deptTampil = karyawan.getDepartemen();
                } else {
                    idTampil = "UID: " + (hashedUid.length() > 8 ? hashedUid.substring(0, 8) : hashedUid);
                }
            }

            String waktu = log.getWaktuTap() != null
                    ? log.getWaktuTap().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                    : "-";

            String statusCode = log.getStatus();
            String statusTampil = "IN".equalsIgnoreCase(statusCode) ? "MASUK" : "PULANG";

            model.addRow(new Object[]{
                idTampil,
                namaTampil,
                deptTampil,
                waktu,
                statusTampil
            });
            count++;
        }
    }

    private void applyLanguage() {
        lblTitleTotal.setText(I18nService.get("ui.dashboard.totalEmployees"));
        lblTitleHadir.setText(I18nService.get("ui.dashboard.presentToday"));
        lblTitleTerlambat.setText(I18nService.get("ui.dashboard.lateToday"));
        lblTitleBelum.setText(I18nService.get("ui.dashboard.notPresentYet"));
        
        // Localized subtitle and titles from properties
        lblSubtitle.setText(I18nService.get("ui.dashboard.subtitle"));
        lblTableTitle.setText(I18nService.get("ui.dashboard.tableTitle"));
        btnRefresh.setText("🔄  " + I18nService.get("ui.dashboard.refresh"));
        
        refreshData();
    }

    @Override
    public void removeNotify() {
        I18nService.unregisterListener(languageListener);
        super.removeNotify();
    }
}
