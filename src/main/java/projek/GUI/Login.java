package projek.GUI;

import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.model.Filters;

public class Login extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Login.class.getName());

    /**
     * Creates new form Login
     */
    // ─── Hospital Color Palette (consistent with Dashboard & Attendance) ─────
    private static final Color CLR_PRIMARY    = new Color(0, 120, 174);  // Medical Blue
    private static final Color CLR_PRIMARY_DK = new Color(0, 91, 132);   // Hover
    private static final Color CLR_BG         = new Color(245, 248, 252); // Light neutral
    private static final Color CLR_BORDER     = new Color(219, 229, 239);
    private static final Color CLR_TEXT_H     = new Color(15, 40, 70);   // dark navy
    private static final Color CLR_TEXT_M     = new Color(71, 100, 130); // muted

    public Login() {
        initComponents();
        applyHospitalTheme();
    }

    private void applyHospitalTheme() {
        // Background panel (left side / full)
        jPanel1.setBackground(CLR_BG);
        jPanel1.setLayout(new GridBagLayout());
        jPanel1.add(jPanel2, new GridBagConstraints());

        // Card — white with subtle medical-blue border
        jPanel2.setBackground(Color.WHITE);
        jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(CLR_BORDER, 1, true),
            javax.swing.BorderFactory.createEmptyBorder(30, 36, 30, 36)
        ));

        // Input fields — clean with focus-friendly border
        Color inputBorder = new Color(186, 212, 232);
        jTextField1.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(inputBorder, 1, true),
            javax.swing.BorderFactory.createEmptyBorder(9, 12, 9, 12)
        ));
        jTextField1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        jPasswordField1.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(inputBorder, 1, true),
            javax.swing.BorderFactory.createEmptyBorder(9, 12, 9, 12)
        ));
        jPasswordField1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

        // Title — hospital navy
        jLabel1.setForeground(CLR_TEXT_H);
        jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Field labels
        jLabel2.setForeground(CLR_TEXT_M);
        jLabel2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        jLabel3.setForeground(CLR_TEXT_M);
        jLabel3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

        // Login button — medical blue
        btnLogin.setBackground(CLR_PRIMARY);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btnLogin.setBackground(CLR_PRIMARY_DK);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                btnLogin.setBackground(CLR_PRIMARY);
            }
        });

        // Load logo
        try {
            java.io.File logoFile = new java.io.File("src/assets/logo.png");
            ImageIcon logoIcon = null;
            if (logoFile.exists()) {
                logoIcon = new ImageIcon(logoFile.getAbsolutePath());
            } else {
                java.net.URL imgUrl = Login.class.getResource("/assets/logo.png");
                if (imgUrl != null) {
                    logoIcon = new ImageIcon(imgUrl);
                }
            }
            if (logoIcon != null) {
                Image img = logoIcon.getImage();
                Image scaledImg = img.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                jLabelLogo.setIcon(new ImageIcon(scaledImg));
                jLabelLogo.setText("");
            } else {
                // Fallback: hospital cross emoji
                jLabelLogo.setText("✚");
                jLabelLogo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 52));
                jLabelLogo.setForeground(CLR_PRIMARY);
                jLabelLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            }
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Gagal memuat logo: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabelLogo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(242, 242, 242));

        jPanel1.setBackground(new java.awt.Color(15, 21, 30));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("MediScan Attendance");

        jLabel2.setText("Username");

        jLabel3.setText("Password");

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                            .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel1)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(243, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String username = jTextField1.getText().trim();
        String password = new String(jPasswordField1.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Username dan Password tidak boleh kosong!", 
                "Login Gagal", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean authenticated = false;
        
        // 1. Coba otentikasi menggunakan MongoDB
        try {
            MongoDatabase database = projek.util.MongoManager.getDatabase();
            if (database != null) {
                MongoCollection<Document> usersCol = database.getCollection("users");
                Document userDoc = usersCol.find(Filters.and(
                    Filters.eq("username", username),
                    Filters.eq("password", password)
                )).first();
                
                if (userDoc != null) {
                    authenticated = true;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.WARNING, "Koneksi MongoDB gagal atau collection users tidak ditemukan: " + ex.getMessage());
        }

        // 2. Fallback menggunakan kredensial default admin/admin
        if (!authenticated) {
            if ("admin".equals(username) && "admin".equals(password)) {
                authenticated = true;
            }
        }

        if (authenticated) {
            JOptionPane.showMessageDialog(this, 
                "Login berhasil! Selamat datang, " + username + ".", 
                "Login Sukses", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Buka DashboardPage
            new DashboardPage().setVisible(true);
            
            // Tutup frame login saat ini
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Username atau Password salah!", 
                "Login Gagal", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
