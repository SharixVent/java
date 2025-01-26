package Java_Projekt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final List<Cashier> cashiers = new ArrayList<>(); // Lista kasjerów
    private final List<Pet> pets = new ArrayList<>(); // Lista zwierząt
    private static final String DATA_FILE = "petstore_data.txt"; // Plik do przechowywania danych

    public Database() {
        loadData(); // Wczytaj dane z pliku przy tworzeniu obiektu bazy danych
    }

    // Dodaj kasjera
    public void addCashier(String username, String password) {
        // Sprawdź, czy kasjer już istnieje
        if (!cashierExists(username)) {
            cashiers.add(new Cashier(username, password)); // Dodaj kasjera
            saveData(); // Zapisz dane do pliku
        }
    }

    // Sprawdź, czy kasjer już istnieje
    private boolean cashierExists(String username) {
        for (Cashier cashier : cashiers) {
            if (cashier.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Sprawdź dane logowania kasjera
    public boolean checkCashierLogin(String username, String password) {
        for (Cashier cashier : cashiers) {
            if (cashier.getUsername().equals(username) && cashier.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Dodaj zwierzę
    public void addPet(Pet pet) {
        // Sprawdź, czy zwierzę już istnieje
        if (!petExists(pet.getName())) {
            pets.add(pet); // Dodaj zwierzę
            saveData(); // Zapisz dane do pliku
        }
    }

    // Sprawdź, czy zwierzę już istnieje
    private boolean petExists(String petName) {
        for (Pet pet : pets) {
            if (pet.getName().equals(petName)) {
                return true;
            }
        }
        return false;
    }

    // Usuń zwierzę
    public void removePet(Pet pet) {
        pets.remove(pet); // Usuń zwierzę
        saveData(); // Zapisz dane do pliku
    }

    // Pobierz listę zwierząt
    public List<Pet> getPets() {
        return pets;
    }

    // Edytuj zwierzę
    public void editPet(Pet oldPet, Pet newPet) {
        int index = pets.indexOf(oldPet); // Znajdź indeks starego zwierzęcia
        if (index != -1) {
            pets.set(index, newPet); // Zastąp stare zwierzę nowym
            saveData(); // Zapisz dane do pliku
        }
    }

    // Wczytaj dane z pliku
    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return; // Jeśli plik nie istnieje, zakończ metodę
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("CASHIER:")) {
                    // Wczytaj dane kasjera
                    String[] parts = line.substring(8).split(",");
                    if (!cashierExists(parts[0])) { // Unikaj duplikatów
                        cashiers.add(new Cashier(parts[0], parts[1]));
                    }
                } else if (line.startsWith("PET:")) {
                    // Wczytaj dane zwierzęcia
                    String[] parts = line.substring(4).split(",");
                    if (!petExists(parts[0])) { // Unikaj duplikatów
                        pets.add(new Pet(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4], parts[5]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Obsłuż błąd odczytu pliku
        }
    }

    // Zapisz dane do pliku (nadpisz plik)
    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            // Zapisz dane kasjerów
            for (Cashier cashier : cashiers) {
                writer.write("CASHIER:" + cashier.getUsername() + "," + cashier.getPassword());
                writer.newLine();
            }

            // Zapisz dane zwierząt
            for (Pet pet : pets) {
                writer.write("PET:" + pet.getName() + "," + pet.getRace() + "," + pet.getColor() + "," +
                        pet.getAge() + "," + pet.getDescription() + "," + pet.getPhoto());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Obsłuż błąd zapisu do pliku
        }
    }
}