package com.mycompany.mediscan.palette;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class AutoScaledLabel extends JLabel{
    private Image image;

    public AutoScaledLabel() {
        super();
    }

    // Konstruktor jika ingin langsung memasukkan ImageIcon
    public AutoScaledLabel(ImageIcon icon) {
        super();
        if (icon != null) {
            this.image = icon.getImage();
        }
    }

    // Override setIcon agar bisa menangkap objek Image di dalamnya
    @Override
    public void setIcon(Icon icon) {
        if (icon instanceof ImageIcon imageIcon) {
            this.image = imageIcon.getImage();
        } else {
            this.image = null;
        }
        
        // Kita sengaja TIDAK memanggil super.setIcon(icon) agar JLabel bawaan 
        // tidak menggambar ulang ikon asli yang ukurannya belum disesuaikan.
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            
            // RenderingHints agar gambar hasil resize tetap halus/tidak patah-patah
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            
            // Menggambar citra mengikuti ukuran lebar (getWidth) dan tinggi (getHeight) dari JLabel saat ini
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            g2.dispose();
        }
        
        // Panggil fungsi bawaan untuk menggambar teks (jika Anda menambahkan setText)
        super.paintComponent(g);
    }
}
