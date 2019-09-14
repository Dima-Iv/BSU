package window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class WindowLoader {
    public void openNewScene(String window, Node node) {
        node.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        Parent parent = loader.getRoot();
        loader.getController();

        try {
            parent = loader.load();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Exception", 0);
        }

        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Collector");
        stage.show();
    }

    public File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        String currentDir = System.getProperty("user.home");

        File file = new File(currentDir);
        fileChooser.setInitialDirectory(file);
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml"),
                new FileChooser.ExtensionFilter("Binary Files", "*.out"));
        Stage stage = new Stage();
        stage.setResizable(false);
        File selectedFile = fileChooser.showOpenDialog(stage);
        return selectedFile;
    }

    public File openSaveFileChooser() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml"),
                new FileChooser.ExtensionFilter("Binary Files", "*.out"),
                new FileChooser.ExtensionFilter("HTML", "*.html"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());
        return file;
    }
}
