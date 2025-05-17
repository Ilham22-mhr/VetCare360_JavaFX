package vetcare360.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import vetcare360.models.Owner;
import vetcare360.models.Pet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EditPetController {

    @FXML private TextField ownerNameField;
    @FXML private TextField nameField;
    @FXML private DatePicker birthDateField;
    @FXML private ComboBox<String> typeField;

    private Pet pet;
    private BorderPane mainContainer;

    public void initialize() {
        // Populate pet types
        typeField.getItems().addAll("Dog", "Cat", "Bird", "Rabbit", "Hamster", "Other");
    }

    public void setMainContainer(BorderPane container) {
        this.mainContainer = container;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
        if (pet != null) {
            nameField.setText(pet.getName());
            birthDateField.setValue(pet.getBirthDate());

            // Set type in ComboBox
            String petType = pet.getType();
            if (!typeField.getItems().contains(petType)) {
                typeField.getItems().add(petType);
            }
            typeField.setValue(petType);

            Owner owner = pet.getOwner();
            System.out.println("DEBUG - Owner: " + (owner != null ? owner.getName() : "null"));

            if (owner != null) {
                ownerNameField.setText(owner.getName());
            } else {
                ownerNameField.setText("No owner assigned");

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OwnerDetails.fxml"));
                    Parent parent = loader.load();
                    OwnerDetailsController controller = loader.getController();
                    Owner currentOwner = controller.getSelectedOwner();

                    if (currentOwner != null) {
                        pet.setOwner(currentOwner);
                        ownerNameField.setText(currentOwner.getName());
                        System.out.println("DEBUG - Owner retrieved and assigned: " + currentOwner.getName());
                    }
                } catch (Exception e) {
                    System.out.println("DEBUG - Unable to retrieve owner: " + e.getMessage());
                }
            }
        } else {
            showAlert("Error", "No pet selected.");
        }
    }

    @FXML
    private void updatePet() {
        if (!validateFields()) return;

        try {
            pet.setName(nameField.getText().trim());
            LocalDate birthDate = birthDateField.getValue();
            pet.setBirthDate(birthDate);
            pet.setType(typeField.getValue());

            Owner owner = pet.getOwner();
            if (owner == null) {
                System.out.println("DEBUG - Pet has no owner during update");

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OwnerDetails.fxml"));
                    Parent parent = loader.load();
                    OwnerDetailsController controller = loader.getController();
                    Owner currentOwner = controller.getSelectedOwner();

                    if (currentOwner != null) {
                        pet.setOwner(currentOwner);
                        currentOwner.addPet(pet);
                        System.out.println("DEBUG - Owner assigned to pet: " + currentOwner.getName());
                    }
                } catch (Exception e) {
                    System.out.println("DEBUG - Unable to retrieve owner: " + e.getMessage());
                }
            }

            if (pet.getOwner() != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OwnerDetails.fxml"));
                Parent detailsView = loader.load();

                OwnerDetailsController controller = loader.getController();
                controller.setOwner(pet.getOwner());

                if (mainContainer != null) {
                    mainContainer.setCenter(detailsView);
                }
            } else {
                showAlert("Warning", "Update successful, but pet has no associated owner.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load owner details.");
        }
    }

    private boolean validateFields() {
        StringBuilder error = new StringBuilder();

        if (nameField.getText().trim().isEmpty()) {
            error.append("Name is required.\n");
        }

        if (birthDateField.getValue() == null) {
            error.append("Birth date is required.\n");
        }

        if (typeField.getValue() == null || typeField.getValue().trim().isEmpty()) {
            error.append("Type is required.\n");
        }

        if (error.length() > 0) {
            showAlert("Validation Failed", error.toString());
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message) {
        Alert.AlertType type = title.equals("Information") || title.equals("Warning")
                ? Alert.AlertType.INFORMATION
                : Alert.AlertType.ERROR;

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}