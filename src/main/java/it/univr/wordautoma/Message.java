package it.univr.wordautoma;

import java.util.List;

public class Message {
    private String msg = "";
    private String sequence = "";

    public void setMessage(String msg){
        this.msg = msg;
    }

    public void setSequence(List<String> nodes,List<String> weigth){
        if(nodes == null) return;

        sequence = "\n\nSequenza: ";
        for(int i=0;i<nodes.size()-1;i++)
            sequence += nodes.get(i) + " -> ("+weigth.get(i)+") -> ";
        sequence+= nodes.get(nodes.size()-1);
    }

    public String getResult(){
        return msg + sequence;
    }
}
