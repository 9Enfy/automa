package it.univr.wordautoma.controllers;

import it.univr.wordautoma.ComponentiAutoma.Automa;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * Controller delle finestre secondarie (aggiungi/modifca/elimina nodo/arco).
 * La classe ha 3 TextField e 1 CheckBox disponibili all'utilizzo. Nel caso una finestra secondaria futura richieda l'utilizzo di 4 o più TextField o di altre componenti, bisogna aggiungerle
 * anche il questa classe. Come nomi si è deciso di dare nome text1, text2, text3 e checkBox1. Questi nomi devono essere utilizzati nei file FXML per il corretto funzionamento del programma
 */
public class OtherController {
    @FXML
    TextField text1;
    @FXML
    TextField text2;
    @FXML
    TextField text3;
    @FXML
    CheckBox checkBox1;

    Automa automa = Automa.getInstance();

    @FXML
    protected void onAggiungiNodoClick()
    {
        automa.addNode(text1.getText(),checkBox1.isSelected());
    }
    @FXML
    protected void onModificaNodoClick()
    {
        if(text2.getText().isEmpty())
            automa.ModifyNode(text1.getText(),text1.getText(),checkBox1.isSelected());
        else
            automa.ModifyNode(text1.getText(),text2.getText(),checkBox1.isSelected());
    }
    @FXML
    protected void onEliminaNodoClick()
    {
        automa.DeleteNode(text1.getText());
    }
    @FXML
    protected void onAggiungiArcoClick()
    {
        automa.AggiungiArco(text1.getText(),text2.getText(),text3.getText());
    }
    @FXML
    protected void onModificaArcoClick()
    {
        automa.ModificaArco(text1.getText(),text2.getText(),text3.getText());
    }
    @FXML
    protected void onEliminaArcoClick()
    {
        automa.EliminaArco(text1.getText(),text2.getText());
    }

}
