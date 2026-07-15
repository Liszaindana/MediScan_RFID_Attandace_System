package com.mycompany.mediscan.gui;

import javax.swing.JLabel;

public class NewJFrame extends javax.swing.JFrame {

    private Thread delayThread;
    int count = 5;

    public NewJFrame() {
        initComponents();

        backgroundImagePanel1.setBackground("/images/bg2.png");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        backgroundImagePanel1 = new com.mycompany.mediscan.palette.BackgroundImagePanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_END);

        jLabel1.setBackground(new java.awt.Color(51, 51, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 100)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("OK");
        jLabel1.setOpaque(true);

        jButton1.setText("Mulai");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundImagePanel1Layout = new javax.swing.GroupLayout(backgroundImagePanel1);
        backgroundImagePanel1.setLayout(backgroundImagePanel1Layout);
        backgroundImagePanel1Layout.setHorizontalGroup(
            backgroundImagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundImagePanel1Layout.createSequentialGroup()
                .addGroup(backgroundImagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundImagePanel1Layout.createSequentialGroup()
                        .addGap(343, 343, 343)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundImagePanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jButton1)))
                .addContainerGap(269, Short.MAX_VALUE))
        );
        backgroundImagePanel1Layout.setVerticalGroup(
            backgroundImagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundImagePanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jButton1)
                .addGap(44, 44, 44)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        getContentPane().add(backgroundImagePanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        updateLabelWithDelay(jLabel1, "OK");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void updateLabelWithDelay(JLabel comp, String info) {
        // 1. Mencegah Penumpukan: Hentikan thread lama jika masih berjalan
        if (delayThread != null && delayThread.isAlive()) {
            delayThread.interrupt();
        }

        // 2. Inisialisasi Thread Baru
        delayThread = new Thread(() -> {
            try {
                // Perulangan hitung mundur dari 5 ke 1
                for (int i = 5; i >= 1; i--) {
                    final String text = String.valueOf(i);
                    
                    // 3. Update GUI secara aman menggunakan invokeLater [1]
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        comp.setText(text);
                    });

                    // 4. Jeda 1 detik (1000ms) seperti pada DigitalClockService [2]
                    Thread.sleep(1000);
                }
                
                // Aksi opsional setelah countdown selesai
                javax.swing.SwingUtilities.invokeLater(() -> comp.setText(info));

            } catch (InterruptedException e) {
                // Penanganan jika thread dihentikan paksa (Interrupted)
                System.out.println("Countdown " + Thread.currentThread().getName() + " dihentikan.");
            }
        });

        // 5. Konfigurasi Thread Standar Industri
        delayThread.setName("delayThread"); // Menggunakan nama sesuai permintaan
        delayThread.setDaemon(true); // Agar otomatis mati saat aplikasi ditutup
        
        // 6. Jalankan Thread (Pindah ke fase Running)
        delayThread.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new NewJFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.mycompany.mediscan.palette.BackgroundImagePanel backgroundImagePanel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
