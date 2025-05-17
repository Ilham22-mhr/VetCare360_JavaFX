package vetcare360.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;

import vetcare360.models.Owner;
import vetcare360.models.Pet;

import java.io.IOException;
import java.time.LocalDate;

public class OwnerListController {

    @FXML private TableView<Owner> ownersTable;
    @FXML private TableColumn<Owner, String> nameColumn;
    @FXML private TableColumn<Owner, String> addressColumn;
    @FXML private TableColumn<Owner, String> cityColumn;
    @FXML private TableColumn<Owner, String> telephoneColumn;
    @FXML private TableColumn<Owner, String> petsColumn;

    private ObservableList<Owner> ownersList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        petsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPetsAsString())
        );

        ownersList.addAll(
                new Owner("1", "George", "Franklin", "2693 Commerce St.", "Windsor", "6085551023", FXCollections.observableArrayList()),
                new Owner("2", "Betty", "Davis", "563 Friendly St.", "Madison", "6085551749", FXCollections.observableArrayList()),
                new Owner("3", "Eduardo", "Rodriquez", "110 W. Liberty St.", "Madison", "6085558763", FXCollections.observableArrayList()),
                new Owner("4", "Harold", "Davis", "638 Cardinal Ave.", "Madison", "6085553198", FXCollections.observableArrayList()),
                new Owner("5", "Peter", "McTavish", "2387 S. Fair Way", "Sun Prairie", "6085552765", FXCollections.observableArrayList()),
                new Owner("6", "Jean", "Coleman", "105 N. Lake St.", "Monona", "6085552654", FXCollections.observableArrayList()),
                new Owner("7", "Jeff", "Black", "1450 Oak Blvd.", "Monona", "6085555387", FXCollections.observableArrayList()),
                new Owner("8", "Maria", "Escobito", "345 Maple St.", "Madison", "6083557683", FXCollections.observableArrayList()),
                new Owner("9", "David", "Schroeder", "2749 Blackhawk Trail", "Madison", "6085559435", FXCollections.observableArrayList()),
                new Owner("10", "Carlos", "Estaban", "2335 Independence La.", "Waunakee", "6085555487", FXCollections.observableArrayList())
        );

        ownersList.get(0).addPet(new Pet("1", "Leo", LocalDate.of(2018, 5, 1), "Dog"));
        ownersList.get(1).addPet(new Pet("2", "Basil", LocalDate.of(2017, 3, 12), "Cat"));
        ownersList.get(2).addPet(new Pet("3", "Jewel", LocalDate.of(2019, 8, 20), "Dog"));
        ownersList.get(2).addPet(new Pet("4", "Rosy", LocalDate.of(2020, 11, 5), "Cat"));
        ownersList.get(3).addPet(new Pet("5", "Iggy", LocalDate.of(2016, 7, 15), "Bird"));
        ownersList.get(4).addPet(new Pet("6", "George", LocalDate.of(2017, 2, 10), "Dog"));
        ownersList.get(5).addPet(new Pet("7", "Max", LocalDate.of(2018, 4, 22), "Dog"));
        ownersList.get(5).addPet(new Pet("8", "Samantha", LocalDate.of(2019, 9, 9), "Cat"));
        ownersList.get(6).addPet(new Pet("9", "Lucky", LocalDate.of(2015, 1, 30), "Dog"));
        ownersList.get(7).addPet(new Pet("10", "Mulligan", LocalDate.of(2018, 12, 17), "Dog"));
        ownersList.get(8).addPet(new Pet("11", "Freddy", LocalDate.of(2020, 6, 18), "Cat"));
        ownersList.get(9).addPet(new Pet("12", "Lucky Sly", LocalDate.of(2017, 5, 5), "Dog"));

        ownersTable.setItems(ownersList);

        ownersTable.setRowFactory(tv -> {
            TableRow<Owner> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Owner selectedOwner = row.getItem();
                    showOwnerDetails(selectedOwner);
                }
            });
            return row;
        });
    }

    public void addNewOwner(Owner owner) {
        ownersList.add(owner);
        ownersTable.setItems(ownersList);
    }

    private void showOwnerDetails(Owner owner) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OwnerDetails.fxml"));
            Parent detailsView = loader.load();

            OwnerDetailsController controller = loader.getController();
            controller.setOwner(owner);

            BorderPane root = (BorderPane) ownersTable.getScene().getRoot();
            root.setCenter(detailsView);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load owner details.");
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