package it.univr.wordautoma;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;


public class HelloController {
    Automa test;
    @FXML
    private Label welcomeText;

    @FXML
    private TextField simulateText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onButtonClick()
    {
        test = new Automa();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage tempStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(tempStage);
        /*if (selectedFile != null) {
            tempStage.display(selectedFile);
        }*/
        try {
            test.ReadAutomaFromFile(selectedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onSimulateClick()
    {
        test.Simulate(simulateText.getText());
    }

    @FXML
    protected void onNewClick() { // risolvere: quando si preme "Annulla" nella finestra di dialogo
        test = new Automa();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Automa");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Stage tempStage = new Stage();
        File selectedFile = fileChooser.showSaveDialog(tempStage);
        if(!selectedFile.getName().contains(".txt"))
            selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
        String dirPath = selectedFile.getAbsolutePath().toString().substring(0, selectedFile.getAbsolutePath().toString().length() - (selectedFile.getName().toString().length()));
        File dir = new File(dirPath);
        String fileName = selectedFile.getName().toString();
        System.out.println(dirPath + ' ' + fileName);
        File newFile = new File(dir, fileName);
        try {
            // File.createNewFile() Method Used
            boolean isFileCreated = newFile.createNewFile();
            if (isFileCreated) {
                System.out.println("File created successfully.");
            }
            else {
                System.out.println("File already exists or an error occurred.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}