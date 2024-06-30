package it.univr.wordautoma;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class ModifyNodeController {
    @FXML
    TextField vecchioNomeTextField;
    @FXML
    TextField nuovoNomeTextField;
    @FXML
    CheckBox isEndNodeCheckBox;

    Automa automa = Automa.getInstance();

    @FXML
    protected void onConfermaClick()
    {
        String nome;
        if(nuovoNomeTextField.getText().isEmpty())
        {
            automa.ModifyNode(vecchioNomeTextField.getText(),vecchioNomeTextField.getText(),isEndNodeCheckBox.isSelected());
        }
        else
        {
            automa.ModifyNode(vecchioNomeTextField.getText(),nuovoNomeTextField.getText(),isEndNodeCheckBox.isSelected());
        }
    }

}
