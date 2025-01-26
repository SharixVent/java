package Java_Projekt;

import javax.swing.JOptionPane;

public class PetStoreApp {
    public static void main(String[] args) {
        // Tworzenie obiektu bazy danych
        Database database = new Database();

        // Uruchomienie aplikacji
        new PetStoreApp(database);
    }

    public PetStoreApp(Database database) {
        // Zapytanie użytkownika, czy jest administratorem czy gościem
        String[] options = {"Admin", "Guest"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Are you an admin or a guest?", // Pytanie do użytkownika
                "Pet Store Management", // Tytuł okna dialogowego
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Obsługa wyboru użytkownika
        if (choice == 0) { 
            new LoginPanel(database); // Otwórz panel logowania dla administratora
        } else if (choice == 1) { // Jeśli wybrano Guest
            new GuestPanel(database); // Otwórz panel dla gościa
        } else {
            JOptionPane.showMessageDialog(null, "Invalid choice. Exiting..."); // Nieprawidłowy wybór
            System.exit(0); // Zakończ program, jeśli wybór jest nieprawidłowy
        }
    }
}