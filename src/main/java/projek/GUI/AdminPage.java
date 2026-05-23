package projek.GUI;

import projek.object.Karyawan;
import projek.services.KaryawanService;
import java.awt.Color;

public class AdminPage extends javax.swing.JFrame {

    // ─── Hospital Color Palette ───────────────────────────────────────────────
    private static final Color CLR_PRIMARY    = new Color(0, 120, 174);   // Medical Blue
    private static final Color CLR_SUCCESS    = new Color(0, 168, 143);   // Teal-Green (Save)
    private static final Color CLR_SUCCESS_DK = new Color(0, 130, 110);
    private static final Color CLR_AMBER      = new Color(217, 119, 6);   // Amber (Update)
    private static final Color CLR_AMBER_DK   = new Color(180, 95, 0);
    private static final Color CLR_SLATE      = new Color(100, 116, 139); // Slate (Refresh)
    private static final Color CLR_SLATE_DK   = new Color(71, 85, 105);
    private static final Color CLR_BG         = new Color(245, 248, 252);
    private static final Color CLR_CARD       = new Color(255, 255, 255);
    private static final Color CLR_BORDER     = new Color(219, 229, 239);
    private static final Color CLR_TEXT_M     = new Color(71, 100, 130);

    public AdminPage() {
        initComponents();
        styleCustomComponents();
        showData(""); //tampilkan seluruh data karyawan
    }

