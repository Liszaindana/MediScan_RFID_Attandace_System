package com.mycompany.mediscan.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javax.swing.UIManager;

public class I18nService {
    private static ResourceBundle bundle;
    private static Locale currentLocale;
    
    private static final Preferences PREF =
        Preferences.userRoot().node("MediScan");
    
    public interface I18nChangeListener {
        void onLanguageChanged();
    }
    
    private static final List<I18nChangeListener> listeners = new ArrayList<>();
    
    static {
        String language = PREF.get("language", "id"); // default Indonesia
        currentLocale = new Locale(language);
        loadBundle();
        updateOptionPaneLanguage();
    }
    
    private static void loadBundle() {
        ResourceBundle.clearCache();
        try {
            // Coba dengan locale persis
            bundle = ResourceBundle.getBundle("i18n.messages", currentLocale);
            System.out.println("Bundle loaded: locale " + currentLocale);
        } catch (MissingResourceException e1) {
            try {
                // Coba hanya language-nya saja (tanpa country)
                Locale langOnly = new Locale(currentLocale.getLanguage());
                bundle = ResourceBundle.getBundle("i18n.messages", langOnly);
                System.out.println("Bundle loaded dengan language only: " + langOnly);
            } catch (MissingResourceException e2) {
                try {
                    // Fallback ke messages.properties
                    bundle = ResourceBundle.getBundle("i18n.messages", Locale.ROOT);
                    System.out.println("Bundle fallback ke ROOT");
                } catch (MissingResourceException e3) {
                    System.err.println("WARNING: i18n bundle tidak ditemukan! " + e3.getMessage());
                    bundle = null;
                }
            }
        }
    }
    
    public static void setLocale(Locale locale) {
        currentLocale = locale;
        // Simpan bahasa yang dipilih
        PREF.put("language", locale.getLanguage());
        loadBundle();
        updateOptionPaneLanguage();
        notifyListeners();
    }
    
    public static String get(String key) {
        if (bundle == null) return "!" + key + "!";
        try {
            return bundle.getString(key);
        } catch (MissingResourceException | NullPointerException e) {
            return "!" + key + "!";
        }
    }
    
    public static Locale getCurrentLocale() {
        return currentLocale != null ? currentLocale : new Locale("id");
    }
    
    public static synchronized void registerListener(I18nChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    public static synchronized void unregisterListener(I18nChangeListener listener) {
        listeners.remove(listener);
    }
    
    private static void notifyListeners() {
        for (I18nChangeListener listener : new ArrayList<>(listeners)) {
            if (listener != null) {
                listener.onLanguageChanged();
            }
        }
    }
    
    private static void updateOptionPaneLanguage() {
        String lang = currentLocale.getLanguage();
        switch (lang) {
            case "id":
                UIManager.put("OptionPane.yesButtonText", "Ya");
                UIManager.put("OptionPane.noButtonText", "Tidak");
                UIManager.put("OptionPane.cancelButtonText", "Batal");
                UIManager.put("OptionPane.okButtonText", "OK");
                break;
            case "en":
                UIManager.put("OptionPane.yesButtonText", "Yes");
                UIManager.put("OptionPane.noButtonText", "No");
                UIManager.put("OptionPane.cancelButtonText", "Cancel");
                UIManager.put("OptionPane.okButtonText", "OK");
                break;
            case "nl":
                UIManager.put("OptionPane.yesButtonText", "Ja");
                UIManager.put("OptionPane.noButtonText", "Nee");
                UIManager.put("OptionPane.cancelButtonText", "Annuleren");
                UIManager.put("OptionPane.okButtonText", "OK");
                break;
        }
    }
}
