package vetcare360.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import vetcare360.models.Owner;
import java.io.IOException;

public class EditOwnerController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField telephoneField;

    private Owner owner;
    private BorderPane mainContainer;

    public void setMainContainer(BorderPane container) {
        this.mainContainer = container;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
        if (owner != null) {
            firstNameField.setText(owner.getFirstName());
            lastNameField.setText(owner.getLastName());
            addressField.setText(owner.getAddress());
            cityField.setText(owner.getCity());
            telephoneField.setText(owner.getTelephone());
        }
    }

    @FXML
    private void updateOwner() {
        if (validateFields()) {
            owner.setFirstName(firstNameField.getText().trim());
            owner.setLastName(lastNameField.getText().trim());
            owner.setAddress(addressField.getText().trim());
            owner.setCity(cityField.getText().trim());
            owner.setTelephone(telephoneField.getText().trim());

            returnToOwnerDetails();
        }
    }

    private void returnToOwnerDetails() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OwnerDetails.fxml"));
            Parent detailsView = loader.load();

            OwnerDetailsController controller = loader.getController();
            controller.setOwner(owner);

            if (mainContainer != null) {
                mainContainer.setCenter(detailsView);
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load owner details.");
        }
    }

    private boolean validateFields() {
        StringBuilder errorMessage = new StringBuilder();

        if (firstNameField.getText() == null || firstNameField.getText().trim().isEmpty()) {
            errorMessage.append("First name is required.\n");
        }

        if (lastNameField.getText() == null || lastNameField.getText().trim().isEmpty()) {
            errorMessage.append("Last name is required.\n");
        }

        if (addressField.getText() == null || addressField.getText().trim().isEmpty()) {
            errorMessage.append("Address is required.\n");
        }

        if (cityField.getText() == null || cityField.getText().trim().isEmpty()) {
            errorMessage.append("City is required.\n");
        }

        if (telephoneField.getText() == null || telephoneField.getText().trim().isEmpty()) {
            errorMessage.append("Phone number is required.\n");
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