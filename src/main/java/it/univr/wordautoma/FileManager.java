package it.univr.wordautoma;

import it.univr.wordautoma.ComponentiAutoma.Automa;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Classe che gestisce i file esterni utilizzati dal programma.
 * FileManager, la prima volta che viene chiamato, crea una cartella temporanea nella directory TMP.
 * All'interno della cartella temporanea sono presenti il file dot che rappresenta il grafo e l'immagine (test.png) che rappresenta il grafo.
 * La classe utilizza l'approccio Singleton, quindi esiste un'unica istanza della classe FileManager per tutto il programma.
 * TODO La cartella temporanea non viene cancellata quando il programma viene chiuso. La cartella viene comunque cancellata quando si spegne il PC.
 */
public class FileManager {



    private String temporaryWorkDirectory="";

    private static final FileManager instance = new FileManager();

    private FileManager() {

    }

    /**
     * Metodo per ottenere l'istanza del FileManager
     * @return l'istanza del FileManager
     */
    public static FileManager getInstance()
    {
        return instance;
    }

    /**
     * Metodo per ottenere il path  della cartella temporanea. Nel caso non esiste ancora la directory temporanea (priva incovazione del metodo) viene creata
     *
     * @return Il path come String della cartella temporanea
     */
    public String getTemporaryWorkDirectory()
    {
        if(temporaryWorkDirectory.isEmpty())
        {
            try {
                File tmp = Files.createTempDirectory("workspace").toFile();
                tmp.setWritable(true,false);
                tmp.setReadable(true,false);
                tmp.setExecutable(true,false);
                tmp.deleteOnExit();
                temporaryWorkDirectory = tmp.toPath().toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return temporaryWorkDirectory;
    }

    /**
     * Metodo che permette di aprire il file explorer usato di default dal Sistema operativo.
     * File explorer testati e funzionanti: Dolphin, Thunar, Nautilus
     * @return Il file che l'utente sceglie
     */
    public File SelectFile()
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

    /**
     * Metodo che permette all'utente dell'applicazione di salvare il grafo in una qualsiasi cartella. Il file ha estensione .dot.
     * @param whatToSave Stringa che verrà memorizzata nel file. Nel programma è l'insieme di nodi e archi scritti in modo che il programma esterno "dot" di graphviz riesca a leggerlo
     * @return
     */
    public Boolean SaveToFile(String whatToSave) {
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
        if (!selectedFile.getName().contains(".dot"))
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
            Automa.ShowAlert("Scrittura nel file fallita");
            return false;
        } catch (NullPointerException e)
        {
            Automa.ShowAlert("Scrittura nel file fallita");
            return false;
        }
        return true;
    }

    /**
     * Salva il grafo in un file .dot dentro la cartella temporanea.
     * @param whatToSave Stringa che verrà memorizzata nel file. Nel programma è l'insieme di nodi e archi scritti in modo che il programma esterno "dot" di graphviz riesca a leggerlo
     * @return
     */
    public File SaveToFileAutomatic(String whatToSave)
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
    public Boolean ExportImage()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporta l'immagine");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Png Files", "*.png"));
        Stage tempStage = new Stage();
        File selectedFile = fileChooser.showSaveDialog(tempStage);
        if (!selectedFile.getName().contains(".png"))
            selectedFile = new File(selectedFile.getAbsolutePath() + ".png");
        File fileDaCopiare = new File(getTemporaryWorkDirectory(),"test.png");
        try
        {
            Files.copy(fileDaCopiare.toPath(), selectedFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e)
        {
            return false;
        }
        return true;


    }


}
