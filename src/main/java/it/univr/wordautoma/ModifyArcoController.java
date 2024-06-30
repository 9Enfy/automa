package it.univr.wordautoma;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ModifyArcoController {
    @FXML
    TextField arcoPartenzaTextField;
    @FXML
    TextField arcoArrivoTextField;
    @FXML
    TextField pesoArcoTextField;
    Automa automa = Automa.getInstance();
    @FXML
    protected void onConfermaClick()
    {
        automa.ModificaArco(arcoPartenzaTextField.getText(),arcoArrivoTextField.getText(),pesoArcoTextField.getText());
    }
}
