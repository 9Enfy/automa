package it.univr.wordautoma;

import java.util.List;

/**
 * Classe che gestisce il messaggio da visualizzare negli alert
 */
public class Message {
    private String msg = "";
    private String sequence = "";

    /**
     * Metodo per impostare la frase risultato
     * @param msg
     */
    public void setMessage(String msg){
        this.msg = msg;
    }

    /**
     * Metodo per impostare la sequenza di passi del risultato
     * @param nodes
     * @param weigth
     */
    public void setSequence(List<String> nodes,List<String> weigth){
        if(nodes == null) return;

        sequence = "\n\nSequenza: ";
        for(int i=0;i<nodes.size()-1;i++)
            sequence += nodes.get(i) + " -> ("+weigth.get(i)+") -> ";
        sequence+= nodes.get(nodes.size()-1);
    }

    /**
     * Metodo per ritornare il messaggio da visualizzare nell'alert
     * @return Messaggio da stampare nell'alert
     */
    public String getResult(){
        return msg + sequence;
    }
}
