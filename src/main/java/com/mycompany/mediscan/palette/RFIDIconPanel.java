package com.mycompany.mediscan.palette;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class RFIDIconPanel extends JPanel {

    public RFIDIconPanel() {
        setPreferredSize(new Dimension(60, 40));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Gambar Badan Kartu Kredit/RFID
        g2.setColor(new Color(100, 110, 125));
        g2.fillRoundRect(5, 2, 50, 36, 6, 6);

        // Chip Emas/Garis RFID Sederhana
        g2.setColor(new Color(230, 180, 80));
        g2.fillRoundRect(12, 12, 10, 10, 2, 2);

        // Sinyal Waves Lambang Contactless
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawArc(24, 10, 10, 18, -45, 90);
        g2.drawArc(28, 7, 14, 24, -45, 90);

        g2.dispose();
    }
}
