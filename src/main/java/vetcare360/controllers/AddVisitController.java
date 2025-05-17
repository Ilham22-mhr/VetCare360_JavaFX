package vetcare360.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import vetcare360.models.Pet;
import vetcare360.models.Visit;
import java.time.LocalDate;

public class AddVisitController {
    @FXML private DatePicker birthDateField;
    @FXML private TextField descriptionField;
    @FXML private TextField petNameField;

    private Pet selectedPet;
    private Visit selectedVisit;
    private BorderPane mainContainer;

    public void setMainContainer(BorderPane container) {
        this.mainContainer = container;
    }

    public void setPet(Pet pet) {
        this.selectedPet = pet;
        if (pet != null && petNameField != null) {
            petNameField.setText(pet.getName());
        }
    }

    public void setVisit(Visit visit) {
        this.selectedVisit = visit;
        if (visit != null) {
            birthDateField.setValue(visit.getDate());
            descriptionField.setText(visit.getDescription());
        }
    }

    @FXML
    private void addVisit() {
        if (!validateFields()) return;

        LocalDate visitDate = birthDateField.getValue();
        String description = descriptionField.getText().trim();

        Visit newVisit = new Visit(
                String.valueOf(System.currentTimeMillis()),
                visitDate,
                description,
                selectedPet
        );

        if (selectedPet != null) {
            selectedPet.addVisit(newVisit);
        } else {
            showAlert("Error", "Unable to add visit: no pet selected.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OwnerDetails.fxml"));
            Parent detailsView = loader.load();
            OwnerDetailsController controller = loader.getController();

            if (controller != null && selectedPet.getOwner() != null) {
                controller.setOwner(selectedPet.getOwner());
            } else {
                showAlert("Error", "Unable to retrieve owner details.");
            }

            if (mainContainer != null) {
                mainContainer.setCenter(detailsView);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load owner details.");
        }
    }

    private boolean validateFields() {
        StringBuilder error = new StringBuilder();

        if (birthDateField.getValue() == null) {
            error.append("Date is required.\n");
        }

        if (descriptionField.getText().isEmpty()) {
            error.append("Description is required.\n");
        }

        if (error.length() > 0) {
            showAlert("Validation Failed", error.toString());
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