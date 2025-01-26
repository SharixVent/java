package Java_Projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class AdminPanel extends JFrame {
    private Database database; // Obiekt bazy danych
    private JPanel petListPanel; // Panel do wyświetlania listy zwierząt

    public AdminPanel(Database database) {
        this.database = database;

        setTitle("Admin Panel"); // Tytuł okna
        setSize(600, 400); // Rozmiar okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Zamknij aplikację po zamknięciu okna

        JPanel panel = new JPanel(); // Główny panel
        panel.setLayout(new BorderLayout()); // Ustaw układ BorderLayout
        add(panel);

        // Panel dla przycisków
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); // Ustaw układ 3 wiersze, 1 kolumna, z odstępami
        panel.add(buttonPanel, BorderLayout.WEST);

        // Przycisk "Add Pet"
        JButton addPetButton = new JButton("Add Pet");
        addPetButton.setPreferredSize(new Dimension(150, 50)); // Ustaw rozmiar przycisku
        buttonPanel.add(addPetButton);

        // Obsługa zdarzenia kliknięcia przycisku "Add Pet"
        addPetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter pet name:"); // Pobierz nazwę zwierzęcia
                String race = JOptionPane.showInputDialog("Enter pet race:"); // Pobierz rasę zwierzęcia
                String color = JOptionPane.showInputDialog("Enter pet color:"); // Pobierz kolor zwierzęcia
                int age = Integer.parseInt(JOptionPane.showInputDialog("Enter pet age:")); // Pobierz wiek zwierzęcia
                String description = JOptionPane.showInputDialog("Enter pet description:"); // Pobierz opis zwierzęcia

                // Wybierz zdjęcie zwierzęcia
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                String photoPath = "";
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    photoPath = selectedFile.getAbsolutePath(); // Pobierz ścieżkę do zdjęcia
                }

                // Utwórz i dodaj nowe zwierzę
                Pet newPet = new Pet(name, race, color, age, description, photoPath);
                database.addPet(newPet);
                JOptionPane.showMessageDialog(AdminPanel.this, "Pet added successfully!"); // Potwierdzenie dodania zwierzęcia
                refreshPetList(); // Odśwież listę zwierząt
            }
        });

        // Przycisk "Remove Pet"
        JButton removePetButton = new JButton("Remove Pet");
        removePetButton.setPreferredSize(new Dimension(150, 50)); // Ustaw rozmiar przycisku
        buttonPanel.add(removePetButton);

        // Obsługa zdarzenia kliknięcia przycisku "Remove Pet"
        removePetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter pet name to remove:"); // Pobierz nazwę zwierzęcia do usunięcia
                for (Pet pet : database.getPets()) {
                    if (pet.getName().equals(name)) {
                        database.removePet(pet); // Usuń zwierzę
                        JOptionPane.showMessageDialog(AdminPanel.this, "Pet removed successfully!"); // Potwierdzenie usunięcia
                        refreshPetList(); // Odśwież listę zwierząt
                        return;
                    }
                }
                JOptionPane.showMessageDialog(AdminPanel.this, "Pet not found!"); // Zwierzę nie znalezione
            }
        });

        // Przycisk "Return to Home"
        JButton returnButton = new JButton("Return to Home");
        returnButton.setPreferredSize(new Dimension(150, 50)); // Ustaw rozmiar przycisku
        buttonPanel.add(returnButton);

        // Obsługa zdarzenia kliknięcia przycisku "Return to Home"
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Zamknij bieżące okno
                new PetStoreApp(database); // Otwórz główne okno aplikacji
            }
        });

        // Panel do wyświetlania listy zwierząt
        petListPanel = new JPanel();
        petListPanel.setLayout(new BoxLayout(petListPanel, BoxLayout.Y_AXIS)); // Ustaw układ pionowy
        petListPanel.setBorder(BorderFactory.createTitledBorder("Current Pets")); // Dodaj ramkę z tytułem
        JScrollPane scrollPane = new JScrollPane(petListPanel); // Dodaj możliwość przewijania
        panel.add(scrollPane, BorderLayout.CENTER);

        // Wyświetl listę zwierząt
        refreshPetList(); // Odśwież listę zwierząt

        setVisible(true); // Ustaw widoczność okna
    }

    // Odśwież listę zwierząt
    private void refreshPetList() {
        petListPanel.removeAll(); // Wyczyść bieżącą listę
        List<Pet> pets = database.getPets(); // Pobierz listę zwierząt z bazy danych
        for (Pet pet : pets) {
            JLabel petLabel = new JLabel(pet.getName()); // Etykieta z nazwą zwierzęcia
            petLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Ustaw większą czcionkę
            petLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Dodaj marginesy
            petListPanel.add(petLabel); // Dodaj etykietę do panelu
        }
        petListPanel.revalidate(); // Odśwież panel
        petListPanel.repaint(); // Przerysuj panel
    }
}