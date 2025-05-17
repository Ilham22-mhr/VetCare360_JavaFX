package vetcare360.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private ImageView homeImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeLabel.setText("Welcome to VetCare360");

        try {
            Image image = new Image(getClass().getResourceAsStream("/images/VetCare360.png"));
            homeImageView.setImage(image);
            homeImageView.setPreserveRatio(true);
            homeImageView.setFitWidth(400);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}