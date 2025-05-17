package vetcare360.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import vetcare360.models.Owner;
import vetcare360.models.Pet;

import java.io.IOException;
import java.time.LocalDate;

public class AddPetController {

    @FXML private TextField ownerNameField;
    @FXML private TextField petNameField;
    @FXML private DatePicker birthDateField;
    @FXML private ComboBox<String> petTypeField;

    private Owner currentOwner;
    private BorderPane mainContainer;

    public void setMainContainer(BorderPane container) {
        this.mainContainer = container;
    }

    public void setOwner(Owner owner) {
        this.currentOwner = owner;
        if (owner != null) {
            ownerNameField.setText(owner.getName());
        }
    }

    public void initialize() {
        petTypeField.getItems().addAll("Dog", "Cat", "Bird", "Rabbit", "Hamster", "Other");
    }

    @FXML
    private void addPet() {
        if (currentOwner == null || !validateFields()) return;

        String name = petNameField.getText().trim();
        LocalDate birthDate = birthDateField.getValue();
        String type = petTypeField.getValue();

        Pet newPet = new Pet(
                String.valueOf(System.currentTimeMillis()),
                name,
                birthDate,
                type
        );

        currentOwner.addPet(newPet);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OwnerDetails.fxml"));
            Parent view = loader.load();

            OwnerDetailsController controller = loader.getController();
            controller.setOwner(currentOwner);

            mainContainer.setCenter(view);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load owner details page.");
        }
    }

    private boolean validateFields() {
        StringBuilder errorMessage = new StringBuilder();

        if (petNameField.getText() == null || petNameField.getText().trim().isEmpty()) {
            errorMessage.append("Pet name is required.\n");
        }

        if (birthDateField.getValue() == null) {
            errorMessage.append("Birth date is required.\n");
        }

        if (petTypeField.getValue() == null || petTypeField.getValue().trim().isEmpty()) {
            errorMessage.append("Pet type is required.\n");
        }

        if (errorMessage.length() > 0) {
            showAlert("Validation Failed", errorMessage.toString());
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}