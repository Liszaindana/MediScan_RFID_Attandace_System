package com.mycompany.mediscan.gui.panel;

import com.mycompany.mediscan.objects.Karyawan;
import com.mycompany.mediscan.objects.LogAbsensi;
import com.mycompany.mediscan.services.I18nService;
import com.mycompany.mediscan.services.KaryawanService;
import com.mycompany.mediscan.services.LogAbsensiService;
import com.mycompany.mediscan.util.EncryptionUtils;
import java.io.File;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class Reports extends javax.swing.JPanel {
    private final I18nService.I18nChangeListener languageListener = this::applyLanguage;

    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;

    public Reports() {
        initComponents();
        
        applyLanguage();
        I18nService.registerListener(languageListener);
        
        // Set layout utama
        this.setLayout(new java.awt.BorderLayout());

        // Buat panel atas untuk combobox dan button - center
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));
        jPanel2.setBackground(new java.awt.Color(245, 247, 250));

        // Ganti layout jPanel1
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.setBackground(new java.awt.Color(245, 247, 250));
        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);
        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        this.add(jPanel1, java.awt.BorderLayout.CENTER);

        showReportData();
    }
    
    public void showReportData() {
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new Object[][]{}, 
            new String[]{
                I18nService.get("report.id"),
                I18nService.get("report.employee"),
                I18nService.get("report.department"),
                I18nService.get("report.status")
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        jTable1.setModel(model);

        if (jComboBox1.getSelectedItem() == null) return;
        String bulanDipilih = jComboBox1.getSelectedItem().toString().trim();
        
        LogAbsensiService logService = new LogAbsensiService();
        KaryawanService karyawanService = new KaryawanService();

        List<LogAbsensi> listLog = logService.getAllLog();
        
        if (listLog == null || listLog.isEmpty()) return;

        for (com.mycompany.mediscan.objects.LogAbsensi log : listLog) {
            
            // penyaringan berdasarkan teks combobox
            if (!bulanDipilih.equalsIgnoreCase(I18nService.get("report.allMonth"))) {
                int bulanLog = 0;
                
                if (log.getWaktuTap() != null) {
                    // Jika sukses dipetakan sebagai LocalDateTime Java
                    bulanLog = log.getWaktuTap().getMonthValue();
                } else {
                    try {
                        java.lang.reflect.Field fieldWaktu = log.getClass().getDeclaredField("waktuTap");
                        fieldWaktu.setAccessible(true);
                        Object objWaktu = fieldWaktu.get(log);
                        
                        if (objWaktu != null) {
                            String teksWaktuMentah = objWaktu.toString().trim(); 
                            if (teksWaktuMentah.length() >= 7) {
                                String teksBulan = teksWaktuMentah.substring(5, 7); 
                                bulanLog = Integer.parseInt(teksBulan); 
                            }
                        }
                    } catch (Exception e) {
                        bulanLog = 0; 
                    }
                }
                
                String namaBulanLog = switch (bulanLog) {
                    case 1 -> I18nService.get("month.january");
                    case 2 -> I18nService.get("month.february");
                    case 3 -> I18nService.get("month.march");
                    case 4 -> I18nService.get("month.april");
                    case 5 -> I18nService.get("month.may");
                    case 6 -> I18nService.get("month.june");
                    case 7 -> I18nService.get("month.july");
                    case 8 -> I18nService.get("month.august");
                    case 9 -> I18nService.get("month.september");
                    case 10 -> I18nService.get("month.october");
                    case 11 -> I18nService.get("month.november");
                    case 12 -> I18nService.get("month.december");
                    default -> "";
                };
                
                if (!namaBulanLog.equalsIgnoreCase(bulanDipilih)) {
                    continue; 
                }
            }

            String idTampil = I18nService.get("report.notRegistered");
            String namaTampil = "-";
            String deptTampil = "-";

            com.mycompany.mediscan.objects.Karyawan karyawan = karyawanService.findByUid(log.getUidRfid());

            if (karyawan != null) {
                idTampil = com.mycompany.mediscan.util.EncryptionUtils.decrypt(karyawan.getIdKaryawan());
                namaTampil = karyawan.getNamaLengkap();
                deptTampil = I18nService.get(karyawan.getDepartemen());   
            } else {
                if (log.getUidRfid() != null) {
                    String shortUid = log.getUidRfid().length() > 8 ? log.getUidRfid().substring(0, 8) : log.getUidRfid();
                    idTampil = I18nService.get("log.uid") + ": " + shortUid;
                }
            }

            String statusTampil = "IN".equalsIgnoreCase(log.getStatus())
                    ? I18nService.get("ui.status.in")
                    : I18nService.get("ui.status.out");

            model.addRow(new Object[]{
                idTampil,
                namaTampil,
                deptTampil,
                statusTampil   
            });
        }
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(245, 247, 250));

        jScrollPane1.setBackground(new java.awt.Color(245, 247, 250));
        jScrollPane1.setOpaque(false);

        jTable1.setBackground(new java.awt.Color(255, 255, 255));
        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTable1.setRowHeight(24);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Id", "Nama Karyawan", "Departemen", "Status Absensi"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox1);

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
    }

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        showReportData();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (jComboBox1.getSelectedItem() == null) return;
        String bulanIni = jComboBox1.getSelectedItem().toString().trim();
        
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle(I18nService.get("report.save"));
        fileChooser.setSelectedFile(new File(I18nService.get("report.filename") + "_" + bulanIni + ".csv"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            
            try (java.io.FileWriter fw = new java.io.FileWriter(fileToSave)) {
                javax.swing.table.TableModel tableModel = jTable1.getModel();
                
                fw.write(
                    I18nService.get("report.id") + ";" +
                    I18nService.get("report.employee") + ";" +
                    I18nService.get("report.department") + ";" +
                    I18nService.get("report.status") + "\n"
                );
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String id = tableModel.getValueAt(i, 0).toString();
                    String nama = tableModel.getValueAt(i, 1).toString();
                    String dept = tableModel.getValueAt(i, 2).toString();
                    String status = tableModel.getValueAt(i, 3).toString();
                    
                    fw.write(id + ";" + nama + ";" + dept + ";" + status + "\n");
                }
                
                javax.swing.JOptionPane.showMessageDialog(this, I18nService.get("report.exportSuccess")
                    + " " + bulanIni
                    + "\n"
                    + fileToSave.getAbsolutePath());

            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, I18nService.get("report.exportFailed") + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void applyLanguage() {
        jButton1.setText(I18nService.get("report.export"));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            I18nService.get("report.allMonth"),
            I18nService.get("month.january"),
            I18nService.get("month.february"),
            I18nService.get("month.march"),
            I18nService.get("month.april"),
            I18nService.get("month.may"),
            I18nService.get("month.june"),
            I18nService.get("month.july"),
            I18nService.get("month.august"),
            I18nService.get("month.september"),
            I18nService.get("month.october"),
            I18nService.get("month.november"),
            I18nService.get("month.december")
        }));

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        model.setColumnIdentifiers(new Object[]{
            I18nService.get("report.id"),
            I18nService.get("report.employee"),
            I18nService.get("report.department"),
            I18nService.get("report.status")
        });

        showReportData();
    }

    @Override
    public void removeNotify() {
        I18nService.unregisterListener(languageListener);
        super.removeNotify();
    }
}
