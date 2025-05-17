package vetcare360;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane mainContainer;

    public void loadView(String fxmlFile) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
            mainContainer.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            loadErrorView();
        }
    }

    private void loadErrorView() {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/views/Error.fxml"));
            mainContainer.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}