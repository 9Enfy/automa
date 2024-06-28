package it.univr.wordautoma;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {
    private static Model instance;
    private String currentImage = "-1.png";
    private static final String IMAGE_PATH = "img/";

    public static Model getInstance() {
        if(instance == null) {
            instance = new Model();
        }
        return instance;
    }

    private Model() {

    }

    private void update_last_index() {
        File folder = new File(IMAGE_PATH);
        List<String> filenames = new ArrayList<>();
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".png")) {
                filenames.add(file.getName());
                System.out.println(file.getName());
            }
        }
        Collections.sort(filenames);
        currentImage = filenames.get(filenames.size()-1);
    }

    public Image getImage() {
        String path = IMAGE_PATH + currentImage + ".png";
        System.out.println("Loading image: " + path);
        System.out.println("Images in folder: ");
        update_last_index();
        return new Image(IMAGE_PATH + currentImage + ".png");
    }
}
