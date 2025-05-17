package vetcare360.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private BorderPane mainContainer;

    @FXML
    private Button homeButton;

    @FXML
    private Button findOwnersButton;

    @FXML
    private Button veterinariansButton;

    @FXML
    private Button errorButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPage("/views/Home.fxml");

        homeButton.setOnAction(event -> loadPage("/views/Home.fxml"));
        findOwnersButton.setOnAction(event -> loadPage("/views/SearchOwner.fxml"));
        veterinariansButton.setOnAction(event -> loadPage("/views/VeterinarianList.fxml"));
        errorButton.setOnAction(event -> loadPage("/views/Error.fxml"));
    }

    private void loadPage(String fxmlPath) {
        try {
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                System.err.println("Error: FXML file not found - " + fxmlPath);
                loadErrorPage();
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent view = loader.load();

            Object controller = loader.getController();
            if (controller instanceof AddOwnerController) {
                ((AddOwnerController) controller).setMainContainer(mainContainer);
            }

            mainContainer.setCenter(view);

        } catch (IOException e) {
            System.err.println("Error loading page: " + fxmlPath);
            e.printStackTrace();
            loadErrorPage();
        }
    }

    private void loadErrorPage() {
        try {
            URL errorResource = getClass().getResource("/views/Error.fxml");
            if (errorResource == null) {
                System.err.println("Critical error: Error page not found!");
                return;
            }

            FXMLLoader loader = new FXMLLoader(errorResource);
            Parent errorView = loader.load();

            mainContainer.setCenter(errorView);

        } catch (IOException e) {
            System.err.println("Unable to load error page.");
            e.printStackTrace();
        }
    }
}