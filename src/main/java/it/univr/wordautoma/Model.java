package it.univr.wordautoma;

import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Model {
    private static Model instance;
    private String currentImage = "defaultImage";
    private static Path IMAGE_PATH;
    private FileManager filemanager;

    public static Model getInstance() {
        if(instance == null) {
            instance = new Model();
        }
        return instance;
    }

    private Model() {
        filemanager = FileManager.getInstance();
    }

    private void update_last_index() {
        File folder = new File(filemanager.getTemporaryWorkDirectory());
        List<String> filenames = new ArrayList<>();
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".png")) {
                filenames.add(file.getName());
                //System.out.println(file.getName());
            }
        }
        Collections.sort(filenames);
        if(!filenames.isEmpty())
            currentImage = filenames.get(filenames.size()-1);
    }

    public Image getImage() {
        String path = filemanager.getTemporaryWorkDirectory() + File.separator+ currentImage;
        //System.out.println("Loading image: " + path);
        //System.out.println("Images in folder: ");
        update_last_index();
        //System.out.println();
        if(currentImage.equals("defaultImage"))
        {
            return new Image(getClass().getResource("defaultImage.png").toString(),true);
        }
        String newImagePath = filemanager.getTemporaryWorkDirectory()+"/"+currentImage;
        File dir = new File(filemanager.getTemporaryWorkDirectory());
        File path1 = new File(dir,currentImage);
       // System.out.println(path1.getAbsolutePath());
        try {
            return new Image(path1.toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
