package Java_Projekt;

public class Cashier {
    private String username; // Nazwa użytkownika kasjera
    private String password; // Hasło kasjera

    // Konstruktor klasy Cashier
    public Cashier(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Gettery
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}