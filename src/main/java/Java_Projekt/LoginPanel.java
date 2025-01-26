package Java_Projekt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JFrame {
    private JTextField usernameField; // Pole do wprowadzenia nazwy użytkownika
    private JPasswordField passwordField; // Pole do wprowadzenia hasła
    private Database database; // Obiekt bazy danych

    public LoginPanel(Database database) {
        this.database = database;

        setTitle("Login Panel"); // Tytuł okna
        setSize(300, 200); // Rozmiar okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Zamknij aplikację po zamknięciu okna

        JPanel panel = new JPanel(); // Panel do przechowywania komponentów
        add(panel);
        placeComponents(panel); // Umieść komponenty na panelu

        setVisible(true); // Ustaw widoczność okna
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null); // Ustaw brak układu (absolute positioning)

        // Etykieta i pole do wprowadzenia nazwy użytkownika
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        // Etykieta i pole do wprowadzenia hasła
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        // Przycisk logowania
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        // Obsługa zdarzenia kliknięcia przycisku logowania
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText(); // Pobierz nazwę użytkownika
                String password = new String(passwordField.getPassword()); // Pobierz hasło

                // Sprawdź, czy dane logowania są poprawne
                if (database.checkCashierLogin(username, password)) {
                    new AdminPanel(database); // Otwórz panel administratora
                    dispose(); // Zamknij bieżące okno
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Invalid username or password!"); // Błędne dane logowania
                }
            }
        });
    }
}