/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mediscan.palette;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author mnish
 */
public class BackgroundImagePanel extends JPanel{
    private Image backgroundImage;

    public BackgroundImagePanel() {
    }
    
    public void setBackground(String imagePath){
        this.backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        this.revalidate();
        this.repaint();
    }

    // Constructor that takes the image path
    public BackgroundImagePanel(String imagePath) {
        this.setLayout(new BorderLayout()); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the image scaled to the current width and height of the panel
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
