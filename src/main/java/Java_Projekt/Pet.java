package Java_Projekt;

public class Pet {
    private String name; // Nazwa zwierzaka
    private String race; // Rasa zwierzaka
    private String color; // Kolor zwierzaka
    private int age; // Wiek zwierzaka
    private String description; // Opis zwierzaka
    private String photo; // Ścieżka do zdjęcia zwierzaka

    // Konstruktor klasy Pet
    public Pet(String name, String race, String color, int age, String description, String photo) {
        this.name = name;
        this.race = race;
        this.color = color;
        this.age = age;
        this.description = description;
        this.photo = photo;
    }

    // Gettery i Settery
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    // Metoda toString, która zwraca nazwę i rasę zwierzaka
    @Override
    public String toString() {
        return name + " (" + race + ")";
    }
}