package it.univr.wordautoma;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class InsertNodeController {
    @FXML
    TextField nodeNameTextField;
    @FXML
    CheckBox isEndNodeCheckBox;
    Automa automa = Automa.getInstance();

    @FXML
    protected void onConfermaClick()
    {
        automa.addNode(nodeNameTextField.getText(),isEndNodeCheckBox.isSelected());
    }
}
