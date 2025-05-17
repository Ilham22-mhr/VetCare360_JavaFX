package vetcare360.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import vetcare360.models.Owner;

public class AddOwnerController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField telephoneField;

    private BorderPane mainContainer;

    public void setMainContainer(BorderPane mainContainer) {
        this.mainContainer = mainContainer;
    }

    @FXML
    private void AddOwner() {
        if (validateFields()) {
            Owner newOwner = new Owner();
            newOwner.setId(String.valueOf(System.currentTimeMillis()));
            newOwner.setFirstName(firstNameField.getText());
            newOwner.setLastName(lastNameField.getText());
            newOwner.setAddress(addressField.getText());
            newOwner.setCity(cityField.getText());
            newOwner.setTelephone(telephoneField.getText());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OwnerList.fxml"));
                Parent ownerListView = loader.load();

                OwnerListController controller = loader.getController();
                controller.addNewOwner(newOwner);

                mainContainer.setCenter(ownerListView);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Unable to load the owners list.");
            }
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