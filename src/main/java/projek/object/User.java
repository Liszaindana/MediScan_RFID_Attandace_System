package projek.object;

import java.time.LocalDateTime;

public class User {

    private String fullname;
    private String username;
    private String password; // Tersimpan dalam format Hash SHA-256
    private LocalDateTime lastLogin;

    public User() {
    }

    public User(String fullname, String username, String password, LocalDateTime lastLogin) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.lastLogin = lastLogin;
    }

    // Getter dan Setter untuk akses data secara enkapsulasi [1]
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
