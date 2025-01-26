package Java_Projekt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MediaTracker;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GuestPanel extends JFrame {
    private Database database; // Obiekt bazy danych

    public GuestPanel(Database database) {
        this.database = database;

        setTitle("Guest Panel"); // Tytuł okna
        setSize(800, 600); // Rozmiar okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Zamknij aplikację po zamknięciu okna

        JPanel mainPanel = new JPanel(); // Główny panel
        mainPanel.setLayout(new BorderLayout()); // Ustaw układ BorderLayout
        add(mainPanel);

        // Panel dla przycisków zwierząt
        JPanel petPanel = new JPanel();
        petPanel.setLayout(new BoxLayout(petPanel, BoxLayout.Y_AXIS)); // Ustaw układ pionowy
        petPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Dodaj marginesy
        JScrollPane scrollPane = new JScrollPane(petPanel); // Dodaj możliwość przewijania
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Przycisk "Return to Home"
        JButton returnButton = new JButton("Return to Home");
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Wyśrodkuj przycisk
        returnButton.setPreferredSize(new Dimension(200, 50)); // Ustaw rozmiar przycisku
        mainPanel.add(returnButton, BorderLayout.SOUTH);

        // Obsługa zdarzenia kliknięcia przycisku "Return to Home"
        returnButton.addActionListener(e -> {
            dispose(); // Zamknij bieżące okno
            new PetStoreApp(database); // Otwórz główne okno aplikacji
        });

        add(mainPanel);
        displayPets(petPanel); // Wyświetl listę zwierząt

        setVisible(true); // Ustaw widoczność okna
    }

    private void displayPets(JPanel panel) {
        List<Pet> pets = database.getPets(); // Pobierz listę zwierząt z bazy danych
        for (Pet pet : pets) {
            JButton petButton = new JButton(pet.getName()); // Przycisk z nazwą zwierzęcia
            petButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Wyśrodkuj przycisk
            petButton.setPreferredSize(new Dimension(200, 50)); // Ustaw rozmiar przycisku
            petButton.setMaximumSize(new Dimension(200, 50)); // Zapobiegaj rozciąganiu przycisku
            panel.add(petButton);

            // Dodaj odstęp między przyciskami
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Obsługa zdarzenia kliknięcia przycisku zwierzęcia
            petButton.addActionListener(e -> {
                // Utwórz okno dialogowe z informacjami o zwierzęciu
                JDialog dialog = new JDialog(this, "Pet Details", true);
                dialog.setLayout(new BorderLayout());

                // Wyświetl informacje o zwierzęciu
                JTextArea details = new JTextArea(
                    "Name: " + pet.getName() + "\n" +
                    "Race: " + pet.getRace() + "\n" +
                    "Color: " + pet.getColor() + "\n" +
                    "Age: " + pet.getAge() + "\n" +
                    "Description: " + pet.getDescription()
                );
                details.setEditable(false); // Uniemożliwia edycję tekstu
                dialog.add(details, BorderLayout.CENTER);

                // Wyświetl zdjęcie zwierzęcia (jeśli istnieje)
                String photoPath = pet.getPhoto();
                if (photoPath != null && !photoPath.isEmpty()) {
                    ImageIcon imageIcon = new ImageIcon(photoPath);
                    if (imageIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                        // Zmniejsz zdjęcie do rozmiaru 200x200 pikseli
                        Image image = imageIcon.getImage();
                        Image resizedImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        ImageIcon resizedIcon = new ImageIcon(resizedImage);

                        JLabel photoLabel = new JLabel(resizedIcon);
                        dialog.add(photoLabel, BorderLayout.EAST);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to load photo: " + photoPath); // Błąd ładowania zdjęcia
                    }
                }

                dialog.pack(); // Dopasuj rozmiar okna do zawartości
                dialog.setLocationRelativeTo(this); // Wyśrodkuj okno dialogowe
                dialog.setVisible(true); // Ustaw widoczność okna dialogowego
            });
        }
    }
}