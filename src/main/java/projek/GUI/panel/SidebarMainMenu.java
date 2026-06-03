package projek.GUI.panel;

import projek.GUI.AdminPage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Sidebar menu bertema rumah sakit - menggunakan custom programmatic vector icons.
 */
public class SidebarMainMenu extends JPanel {

    // === HOSPITAL COLOR PALETTE ===
    private static final Color CLR_SIDEBAR_BG  = new Color(0, 83, 130);
    private static final Color CLR_HEADER_BG   = new Color(0, 53, 100);
    private static final Color CLR_SECTION_LBL = new Color(160, 210, 240);
    private static final Color CLR_ITEM_BG     = new Color(0, 83, 130);
    private static final Color CLR_ITEM_HOVER  = new Color(0, 130, 180);
    private static final Color CLR_ITEM_ACTIVE = new Color(0, 188, 212);
    private static final Color CLR_ACCENT      = new Color(0, 188, 212);
    private static final Color CLR_TEXT        = Color.WHITE;
    private static final Color CLR_TEXT_DIM    = new Color(180, 220, 255);

    private JButton activeButton = null;

    public SidebarMainMenu() {
        setLayout(new BorderLayout());
        setBackground(CLR_SIDEBAR_BG);
        setPreferredSize(new Dimension(230, 0));

        // ---- BRAND HEADER ----
        add(buildBrandHeader(), BorderLayout.NORTH);

        // ---- MENU ----
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(CLR_SIDEBAR_BG);
        menuPanel.setBorder(new EmptyBorder(8, 0, 8, 0));

        menuPanel.add(createSectionLabel("DATA MASTER"));
        menuPanel.add(createMenuItem("Karyawan",    new StaffIcon(),    "Karyawan"));
        menuPanel.add(createMenuItem("Log Absensi", new ClockIcon(),    "Log Absensi"));
        menuPanel.add(createMenuItem("Pengguna",    new ShieldIcon(),   "Pengguna"));
        menuPanel.add(Box.createVerticalStrut(6));

        menuPanel.add(createSectionLabel("RFID & ABSENSI"));
        menuPanel.add(createMenuItem("Scan RFID",       new RfidIcon(),       "Scan RFID"));
        menuPanel.add(createMenuItem("Rekap Absensi",   new DocumentIcon(),   "Rekap Absensi"));
        menuPanel.add(Box.createVerticalStrut(6));

        menuPanel.add(createSectionLabel("LAPORAN"));
        menuPanel.add(createMenuItem("Lap. Kehadiran",  new ChartIcon(),      "Lap. Kehadiran"));
        menuPanel.add(createMenuItem("Lap. Kinerja",    new ChartIcon(),      "Lap. Kinerja"));
        menuPanel.add(Box.createVerticalStrut(6));

        menuPanel.add(createSectionLabel("PENGATURAN"));
        menuPanel.add(createMenuItem("Pengaturan Umum", new GearIcon(),       "Pengaturan Umum"));
        menuPanel.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(menuPanel);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(8);
        scroll.setBackground(CLR_SIDEBAR_BG);

        add(scroll, BorderLayout.CENTER);

        // ---- FOOTER ----
        add(buildFooter(), BorderLayout.SOUTH);
    }

    // ─────────────────────────────────────────────
    private JPanel buildBrandHeader() {
        JPanel brand = new JPanel(new BorderLayout(10, 0));
        brand.setBackground(CLR_HEADER_BG);
        brand.setBorder(new EmptyBorder(14, 14, 14, 14));

        // Cross symbol menggunakan HTML agar lebih besar
        JLabel cross = new JLabel("<html><font color='#00BCD4' size='5'><b>+</b></font></html>");
        cross.setFont(new Font("Segoe UI", Font.BOLD, 30));
        cross.setForeground(CLR_ACCENT);

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textPanel.setOpaque(false);

        JLabel appName = new JLabel("MediScan RFID");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        appName.setForeground(CLR_TEXT);

        JLabel appSub = new JLabel("Absensi RS");
        appSub.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        appSub.setForeground(CLR_TEXT_DIM);

        textPanel.add(appName);
        textPanel.add(appSub);

        brand.add(cross, BorderLayout.WEST);
        brand.add(textPanel, BorderLayout.CENTER);

        // Cyan bottom border
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CLR_HEADER_BG);
        wrapper.add(brand, BorderLayout.CENTER);
        JSeparator sep = new JSeparator();
        sep.setForeground(CLR_ACCENT);
        sep.setBackground(CLR_ACCENT);
        wrapper.add(sep, BorderLayout.SOUTH);

