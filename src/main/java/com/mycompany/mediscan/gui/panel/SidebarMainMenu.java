/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mediscan.gui.panel;

import com.mycompany.mediscan.gui.AdminPage;
import com.mycompany.mediscan.gui.AttendancePage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author mnish
 */
public class SidebarMainMenu extends JPanel {

    private final Color SIDEBAR_BG = new Color(30, 41, 59);
    private final Color MENU_BG = new Color(51, 65, 85);
    private final Color SUBMENU_BG = new Color(15, 23, 42);
    private final Color HOVER_BG = new Color(37, 99, 235);
    private final Color ACTIVE_BG = new Color(59, 130, 246);
    private final Color TEXT_COLOR = Color.WHITE;

    private JButton activeButton = null;

    public SidebarMainMenu() {
        this.setPreferredSize(new Dimension(260, 0));
        this.setBackground(new Color(33, 37, 41));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // DASHBOARD SECTION
        this.add(createAccordion(
                "Data Master",
                new String[]{"Karyawan", "Log Absensi", "Pengguna"}
        ));

        // MANAGEMENT SECTION
        this.add(createAccordion(
                "Attendance",
                new String[]{"KiosK", "Riwayat", "Analisis"}
        ));

        // SETTINGS SECTION
        this.add(createAccordion(
                "Settings",
                new String[]{"General"}
        ));

        // REPORT SECTION
        this.add(createAccordion(
                "Report",
                new String[]{"Log Absensi", "Performance"}
        ));

        this.add(Box.createVerticalGlue());
    }

    private JPanel createAccordion(String title, String[] menus) {

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(33, 37, 41));

        // HEADER BUTTON
        JButton header = new JButton(title);

        header.setFocusPainted(false);
        header.setBackground(MENU_BG);
        header.setForeground(TEXT_COLOR);
        header.setBorder(new EmptyBorder(15, 15, 15, 15));
        header.setHorizontalAlignment(SwingConstants.LEFT);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // BODY PANEL
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(SIDEBAR_BG);

        for (String menu : menus) {

            JButton btn = new JButton(menu);

            btn.setFocusPainted(false);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            btn.setBackground(SUBMENU_BG);
            btn.setForeground(TEXT_COLOR);
            btn.setBorder(new EmptyBorder(10, 20, 10, 10));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // HOVER
            btn.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(HOVER_BG);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(SUBMENU_BG);
                }
            });

            // ACTION
            btn.addActionListener(e -> {

                switch (menu) {

                    case "Karyawan":
                        showPage(new KaryawanPanel());
                        break;

                    case "Log Absensi":
                        showPage(null);
                        break;

                    case "Pengguna":
                        showPage(null);
                        break;

                    case "KiosK":
                        showPage(new AttendancePage());
                        break;

                    case "Products":
                        showPage(null);
                        break;

                    case "Orders":
                        showPage(null);
                        break;

                    case "General":
                        showPage(new Settings());
                        break;

                    case "Security":
                        showPage(null);
                        break;
                }

                // RESET OLD ACTIVE BUTTON
                if (activeButton != null) {
                    activeButton.setBackground(SUBMENU_BG);
                }

                // SET NEW ACTIVE BUTTON
                activeButton = btn;
                btn.setBackground(ACTIVE_BG);
            });

            body.add(btn);
        }

        // DEFAULT COLLAPSE
        body.setVisible(false);

        header.addActionListener(e -> {

            body.setVisible(!body.isVisible());

            container.revalidate();
            container.repaint();
        });

        container.add(header);
        container.add(body);

        return container;
    }

    private void showPage(Component comp) {
        switch (comp) {
            case JPanel pnl -> {
                AdminPage.appContentPane.removeAll();
                AdminPage.appContentPane.add(pnl, BorderLayout.CENTER);
                
                AdminPage.appContentPane.revalidate();
                AdminPage.appContentPane.repaint();
            }
            case JFrame frm -> { 
                JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(SidebarMainMenu.this);
                if (mainFrame != null) {
                    mainFrame.dispose();
                }                
                
                frm.setExtendedState(Frame.MAXIMIZED_BOTH);
                frm.setVisible(true);
            }
            default -> {
            }
        }
    }

}
