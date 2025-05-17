package vetcare360.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorController implements Initializable {

    @FXML
    private Label errorMessageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessageLabel.setText("Expected: controller used to showcase what happens when an exception is thrown");
    }
}