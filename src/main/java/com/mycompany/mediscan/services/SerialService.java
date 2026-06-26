/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mediscan.services;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.mycompany.mediscan.serial.SerialDataHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author mnish
 */
public class SerialService {
    private static SerialService instance;
    private SerialPort activePort;
    private final List<SerialDataHandler<String>> handlers = new ArrayList<>();

    // Private constructor untuk Singleton
    private SerialService() {}

    public static synchronized SerialService getInstance() {
        if (instance == null) {
            instance = new SerialService();
        }
        return instance;
    }

    /**
     * Menambahkan handler baru ke dalam daftar observer.
     * @param handler
     */
    public void addHandler(SerialDataHandler<String> handler) {
        if (!handlers.contains(handler)) {
            handlers.add(handler);
        }
    }

    /**
     * Menghapus handler (penting untuk mencegah memory leak saat frame ditutup).
     * @param handler
     */
    public void removeHandler(SerialDataHandler<String> handler) {
        handlers.remove(handler);
    }

    /**
     * Membuka koneksi ke port serial.
     * @param portName
     * @param baudRate
     * @return 
     */
    public boolean connect(String portName, int baudRate) {
        // Jika port sudah terbuka, tidak perlu buka lagi
        if (activePort != null && activePort.isOpen()) {
            return true;
        }

        activePort = SerialPort.getCommPort(portName);
        activePort.setBaudRate(baudRate);
        
        // Setting Timeout agar pembacaan tidak memblokir thread utama
        // TIMEOUT_READ_SEMI_BLOCKING cocok untuk scanner.nextLine()
        activePort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1000, 0);

        if (activePort.openPort()) {
            System.out.println("INFO: Port " + portName + " terbuka.");
            setupListener();
            return true;
        } else {
            System.err.println("ERROR: Gagal membuka port " + portName);
            return false;
        }
    }

    /**
     * Mengatur listener event untuk mendeteksi data masuk secara otomatis.
     */
    private void setupListener() {
        activePort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) return;

                // Menggunakan Scanner untuk menangkap satu baris utuh (ID RFID)
                try (Scanner scanner = new Scanner(activePort.getInputStream())) {
                    if (scanner.hasNextLine()) {
                        String data = scanner.nextLine().trim();
                        if (!data.isEmpty()) {
                            broadcast(data);
                        }
                    }
                } catch (Exception e) {
                    //System.err.println("Error saat membaca data: " + e.getMessage());
                }
            }
        });
    }

    /**
     * Mengirimkan data ke semua handler yang terdaftar.
     */
    private void broadcast(String data) {
        for (SerialDataHandler<String> handler : handlers) {
            handler.onDataReceived(data);
        }
    }

    public void disconnect() {
        if (activePort != null && activePort.isOpen()) {
            activePort.removeDataListener();
            activePort.closePort();
            System.out.println("INFO: Port ditutup.");
        }
    }

    public boolean isConnected() {
        return activePort != null && activePort.isOpen();
    }
    
}
