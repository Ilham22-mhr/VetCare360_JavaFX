package vetcare360.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Owner {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;

    private ObservableList<Pet> pets;

    public Owner() {
        this.pets = FXCollections.observableArrayList();
    }

    public Owner(String id, String firstName, String lastName, String address, String city, String telephone) {
        this();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
    }

    public Owner(String id, String firstName, String lastName, String address, String city, String telephone, List<Pet> pets) {
        this(id, firstName, lastName, address, city, telephone);
        if (pets != null) {
            this.pets.addAll(pets);
        }
    }

    public String getName() {
        StringBuilder name = new StringBuilder();
        if (firstName != null && !firstName.trim().isEmpty()) {
            name.append(firstName.trim());
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            if (name.length() > 0) name.append(" ");
            name.append(lastName.trim());
        }
        return name.toString();
    }

    public String getPetsAsString() {
        if (pets.isEmpty()) {
            return "Aucun animal";
        }
        return pets.stream()
                .map(Pet::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }

    public void addPet(Pet pet) {
        if (pet != null && !pets.contains(pet)) {
            pets.add(pet);
        }
    }

    public ObservableList<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets.clear();
        if (pets != null) {
            this.pets.addAll(pets);
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}