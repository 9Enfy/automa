package it.univr.wordautoma;

import java.util.List;

public class Message {
    private String msg = "";
    private String sequence = "";

    public void setMessage(String msg){
        this.msg = msg;
    }

    public void setSequence(List<String> nodes){
        if(nodes == null) return;

        sequence = "\n\nSequenza: ";
        for(String node : nodes)
            sequence += node + " -> ";
        sequence += "FIN";
    }

    public String getResult(){
        return msg + sequence;
    }
}
