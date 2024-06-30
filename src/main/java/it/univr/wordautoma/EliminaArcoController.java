package it.univr.wordautoma;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EliminaArcoController {
    @FXML
    TextField arcoPartenzaTextField;
    @FXML
    TextField arcoArrivoTextField;

    Automa automa = Automa.getInstance();
    @FXML
    protected void onConfermaClick()
    {
        automa.EliminaArco(arcoPartenzaTextField.getText(),arcoArrivoTextField.getText());
    }
}
