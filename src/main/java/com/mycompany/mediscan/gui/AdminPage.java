package com.mycompany.mediscan.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import com.mycompany.mediscan.services.DigitalClockService;
import com.mycompany.mediscan.services.I18nService;
import com.mycompany.mediscan.gui.panel.DashboardPanel;

public class AdminPage extends javax.swing.JFrame {

    private Thread clockThread;
    private final I18nService.I18nChangeListener languageListener = this::applyLanguage;

    public AdminPage() {
        initComponents();
        
        // Start Digital Clock Thread (Multithreading)
        DigitalClockService clockService = new DigitalClockService(jLabel2, "dd MMMM yyyy HH:mm:ss");
        clockThread = clockService.getThread();
        clockThread.start();

        appContentPane.remove(jPanel3);

        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        
        // i18n
        applyLanguage();
        I18nService.registerListener(languageListener);

        appContentPane.setLayout(new java.awt.BorderLayout(0, 0));
        addContent(new DashboardPanel());
        
        this.revalidate();
        this.repaint();
    }
    
    private void applyLanguage() {
        // Translation updates for any frame UI if needed
        // For example, frame title
        this.setTitle(I18nService.get("ui.sidebar.dashboard") + " - MediScan");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        sidebarMainMenu1 = new com.mycompany.mediscan.gui.panel.SidebarMainMenu();
        jPanel4 = new javax.swing.JPanel();
        appContentPane = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 59, 111));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logoMediscan.png")));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); 
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Waktu Sekarang");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 686, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(97, 97, 97))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel2)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(226, 211, 188));
        jPanel2.setPreferredSize(new java.awt.Dimension(260, 365));
        jPanel2.setLayout(new java.awt.BorderLayout());

        sidebarMainMenu1.setBackground(new java.awt.Color(0, 59, 111));
        jPanel2.add(sidebarMainMenu1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel4.setBackground(new java.awt.Color(0, 59, 111));
        jPanel4.setPreferredSize(new java.awt.Dimension(893, 40));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1197, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel4, java.awt.BorderLayout.PAGE_END);

        appContentPane.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 947, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 425, Short.MAX_VALUE)
        );

        appContentPane.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(appContentPane, java.awt.BorderLayout.CENTER);

        pack();
    }

    @Override
    public void dispose() {
        if (clockThread != null && clockThread.isAlive()) {
            clockThread.interrupt();
        }
        I18nService.unregisterListener(languageListener);
        super.dispose();
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

        java.awt.EventQueue.invokeLater(() -> {
            new AdminPage().setVisible(true);
        });
    }

    public static javax.swing.JPanel appContentPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private com.mycompany.mediscan.gui.panel.SidebarMainMenu sidebarMainMenu1;

    public void addContent(JPanel panel){
        if(appContentPane.getComponentCount() > 0){
            appContentPane.removeAll();
        }
        
        appContentPane.add(panel, BorderLayout.CENTER);
        appContentPane.revalidate();
        appContentPane.repaint();       
    }
}
