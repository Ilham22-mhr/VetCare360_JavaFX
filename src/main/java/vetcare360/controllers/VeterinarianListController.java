package vetcare360.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import vetcare360.models.Veterinarian;

import java.net.URL;
import java.util.ResourceBundle;

public class VeterinarianListController implements Initializable {

    @FXML
    private TableView<Veterinarian> veterinarianTable;

    @FXML
    private TableColumn<Veterinarian, String> nameColumn;

    @FXML
    private TableColumn<Veterinarian, String> specialtiesColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        specialtiesColumn.setCellValueFactory(new PropertyValueFactory<>("specialties"));

        ObservableList<Veterinarian> veterinarians = FXCollections.observableArrayList(
                new Veterinarian("1", "James Carter", "none"),
                new Veterinarian("2", "Linda Douglas", "dentistry surgery"),
                new Veterinarian("3", "Sharon Jenkins", "none"),
                new Veterinarian("4", "Helen Leary", "radiology"),
                new Veterinarian("5", "Rafael Ortega", "surgery"),
                new Veterinarian("6", "Henry Stevens", "radiology")
        );

        veterinarianTable.setItems(veterinarians);
    }
}