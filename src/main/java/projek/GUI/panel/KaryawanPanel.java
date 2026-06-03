package projek.GUI.panel;

import projek.object.Karyawan;
import projek.services.KaryawanService;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * KaryawanPanel – Manajemen Data Staf Rumah Sakit.
 * Tema: Hospital Blue (#005382) + White + Cyan Accent
 */
public class KaryawanPanel extends JPanel {

    // === HOSPITAL PALETTE ===
    private static final Color CLR_PRIMARY     = new Color(0, 83, 130);
    private static final Color CLR_PRIMARY_DK  = new Color(0, 53, 100);
    private static final Color CLR_ACCENT      = new Color(0, 188, 212);
    private static final Color CLR_SUCCESS     = new Color(0, 168, 107);
    private static final Color CLR_AMBER       = new Color(245, 158, 11);
    private static final Color CLR_DANGER      = new Color(220, 38, 38);
    private static final Color CLR_BG          = new Color(236, 245, 255);
    private static final Color CLR_CARD        = Color.WHITE;
    private static final Color CLR_BORDER      = new Color(200, 220, 240);
    private static final Color CLR_TEXT_MAIN   = new Color(15, 30, 60);
    private static final Color CLR_TEXT_DIM    = new Color(100, 120, 150);
    private static final Color CLR_TABLE_HEAD  = new Color(0, 83, 130);
    private static final Color CLR_TABLE_ALT   = new Color(240, 248, 255);

    // Components
    private JTextField txtUID, txtKRID, txtKRName;
    public static JComboBox<String> txtKRDept;
    private JButton btnSave, btnUpdate, btnRefresh, btnDelete;
    private JTextField txtCari;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel statsLabel;
    private static JPanel jPanel4; // kept for KaryawanService compat

    public KaryawanPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(CLR_BG);

        add(buildTopBar(), BorderLayout.NORTH);
        add(buildFormCard(), BorderLayout.WEST);
        add(buildTableArea(), BorderLayout.CENTER);

        showData("");
    }


    // ─────────────────────────────────────────────────────────
    // TOP BAR
    // ─────────────────────────────────────────────────────────
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(CLR_PRIMARY_DK);
        bar.setBorder(new MatteBorder(0, 0, 2, 0, CLR_ACCENT));
        bar.setPreferredSize(new Dimension(0, 52));

        // Left — page title
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        left.setOpaque(false);
        JLabel icon = new JLabel("👨‍⚕️");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        JLabel title = new JLabel("Manajemen Data Karyawan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        JLabel sub = new JLabel("  ·  Staf & Tenaga Medis");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        sub.setForeground(new Color(180, 220, 255));
        left.add(icon);
        left.add(title);
        left.add(sub);

        // Right — search bar
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        right.setOpaque(false);
        txtCari = new JTextField(18);
        txtCari.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtCari.putClientProperty("JTextField.placeholderText", "🔍  Cari nama / ID...");
        txtCari.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CLR_ACCENT, 1, true),
            new EmptyBorder(4, 10, 4, 10)
        ));
        txtCari.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) { showData(txtCari.getText()); }
        });
        right.add(new JLabel("  🔍 "));
        right.add(txtCari);

        bar.add(left, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        bar.add(Box.createVerticalStrut(14), BorderLayout.NORTH);
        bar.add(Box.createVerticalStrut(14), BorderLayout.SOUTH);
        return bar;
    }

    // ─────────────────────────────────────────────────────────
    // FORM CARD (left panel)
    // ─────────────────────────────────────────────────────────
    private JPanel buildFormCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CLR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 0, 1, CLR_BORDER),
            new EmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(300, 0));

        // Card header
        JLabel cardTitle = new JLabel("📋  Form Input Data");
        cardTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cardTitle.setForeground(CLR_PRIMARY);
        cardTitle.setAlignmentX(LEFT_ALIGNMENT);
        card.add(cardTitle);
        card.add(Box.createVerticalStrut(4));

        JSeparator sep = new JSeparator();
        sep.setForeground(CLR_ACCENT);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        card.add(sep);
        card.add(Box.createVerticalStrut(16));

        // Fields
        String[] labels = {"UID RFID", "ID Karyawan", "Nama Lengkap"};
        txtUID    = buildTextField();
        txtKRID   = buildTextField();
        txtKRName = buildTextField();
        JTextField[] fields = {txtUID, txtKRID, txtKRName};

        for (int i = 0; i < labels.length; i++) {
            card.add(buildFieldLabel(labels[i]));
            card.add(Box.createVerticalStrut(4));
            card.add(fields[i]);
            card.add(Box.createVerticalStrut(12));
        }

        // Departemen
        card.add(buildFieldLabel("Departemen"));
        card.add(Box.createVerticalStrut(4));
        String[] depts = {
            "IGD (Instalasi Gawat Darurat)",
            "Rawat Inap",
            "Rawat Jalan / Poliklinik",
            "ICU / ICCU",
            "Bedah & Kamar Operasi",
            "Radiologi & Laboratorium",
            "Farmasi",
            "Administrasi & Keuangan",
            "Teknologi Informasi",
            "Manajemen & Direksi"
        };
        txtKRDept = new JComboBox<>(depts);
        txtKRDept.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtKRDept.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        txtKRDept.setAlignmentX(LEFT_ALIGNMENT);
        card.add(txtKRDept);
        card.add(Box.createVerticalStrut(20));

        // Buttons
        btnSave    = buildButton("💾  Simpan",  CLR_PRIMARY);
        btnUpdate  = buildButton("✏️  Update",  CLR_AMBER);
        btnRefresh = buildButton("🔄  Reset",   CLR_SUCCESS);
        btnDelete  = buildButton("🗑️  Hapus",   CLR_DANGER);

        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        JPanel btnRow1 = new JPanel(new GridLayout(1, 2, 8, 0));
        btnRow1.setOpaque(false);
        btnRow1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btnRow1.add(btnSave);
        btnRow1.add(btnUpdate);

        JPanel btnRow2 = new JPanel(new GridLayout(1, 2, 8, 0));
        btnRow2.setOpaque(false);
        btnRow2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btnRow2.add(btnRefresh);
        btnRow2.add(btnDelete);

        card.add(btnRow1);
        card.add(Box.createVerticalStrut(8));
        card.add(btnRow2);
        card.add(Box.createVerticalGlue());

        // Button actions
        btnSave.addActionListener(e -> {
            Karyawan k = collectForm();
            new KaryawanService().tambahKaryawan(k);
            showData("");
            resetForm();
        });
        btnUpdate.addActionListener(e -> {
            Karyawan k = collectForm();
            new KaryawanService().updateKaryawan(k);
            resetForm();
        });
        btnRefresh.addActionListener(e -> resetForm());
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String uid = tableModel.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Hapus karyawan dengan UID: " + uid + "?", "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    new KaryawanService().hapusKaryawan(uid);
                    showData("");
                    resetForm();
                }
            }
        });

        return card;
    }

    // ─────────────────────────────────────────────────────────
    // TABLE AREA (center panel)
    // ─────────────────────────────────────────────────────────
    private JPanel buildTableArea() {
        JPanel area = new JPanel(new BorderLayout(0, 0));
        area.setBackground(CLR_BG);
        area.setBorder(new EmptyBorder(16, 16, 16, 16));

        // Column headers
        String[] cols = {"UID RFID", "ID Karyawan", "Nama Lengkap", "Departemen"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(36);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(0, 188, 212, 60));
        table.setSelectionForeground(CLR_TEXT_MAIN);
        table.setBackground(CLR_CARD);
        table.setFillsViewportHeight(true);

        // Header style
        JTableHeader header = table.getTableHeader();
        header.setBackground(CLR_TABLE_HEAD);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 40));
        header.setBorder(BorderFactory.createEmptyBorder());

        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
                if (!sel) c.setBackground(row % 2 == 0 ? CLR_CARD : CLR_TABLE_ALT);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                return c;
            }
        });

        // Row click — populate form
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtUID.setText(tableModel.getValueAt(row, 0).toString());
                    txtKRID.setText(tableModel.getValueAt(row, 1).toString());
                    txtKRName.setText(tableModel.getValueAt(row, 2).toString());
                    String dept = tableModel.getValueAt(row, 3).toString();
                    for (int i = 0; i < txtKRDept.getItemCount(); i++) {
                        if (txtKRDept.getItemAt(i).equals(dept)) { txtKRDept.setSelectedIndex(i); break; }
                    }
                    btnUpdate.setEnabled(true);
                    btnDelete.setEnabled(true);
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(CLR_BORDER, 1, true));
        scroll.getViewport().setBackground(CLR_CARD);

        // Stats bar below table
        JPanel statsBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 6));
        statsBar.setBackground(CLR_CARD);
        statsBar.setBorder(new MatteBorder(1, 0, 0, 0, CLR_BORDER));
        statsLabel = new JLabel("Total: 0 karyawan terdaftar");
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statsLabel.setForeground(CLR_TEXT_DIM);
        statsBar.add(new JLabel("i "));
        statsBar.add(statsLabel);

        area.add(scroll, BorderLayout.CENTER);
        area.add(statsBar, BorderLayout.SOUTH);

        // Keep jPanel4 reference for KaryawanService.tampilKaryawan compat
        jPanel4 = new JPanel();
        jPanel4.setVisible(false);

        return area;
    }

    // ─────────────────────────────────────────────────────────
    // HELPERS
    // ─────────────────────────────────────────────────────────
    private JLabel buildFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(CLR_TEXT_DIM);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField buildTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CLR_BORDER, 1, true),
            new EmptyBorder(5, 10, 5, 10)
        ));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        tf.setAlignmentX(LEFT_ALIGNMENT);
        // Highlight on focus
        tf.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(CLR_ACCENT, 1, true), new EmptyBorder(5, 10, 5, 10)));
            }
            @Override public void focusLost(FocusEvent e) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(CLR_BORDER, 1, true), new EmptyBorder(5, 10, 5, 10)));
            }
        });
        return tf;
    }

    private JButton buildButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setBackground(bg.darker());
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

    private Karyawan collectForm() {
        Karyawan k = new Karyawan();
        k.setUidRfid(txtUID.getText().trim());
        k.setIdKaryawan(txtKRID.getText().trim());
        k.setNamaLengkap(txtKRName.getText().trim());
        k.setDepartemen(txtKRDept.getSelectedItem().toString());
        return k;
    }

    private void resetForm() {
        txtUID.setText("");
        txtKRID.setText("");
        txtKRName.setText("");
        txtKRDept.setSelectedIndex(0);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        table.clearSelection();
        txtUID.requestFocus();
        showData("");
    }

    public static void showData(String key) {
        // Re-implement direct table loading instead of using jPanel4 cards
        // Retrieve data via KaryawanService and populate JTable
        try {
            KaryawanService svc = new KaryawanService();
            java.util.List<Karyawan> list = svc.cariKaryawan(key);
            // We need to access the tableModel — use a static reference trick
            if (_instance != null && _instance.tableModel != null) {
                _instance.tableModel.setRowCount(0);
                for (Karyawan k : list) {
                    _instance.tableModel.addRow(new Object[]{
                        k.getUidRfid(), k.getIdKaryawan(), k.getNamaLengkap(), k.getDepartemen()
                    });
                }
                if (_instance.statsLabel != null) {
                    _instance.statsLabel.setText("Total: " + list.size() + " karyawan terdaftar");
                }
            }
        } catch (Exception ex) {
            System.err.println("KaryawanPanel.showData error: " + ex.getMessage());
        }
    }

    // Singleton instance ref so showData (static) can reach tableModel
    private static KaryawanPanel _instance;

    { _instance = this; }
}
