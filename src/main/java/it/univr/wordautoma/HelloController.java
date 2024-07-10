package it.univr.wordautoma;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalTime;


public class HelloController {
    Automa test;

    // IMAGE UPDATER
    @FXML
    private ImageView graph;

    private Timeline image_updater;
    private FileManager filemanager;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private Stage stage;

    @FXML
    private void initialize() {
        filemanager = FileManager.getInstance();
        test = Automa.getInstance();
        updateImage();
        initialize_image_updater();
        image_updater.play();
    }

    private void initialize_image_updater() {
        image_updater = new Timeline(new KeyFrame(Duration.millis(100), e -> updateImage()));
        image_updater.setCycleCount(Timeline.INDEFINITE);
    }

    public void updateImage() {
        LocalTime time = LocalTime.now();
        test.toImage();
        File dir = new File(filemanager.getTemporaryWorkDirectory());
        File path1 = new File(dir,"test.png");
        try {
            graph.setImage(new Image(path1.toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void printResult(Message resultText){
        alert.setContentText(resultText.getResult());
        alert.setHeaderText("Simulazione terminata");
        alert.setTitle("Result");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    @FXML
    private TextField simulateText;

    @FXML
    protected void onButtonClick()
    {
        File selectedFile = filemanager.SelectFile();
        try {
            test.ReadAutomaFromFile(selectedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onSimulateClick()
    {
        printResult(test.Simulate(simulateText.getText()));
    }

    @FXML
    protected void OnSaveClick()
    {
        //controlla se l'automa è scritto bene, poi salva su file
        SaveToFile();

    }
    @FXML
    protected void onInserisciNodoClick() throws IOException {
        CreaFinestra("inserisciNodo.fxml");

    }
    @FXML
    protected void onModificaNodoClick() throws IOException {
        CreaFinestra("modificaNodo.fxml");
    }
    @FXML
    protected void onEliminaNodoClick() throws IOException
    {
        CreaFinestra("eliminaNodo.fxml");
    }

    @FXML
    protected void onAggiungiArcoClick() throws IOException
    {
        CreaFinestra("aggiungiArco.fxml");
    }
    @FXML
    protected void onModificaArcoClick() throws IOException
    {
        CreaFinestra("modificaArco.fxml");
    }
    @FXML
    protected void onEliminaArcoClick() throws IOException
    {
        CreaFinestra("eliminaArco.fxml");
    }
    @FXML
    protected void onAboutClick()
    {
        HostServices hostServices = (HostServices)this.getStage().getProperties().get("hostServices");
        hostServices.showDocument("https://github.com/9Enfy/automa");
    }
    public Stage getStage() {
        if(this.stage==null)
            this.stage = (Stage) this.graph.getScene().getWindow();
        return stage;
    }
    private void SaveToFile()
    {
        filemanager.SaveToFile(test.toString());
    }

    private void CreaFinestra(String nomeFXML) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(nomeFXML));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}