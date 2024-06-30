package it.univr.wordautoma;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class EliminaNodoController {

    @FXML
    TextField nodeNameTextField;
    @FXML
    Automa automa = Automa.getInstance();

    @FXML
    protected void onConfermaClick()
    {
        automa.DeleteNode(nodeNameTextField.getText());
    }
}
