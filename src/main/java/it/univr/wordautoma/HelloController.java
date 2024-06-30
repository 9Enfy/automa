package it.univr.wordautoma;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
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

    @FXML
    private Text message;

    private Model model;
    private Timeline image_updater;
    private FileManager filemanager;


    @FXML
    private void initialize() {
        filemanager = FileManager.getInstance();
        //resize();
        test = Automa.getInstance();
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
       // System.out.println("Current Time: " + time);
        test.toImage();
        model.getImage();
        graph.setImage(model.getImage());
    }

    @FXML
    private Pane pane;

    @FXML
    private HBox windowBox;

    // non funzionano getWidth/Height
    private void reSize() {
        double w = (windowBox.getWidth() - windowBox.getPadding().getLeft() - windowBox.getPadding().getRight() - pane.getWidth());
        double h = (windowBox.getHeight() - windowBox.getPadding().getBottom() - windowBox.getPadding().getTop() - pane.getHeight());
        System.out.println(w + " - " + h);
        System.out.println(windowBox.getWidth() + " - " + windowBox.getPadding().getLeft() + " - " + windowBox.getPadding().getRight() + " - " + pane.getWidth());
        System.out.println(windowBox.getHeight() + " - " + windowBox.getPadding().getBottom() + " - " + windowBox.getPadding().getTop() + " - " + pane.getHeight());
        graph.setFitWidth(w);
        graph.setFitHeight(h);
    }

    private void printResult(Message resultText){
        message.setText(resultText.getResult());
    }

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
        System.out.println("newugnjngkjan");
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
    protected void onNewClick() { // risolvere: quando si preme "Annulla" nella finestra di dialogo
       filemanager.CreateNewFile();
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