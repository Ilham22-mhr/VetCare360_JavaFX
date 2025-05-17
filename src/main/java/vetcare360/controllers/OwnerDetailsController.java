package vetcare360.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;

import vetcare360.models.Owner;
import vetcare360.models.Pet;
import vetcare360.models.Visit;

public class OwnerDetailsController {

    @FXML private Label ownerNameLabel;
    @FXML private Label addressLabel;
    @FXML private Label cityLabel;
    @FXML private Label telephoneLabel;

    @FXML private TableView<Pet> petsTable;
    @FXML private TableColumn<Pet, String> petNameColumn;
    @FXML private TableColumn<Pet, String> petBirthDateColumn;
    @FXML private TableColumn<Pet, String> petTypeColumn;

    @FXML private TableView<Visit> visitsTable;
    @FXML private TableColumn<Visit, String> visitPetColumn;
    @FXML private TableColumn<Visit, String> visitDateColumn;
    @FXML private TableColumn<Visit, String> visitDescriptionColumn;

    private Owner selectedOwner;

    public Owner getSelectedOwner() {
        return selectedOwner;
    }

    public void setOwner(Owner owner) {
        this.selectedOwner = owner;

        if (owner != null) {
            System.out.println("DEBUG - Owner set: " + owner.getName() + " with " + owner.getPets().size() + " pets");

            ownerNameLabel.setText(owner.getName());
            addressLabel.setText(owner.getAddress());
            cityLabel.setText(owner.getCity());
            telephoneLabel.setText(owner.getTelephone());

            refreshPetsTable();
            refreshVisitsTable();
            setupTableRowEvents();

            for (Pet pet : owner.getPets()) {
                if (pet.getOwner() == null) {
                    System.out.println("DEBUG - Assigning owner to pet: " + pet.getName());
                    pet.setOwner(owner);
                }
            }
        }
    }

    private void refreshPetsTable() {
        ObservableList<Pet> pets = selectedOwner.getPets();
        petsTable.setItems(pets);

        petNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        petBirthDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthDate().toString()));
        petTypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
    }

    private void refreshVisitsTable() {
        ObservableList<Visit> allVisits = FXCollections.observableArrayList();

        if (!selectedOwner.getPets().isEmpty()) {
            for (Pet pet : selectedOwner.getPets()) {
                if (pet.getVisits() != null && !pet.getVisits().isEmpty()) {
                    allVisits.addAll(pet.getVisits());
                }
            }
        }

        Visit emptyVisit = new Visit("", null, "", null);
        allVisits.add(emptyVisit);

        visitsTable.setItems(allVisits);

        visitPetColumn.setCellValueFactory(cellData -> {
            Visit visit = cellData.getValue();
            return new SimpleStringProperty(
                    visit.getPet() != null ? visit.getPet().getName() : ""
            );
        });

        visitDateColumn.setCellValueFactory(cellData -> {
            Visit visit = cellData.getValue();
            return new SimpleStringProperty(
                    visit.getDate() != null ? visit.getDate().toString() : ""
            );
        });

        visitDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    }

    private void setupTableRowEvents() {
        petsTable.setRowFactory(tv -> {
            TableRow<Pet> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Pet selectedPet = row.getItem();

                    if (selectedPet.getOwner() == null) {
                        selectedPet.setOwner(selectedOwner);
                        System.out.println("DEBUG - Owner assigned to pet before edit: " + selectedOwner.getName());
                    }

                    showEditPet(selectedPet);
                }
            });
            return row;
        });

        visitsTable.setRowFactory(tv -> {
            TableRow<Visit> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Visit selectedVisit = row.getItem();

                    if (!selectedOwner.getPets().isEmpty()) {
                        Pet defaultPet = selectedOwner.getPets().get(0);
                        showAddVisitPage(defaultPet);
                    } else {
                        showAlert("Error", "Cannot add visit: no pet found.");
                    }
                }
            });
            return row;
        });
    }

    @FXML
    private void editOwner() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditOwner.fxml"));
            Parent view = loader.load();

            EditOwnerController controller = loader.getController();
            controller.setOwner(selectedOwner);
            controller.setMainContainer((BorderPane) petsTable.getScene().getRoot());

            BorderPane root = (BorderPane) petsTable.getScene().getRoot();
            root.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load owner edit page.");
        }
    }

    @FXML
    private void addNewPet() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddPet.fxml"));
            Parent view = loader.load();

            AddPetController controller = loader.getController();
            controller.setOwner(selectedOwner);
            controller.setMainContainer((BorderPane) petsTable.getScene().getRoot());

            BorderPane root = (BorderPane) petsTable.getScene().getRoot();
            root.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load add new pet page.");
        }
    }

    private void showEditPet(Pet pet) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditPet.fxml"));
            Parent view = loader.load();

            EditPetController controller = loader.getController();
            controller.setPet(pet);
            controller.setMainContainer((BorderPane) petsTable.getScene().getRoot());

            BorderPane root = (BorderPane) petsTable.getScene().getRoot();
            root.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load pet edit page.");
        }
    }

    private void showAddVisitPage(Pet pet) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddVisit.fxml"));
            Parent view = loader.load();

            AddVisitController controller = loader.getController();
            controller.setPet(pet);
            controller.setMainContainer((BorderPane) petsTable.getScene().getRoot());

            BorderPane root = (BorderPane) petsTable.getScene().getRoot();
            root.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load add visit page.");
        }
    }

    private void showEditVisit(Visit visit) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddVisit.fxml"));
            Parent view = loader.load();

            AddVisitController controller = loader.getController();
            controller.setPet(visit.getPet());
            controller.setVisit(visit);
            controller.setMainContainer((BorderPane) petsTable.getScene().getRoot());

            BorderPane root = (BorderPane) petsTable.getScene().getRoot();
            root.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load visit edit page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}