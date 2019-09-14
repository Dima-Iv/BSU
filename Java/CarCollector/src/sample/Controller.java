package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import window.WindowLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button startButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startButton.setOnAction(event -> {
            WindowLoader windowLoader = new WindowLoader();
            windowLoader.openNewScene("/table.fxml", startButton);
        });
    }
}

