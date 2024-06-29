package it.univr.wordautoma;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {



    private String temporaryWorkDirectory="";

    private static final FileManager instance = new FileManager();

    private FileManager() {

    }
    public static FileManager getInstance()
    {
        return instance;
    }

    String getTemporaryWorkDirectory()
    {
        if(temporaryWorkDirectory.isEmpty())
        {
            try {
                File tmp = Files.createTempDirectory("workspace").toFile();
                tmp.setWritable(true,false);
                tmp.setReadable(true,false);
                tmp.setExecutable(true,false);
                temporaryWorkDirectory = tmp.toPath().toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return temporaryWorkDirectory;
    }

    File SelectFile()
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
        return selectedFile;
    }

    void CreateNewFile()
    {
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

    File SaveToFile(String whatToSave) {
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
        if (!selectedFile.getName().contains(".txt"))
            selectedFile = new File(selectedFile.getAbsolutePath() + ".dot");
        String dirPath = selectedFile.getAbsolutePath().toString().substring(0, selectedFile.getAbsolutePath().toString().length() - (selectedFile.getName().toString().length()));
        File dir = new File(dirPath);
        String fileName = selectedFile.getName().toString();
        System.out.println(dirPath + ' ' + fileName);
        File newFile = new File(dir, fileName);
        newFile.setReadable(true,false);
        try {
            FileWriter fW = new FileWriter(newFile);
            fW.write(boiler + "\n" + whatToSave);
            fW.close();
        } catch (IOException e) {
            System.out.println("Scrittura nel file fallita");
            throw new RuntimeException(e);
        }
        return newFile;
    }
    File SaveToFileAutomatic(String whatToSave)
    {
        String boiler = "digraph finite_state_machine {\n" +
                "fontname=\"Helvetica,Arial,sans-serif\"\n" +
                "node [fontname=\"Helvetica,Arial,sans-serif\"]\n" +
                "edge [fontname=\"Helvetica,Arial,sans-serif\"]\n" +
                "start [label= \"\", shape=none,height=.0,width=.0]\n" +
                "rankdir=LR;\n" +
                "nodesep= 0.5;\n" +
                "ranksep = 0.5;\n";
        File newFile = new File(getTemporaryWorkDirectory(), "test.dot");
        try {
            FileWriter fW = new FileWriter(newFile);
            fW.write(boiler + "\n" + whatToSave);
            fW.close();
        } catch (IOException e) {
            System.out.println("Scrittura nel file fallita");
            throw new RuntimeException(e);
        }
        return newFile;
    }


}
