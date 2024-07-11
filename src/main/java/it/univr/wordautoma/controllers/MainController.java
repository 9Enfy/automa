package it.univr.wordautoma.controllers;

import it.univr.wordautoma.ComponentiAutoma.Automa;
import it.univr.wordautoma.FileManager;
import it.univr.wordautoma.MainApplication;
import it.univr.wordautoma.Message;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalTime;

/**
 * Controller della finestra utente principale.
 */
public class MainController {
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

    /**
     * Metodo che inizializza l'aggiornamento dell'immagine del grafo ogni 100 millisecondi
     *
     */
    private void initialize_image_updater() {
        image_updater = new Timeline(new KeyFrame(Duration.millis(100), e -> updateImage()));
        image_updater.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Metodo che permette di aggiornare l'immagine. Il programma aggiorna l'immagine ogni 100 millisecondi
     */
    private void updateImage() {
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
    protected void onAggiungiNodoClick() throws IOException {
        CreaFinestra("inserisciNodo.fxml","Aggiungi Nodo");

    }
    @FXML
    protected void onModificaNodoClick() throws IOException {
        CreaFinestra("modificaNodo.fxml","Modifica Nodo");
    }
    @FXML
    protected void onEliminaNodoClick() throws IOException
    {
        CreaFinestra("eliminaNodo.fxml","Elimina Nodo");
    }

    @FXML
    protected void onAggiungiArcoClick() throws IOException
    {
        CreaFinestra("aggiungiArco.fxml","Aggiungi Arco");
    }
    @FXML
    protected void onModificaArcoClick() throws IOException
    {
        CreaFinestra("modificaArco.fxml","Modifica Arco");
    }
    @FXML
    protected void onEliminaArcoClick() throws IOException
    {
        CreaFinestra("eliminaArco.fxml","Elimina Arco");
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

    private void CreaFinestra(String nomeFXML,String titolo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(nomeFXML));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle(titolo);
        stage.setResizable(false);
        stage.show();
    }

}