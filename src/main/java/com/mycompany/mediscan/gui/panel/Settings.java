package com.mycompany.mediscan.gui.panel;

import java.util.prefs.Preferences;
import java.util.Locale;
import javax.swing.JOptionPane;
import com.mycompany.mediscan.services.I18nService;
import javax.swing.border.TitledBorder;

public class Settings extends javax.swing.JPanel {
    public static String statusAbsen;
    public static Preferences prefs = Preferences.userNodeForPackage(Settings.class);
    private final I18nService.I18nChangeListener languageListener = this::applyLanguage;

    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.mycompany.mediscan.palette.SlidingStatusToggle slidingStatusToggle1;
    private TitledBorder statusBorder;

    public Settings() {
        initComponents();
        applyLanguage();
        I18nService.registerListener(languageListener);
        
        // Set pilihan sesuai bahasa aktif saat ini
        String currentLang = I18nService.getCurrentLocale().getLanguage();
        switch (currentLang) {
            case "en" -> jComboBox1.setSelectedItem("English");
            case "nl" -> jComboBox1.setSelectedItem("Belanda");
            default -> jComboBox1.setSelectedItem("Indonesia");
        }
        
        statusAbsen = prefs.get("LAST_STATUS", "IN");
        slidingStatusToggle1.setStatusByString("OUT".equalsIgnoreCase(statusAbsen) ? "Pulang" : "Masuk");
    }

    private void initComponents() {
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        slidingStatusToggle1 = new com.mycompany.mediscan.palette.SlidingStatusToggle();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(19, 19, 19, 19));
        jPanel1.setLayout(new java.awt.GridLayout(2, 2, 15, 15));

        statusBorder = javax.swing.BorderFactory.createTitledBorder(
            new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), 
            "Status Absensi", 
            javax.swing.border.TitledBorder.CENTER, 
            javax.swing.border.TitledBorder.DEFAULT_POSITION, 
            new java.awt.Font("Segoe UI", 1, 14)
        );
        jPanel3.setBorder(statusBorder);

        slidingStatusToggle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slidingStatusToggle1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(slidingStatusToggle1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(slidingStatusToggle1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        // Dummy panels to complete 2x2 grid
        jPanel4.setOpaque(false);
        jPanel1.add(jPanel4);
        jPanel5.setOpaque(false);
        jPanel1.add(jPanel5);
        jPanel6.setOpaque(false);
        jPanel1.add(jPanel6);

        jTabbedPane1.addTab("General", jPanel1);

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); 
        jLabel1.setText("Bahasa Sistem");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Indonesia", "English", "Belanda" }));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jButton1.setText("Simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, 200, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jButton1)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(245, 247, 250));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 30, 30));
        jPanel2.add(jPanel8);

        jTabbedPane1.addTab("Preferensi", jPanel2);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }

    private void slidingStatusToggle1ActionPerformed(java.awt.event.ActionEvent evt) {
        String toggleVal = slidingStatusToggle1.getStatusString();
        statusAbsen = "Pulang".equalsIgnoreCase(toggleVal) ? "OUT" : "IN";
        prefs.put("LAST_STATUS", statusAbsen);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String selected = jComboBox1.getSelectedItem().toString();
        java.util.Locale locale = switch (selected) {
            case "English" -> new java.util.Locale("en");
            case "Belanda" -> new java.util.Locale("nl");
            default -> new java.util.Locale("id");
        };
        I18nService.setLocale(locale);
        JOptionPane.showMessageDialog(this, "Bahasa berhasil diubah! / Language updated!");
    }

    private void applyLanguage() {
        jTabbedPane1.setTitleAt(0, I18nService.get("ui.tab.general"));
        jTabbedPane1.setTitleAt(1, I18nService.get("ui.tab.preferences"));
        jLabel1.setText(I18nService.get("ui.setting.lang"));
        jButton1.setText(I18nService.get("ui.btn.save"));
        
        if (statusBorder != null) {
            statusBorder.setTitle(I18nService.get("ui.title.attendanceStatus"));
            jPanel3.repaint();
        }
    }

    @Override
    public void removeNotify() {
        I18nService.unregisterListener(languageListener);
        super.removeNotify();
    }
}
