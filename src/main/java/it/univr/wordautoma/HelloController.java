package it.univr.wordautoma;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;


public class HelloController {
    Automa test;

    // IMAGE UPDATER
    @FXML
    private ImageView graph;

    private Model model;
    private Timeline image_updater;

    @FXML
    private void initialize() {
        test = new Automa();
        model = Model.getInstance();
        updateImage();
        initialize_image_updater();
        image_updater.play();
    }

    private void initialize_image_updater() {
        image_updater = new Timeline(new KeyFrame(Duration.seconds(2), e -> updateImage()));
        image_updater.setCycleCount(Timeline.INDEFINITE);
    }

    public void updateImage() {
        LocalTime time = LocalTime.now();
        System.out.println("Current Time: " + time);
        model.getImage();
        graph.setImage(model.getImage());
    }
/*
    @FXML
    private void HelloController()
    {
        test = new Automa();
    }*/

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Dot Files", "*.dot"),
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

    @FXML
    protected void OnSaveClick()
    {
        //controlla se l'automa è scritto bene, poi salva su file
        SaveToFile();

    }

    private void SaveToFile()
    {
        String boiler = "digraph finite_state_machine {\n" +
                "fontname=\"Helvetica,Arial,sans-serif\"\n" +
                "node [fontname=\"Helvetica,Arial,sans-serif\"]\n" +
                "edge [fontname=\"Helvetica,Arial,sans-serif\"]\n" +
                "start [label= \"\", shape=none,height=.0,width=.0]\n" +
                "rankdir=LR;\n" +
                "nodesep= 0.5;\n" +
                "ranksep = 0.5;\n";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Automa");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Dot Files", "*.dot"));
        Stage tempStage = new Stage();
        File selectedFile = fileChooser.showSaveDialog(tempStage);
        if(!selectedFile.getName().contains(".txt"))
            selectedFile = new File(selectedFile.getAbsolutePath() + ".dot");
        String dirPath = selectedFile.getAbsolutePath().toString().substring(0, selectedFile.getAbsolutePath().toString().length() - (selectedFile.getName().toString().length()));
        File dir = new File(dirPath);
        String fileName = selectedFile.getName().toString();
        System.out.println(dirPath + ' ' + fileName);
        File newFile = new File(dir, fileName);
        try {
            FileWriter fW = new FileWriter(newFile);
            fW.write(boiler+"\n"+test);
            fW.close();
        } catch (IOException e) {
            System.out.println("Scrittura nel file fallita");
            throw new RuntimeException(e);
        }/*
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
        }*/
    }
}