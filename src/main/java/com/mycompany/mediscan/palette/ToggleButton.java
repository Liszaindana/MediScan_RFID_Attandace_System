package com.mycompany.mediscan.palette;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JToggleButton;

public class ToggleButton extends JToggleButton {
    
    // Warna tema Bootstrap Success (Hijau) & Danger (Merah)
    private final Color COLOR_MASUK = new Color(25, 135, 84);       // #198754
    private final Color COLOR_MASUK_HOVER = new Color(21, 115, 71);
    
    private final Color COLOR_PULANG = new Color(220, 53, 69);      // #dc3545
    private final Color COLOR_PULANG_HOVER = new Color(187, 45, 59);

    private boolean isHovered = false;
    private final int cornerRadius = 8; // Sudut tumpul modern
    
    public ToggleButton() {
        super("Masuk"); // Status awal secara default
        
        // Matikan style bawaan Swing yang kaku
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        
        // Font tebal dan kursor tangan (pointer)
        setFont(new Font("SansSerif", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Listener untuk efek Hover (saat mouse mendekat/menjauh)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });

        // Listener untuk mengubah teks secara otomatis saat diklik
        addActionListener(e -> {
            if (isSelected()) {
                setText("Pulang");
            } else {
                setText("Masuk");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Tentukan warna berdasarkan status "Masuk" atau "Pulang"
        if (isSelected()) {
            // STATUS: PULANG (Merah)
            g2.setColor(isHovered ? COLOR_PULANG_HOVER : COLOR_PULANG);
        } else {
            // STATUS: MASUK (Hijau)
            g2.setColor(isHovered ? COLOR_MASUK_HOVER : COLOR_MASUK);
        }

        // Gambar background rounded rect
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        
        // Warna teks selalu putih agar kontras dan terbaca bersih
        setForeground(Color.WHITE);

        g2.dispose();
        super.paintComponent(g); // Menggambar teks di tengah
    }
}