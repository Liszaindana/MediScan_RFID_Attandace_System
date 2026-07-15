package com.mycompany.mediscan.gui.panel;

import com.mycompany.mediscan.objects.LogAbsensi;
import com.mycompany.mediscan.services.LogAbsensiService;
import com.mycompany.mediscan.services.KaryawanService;
import com.mycompany.mediscan.objects.Karyawan;
import com.mycompany.mediscan.util.EncryptionUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.mycompany.mediscan.services.I18nService;

public class LogAbsensiPanel extends javax.swing.JPanel {

    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblTitle;
    private final I18nService.I18nChangeListener languageListener = this::applyLanguage;

    public LogAbsensiPanel() {
        initComponents();
        applyLanguage();
        I18nService.registerListener(languageListener);
        showData();
    }
    
    private void showData() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        LogAbsensiService service = new LogAbsensiService();
        KaryawanService karyawanService = new KaryawanService();

        List<LogAbsensi> listLog = service.getAllLog();

        if (listLog == null || listLog.isEmpty()) {
            return;
        }

        for (LogAbsensi log : listLog) {
            String idTampil = I18nService.get("log.anonymous");
            String hashedUid = log.getUidRfid();

            if (hashedUid != null && !hashedUid.isEmpty()) {
                Karyawan karyawan = karyawanService.findByUid(hashedUid);
                if (karyawan != null) {
                    idTampil = EncryptionUtils.decrypt(karyawan.getIdKaryawan());
                } else {
                    String shortUid = hashedUid.length() > 8
                            ? hashedUid.substring(0, 8)
                            : hashedUid;
                    idTampil = I18nService.get("log.uid") + ": " + shortUid;
                }
            }

            String waktu = log.getWaktuTap() != null
                    ? log.getWaktuTap().toString()
                    : "-";

            String statusCode = log.getStatus();
            String statusTampil = "IN".equalsIgnoreCase(statusCode)
                    ? I18nService.get("ui.status.in")
                    : I18nService.get("ui.status.out");

            model.addRow(new Object[]{
                idTampil,
                statusTampil,
                waktu
            });
        }
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblTitle = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(245, 247, 250));
        jPanel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel1.setLayout(new java.awt.BorderLayout(0, 15));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblTitle.setForeground(new java.awt.Color(30, 41, 59));
        lblTitle.setText("Log Absensi");
        jPanel1.add(lblTitle, java.awt.BorderLayout.NORTH);

        jTable1.setBackground(new java.awt.Color(255, 255, 255));
        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTable1.setRowHeight(24);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Id", "Status", "Waktu"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        
        jScrollPane1.setViewportView(jTable1);
        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }

    private void applyLanguage() {
        lblTitle.setText(I18nService.get("ui.sidebar.attendance"));
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setColumnIdentifiers(new Object[]{
            I18nService.get("log.id"),
            I18nService.get("log.status"),
            I18nService.get("log.time")
        });

        showData();
    }

    @Override
    public void removeNotify() {
        I18nService.unregisterListener(languageListener);
        super.removeNotify();
    }
}