    private void styleCustomComponents() {
        setLocationRelativeTo(null);
        setTitle("MediScan — Kelola Karyawan Rumah Sakit");

        // ── 1. Create a header bar at the very top ─────────────────────────────
        javax.swing.JPanel headerBar = new javax.swing.JPanel(new java.awt.BorderLayout());
        headerBar.setBackground(CLR_PRIMARY);
        headerBar.setPreferredSize(new java.awt.Dimension(0, 60));
        headerBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 24, 0, 24));

        javax.swing.JPanel headerLeft = new javax.swing.JPanel(
            new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 15));
        headerLeft.setOpaque(false);

        javax.swing.JLabel lblHeaderIcon = new javax.swing.JLabel("✚");
        lblHeaderIcon.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22));
        lblHeaderIcon.setForeground(new Color(255, 255, 255, 180));

        javax.swing.JLabel lblHeaderTitle = new javax.swing.JLabel(
            "Manajemen Data Karyawan Rumah Sakit");
        lblHeaderTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        lblHeaderTitle.setForeground(CLR_CARD);

        headerLeft.add(lblHeaderIcon);
        headerLeft.add(lblHeaderTitle);
        headerBar.add(headerLeft, java.awt.BorderLayout.WEST);

        // ── 2. Top Form Panel (jPanel1) ────────────────────────────────────────
        jPanel1.setBackground(CLR_CARD);
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, CLR_BORDER));

        // Use a wrapper panel to hold both headerBar and jPanel1 inside PAGE_START
        javax.swing.JPanel topContainer = new javax.swing.JPanel();
        topContainer.setLayout(new javax.swing.BoxLayout(topContainer, javax.swing.BoxLayout.Y_AXIS));
        topContainer.add(headerBar);
        topContainer.add(jPanel1);
        getContentPane().add(topContainer, java.awt.BorderLayout.PAGE_START);

        // Labels — hospital navy-muted
        jLabel1.setForeground(CLR_TEXT_M);
        jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel2.setForeground(CLR_TEXT_M);
        jLabel2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel3.setForeground(CLR_TEXT_M);
        jLabel3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel4.setForeground(CLR_TEXT_M);
        jLabel4.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

        // Inputs — clean light-blue border
        Color inputBorder = new Color(186, 212, 232);
        txtUID.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        txtUID.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(inputBorder, 1, true),
            javax.swing.BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        txtKRID.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        txtKRID.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(inputBorder, 1, true),
            javax.swing.BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        txtKRName.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        txtKRName.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(inputBorder, 1, true),
            javax.swing.BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        txtKRDept.setBackground(CLR_CARD);
        txtKRDept.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        txtKRDept.setBorder(new javax.swing.border.LineBorder(inputBorder, 1, true));

        // Separator
        jSeparator1.setForeground(CLR_BORDER);

        // ── 3. Buttons Panel (jPanel3) — hospital-themed colors ────────────────
        jPanel3.setBackground(CLR_CARD);

        // Save — teal-green (health/positive action)
        btnSave.setBackground(CLR_SUCCESS);
        btnSave.setForeground(CLR_CARD);
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btnSave.isEnabled()) btnSave.setBackground(CLR_SUCCESS_DK);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                if (btnSave.isEnabled()) btnSave.setBackground(CLR_SUCCESS);
            }
        });

        // Update — amber/orange (caution/edit action)
        btnUpdate.setBackground(CLR_AMBER);
        btnUpdate.setForeground(CLR_CARD);
        btnUpdate.setFocusPainted(false);
        btnUpdate.setBorderPainted(false);
        btnUpdate.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btnUpdate.isEnabled()) btnUpdate.setBackground(CLR_AMBER_DK);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                if (btnUpdate.isEnabled()) btnUpdate.setBackground(CLR_AMBER);
            }
        });

        // Refresh — neutral slate
        btnRefresh.setBackground(CLR_SLATE);
        btnRefresh.setForeground(CLR_CARD);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorderPainted(false);
        btnRefresh.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                btnRefresh.setBackground(CLR_SLATE_DK);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                btnRefresh.setBackground(CLR_SLATE);
            }
        });

        // ── 4. Search Area ────────────────────────────────────────────────────
        jPanel2.setBackground(CLR_CARD);
        txtCari.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        txtCari.setForeground(new Color(15, 40, 70));
        txtCari.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(inputBorder, 1, true),
            javax.swing.BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        // ── 5. Content / Card List Area ───────────────────────────────────────
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 16, 16));
        jScrollPane1.setBackground(CLR_BG);
        jScrollPane1.getViewport().setBackground(CLR_BG);
        jPanel4.setBackground(CLR_BG);
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUID = new javax.swing.JTextField();
        txtKRID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtKRName = new javax.swing.JTextField();
        txtKRDept = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        txtCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("UID");

        jLabel2.setText("ID Karyawan");

        jLabel3.setText("Nama Karyawan");

        jLabel4.setText("Departemen");

        txtKRDept.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrasi, Keuangan, & SDM", "Rekam Medis & Pendaftaran", "Pelayanan Medis (Dokter & Perawat)", "Penunjang Medis (Laboratorium, Radiologi, & Farmasi)", "Teknologi Informasi & SIRS", "Logistik, Sarpras, & Keamanan (IPSRS)", "Komite Medis & Manajemen RS" }));

        btnSave.setBackground(new java.awt.Color(0, 0, 255));
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(255, 153, 0));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.setEnabled(false);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(0, 153, 0));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                    .addComponent(btnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnUpdate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCari.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCari.setForeground(new java.awt.Color(0, 0, 255));
        txtCari.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, java.awt.Color.GRAY)); // Menggunakan border standar
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });
        jPanel2.add(txtCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUID)
                    .addComponent(txtKRID, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtKRDept, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtKRName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtUID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txtKRName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txtKRID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtKRDept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1216, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel4);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        Karyawan K = new Karyawan();
        K.setUidRfid(txtUID.getText());
        K.setIdKaryawan(txtKRID.getText()); 
        K.setNamaLengkap(txtKRName.getText());
        K.setDepartemen(txtKRDept.getSelectedItem().toString()); 
        KaryawanService service = new KaryawanService();
        service.tambahKaryawan(K);
        showData("");
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        refresAll();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        showData(txtCari.getText());
    }//GEN-LAST:event_txtCariKeyReleased

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        Karyawan K = new Karyawan();
        K.setUidRfid(txtUID.getText());
        K.setIdKaryawan(txtKRID.getText()); 
        K.setNamaLengkap(txtKRName.getText());
        K.setDepartemen(txtKRDept.getSelectedItem().toString()); 
        KaryawanService service = new KaryawanService();
        service.updateKaryawan(K);
        refresAll();
    }//GEN-LAST:event_btnUpdateActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new AdminPage().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    public static javax.swing.JButton btnSave;
    public static javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private static javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtCari;
    public static javax.swing.JComboBox<String> txtKRDept;
    public static javax.swing.JTextField txtKRID;
    public static javax.swing.JTextField txtKRName;
    public static javax.swing.JTextField txtUID;
    // End of variables declaration//GEN-END:variables

    public static void showData(String key) {
        KaryawanService K = new KaryawanService();
        K.tampilKaryawan(jPanel4, key);
    }

    private void refresAll() {
        showData("");
        txtUID.setText("");
        txtKRID.setText("");
        txtKRName.setText("");
        txtKRDept.setSelectedIndex(0); 
        btnUpdate.setEnabled(false); 
        txtUID.requestFocus();
    }
}
