package it.univr.wordautoma;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MaxWindow maxWindow = MaxWindow.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), maxWindow.getWidth(), maxWindow.getHeight());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getProperties().put("hostServices", this.getHostServices());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}