        return wrapper;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(CLR_HEADER_BG);
        footer.setBorder(new EmptyBorder(8, 14, 8, 14));
        JLabel lbl = new JLabel("(c) 2025 MediScan RS  v1.0");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        lbl.setForeground(CLR_TEXT_DIM);
        footer.add(lbl, BorderLayout.CENTER);
        return footer;
    }

    private JLabel createSectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 9));
        lbl.setForeground(CLR_SECTION_LBL);
        lbl.setBorder(new EmptyBorder(10, 14, 3, 14));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));
        return lbl;
    }

    private JButton createMenuItem(String label, Icon icon, String key) {
        JButton btn = new JButton(label);
        btn.setIcon(icon);
        btn.setIconTextGap(10);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(CLR_TEXT);
        btn.setBackground(CLR_ITEM_BG);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(9, 18, 9, 10));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                if (btn != activeButton) btn.setBackground(CLR_ITEM_HOVER);
            }
            @Override public void mouseExited(MouseEvent e) {
                if (btn != activeButton) btn.setBackground(CLR_ITEM_BG);
            }
        });

        btn.addActionListener(e -> {
            if (activeButton != null) activeButton.setBackground(CLR_ITEM_BG);
            activeButton = btn;
            btn.setBackground(CLR_ITEM_ACTIVE);

            switch (key) {
                case "Karyawan":
                    showPage(new KaryawanPanel());
                    break;
                default:
                    showPage(buildPlaceholder(key));
                    break;
            }
        });

        return btn;
    }

    private JPanel buildPlaceholder(String title) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(236, 245, 255));
        JLabel lbl = new JLabel(
            "<html><center><b style='font-size:14px'>" + title + "</b><br><br>" +
            "<span style='color:#6699BB'>Halaman ini sedang dalam pengembangan.</span></center></html>"
        );
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(lbl);
        return p;
    }

    private void showPage(JPanel panel) {
        AdminPage.appContentPane.removeAll();
        if (panel != null) AdminPage.appContentPane.add(panel, BorderLayout.CENTER);
        AdminPage.appContentPane.revalidate();
        AdminPage.appContentPane.repaint();
    }

    // ─────────────────────────────────────────────
    // Programmatic vector icons
    // ─────────────────────────────────────────────
    private static class StaffIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground());
            g2.fillOval(x + 5, y + 1, 6, 6);
            g2.fillArc(x + 1, y + 8, 14, 10, 0, 180);
            g2.dispose();
        }
        public int getIconWidth() { return 16; }
        public int getIconHeight() { return 16; }
    }

    private static class ClockIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground());
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawOval(x + 1, y + 1, 14, 14);
            g2.drawLine(x + 8, y + 8, x + 8, y + 4);
            g2.drawLine(x + 8, y + 8, x + 11, y + 8);
            g2.dispose();
        }
        public int getIconWidth() { return 16; }
        public int getIconHeight() { return 16; }
    }

    private static class ShieldIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground());
            int[] px = {x + 8, x + 14, x + 14, x + 8, x + 2, x + 2};
            int[] py = {y + 1, y + 3, y + 9, y + 14, y + 9, y + 3};
            g2.fillPolygon(px, py, 6);
            g2.dispose();
        }
        public int getIconWidth() { return 16; }
        public int getIconHeight() { return 16; }
    }

    private static class RfidIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground());
            g2.drawRoundRect(x + 1, y + 3, 10, 10, 2, 2);
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawArc(x + 5, y + 1, 6, 6, -45, 90);
            g2.drawArc(x + 7, y - 1, 10, 10, -45, 90);
            g2.dispose();
        }
        public int getIconWidth() { return 16; }
        public int getIconHeight() { return 16; }
    }

    private static class DocumentIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground());
            g2.drawRect(x + 2, y + 1, 12, 14);
            g2.fillRect(x + 5, y + 4, 6, 2);
            g2.fillRect(x + 5, y + 7, 6, 2);
            g2.fillRect(x + 5, y + 10, 4, 2);
            g2.dispose();
        }
        public int getIconWidth() { return 16; }
        public int getIconHeight() { return 16; }
    }

    private static class ChartIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground());
            g2.drawLine(x + 1, y + 2, x + 1, y + 14);
            g2.drawLine(x + 1, y + 14, x + 14, y + 14);
            g2.fillRect(x + 3, y + 9, 2, 5);
            g2.fillRect(x + 6, y + 6, 2, 8);
            g2.fillRect(x + 9, y + 4, 2, 10);
            g2.dispose();
        }
        public int getIconWidth() { return 16; }
        public int getIconHeight() { return 16; }
    }

    private static class GearIcon implements Icon {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getForeground());
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(x + 3, y + 3, 10, 10);
            g2.drawLine(x + 8, y + 1, x + 8, y + 15);
            g2.drawLine(x + 1, y + 8, x + 15, y + 8);
            g2.drawLine(x + 3, y + 3, x + 13, y + 13);
            g2.drawLine(x + 3, y + 13, x + 13, y + 3);
            g2.fillOval(x + 7, y + 7, 2, 2);
            g2.dispose();
        }
        public int getIconWidth() { return 16; }
        public int getIconHeight() { return 16; }
    }
}
