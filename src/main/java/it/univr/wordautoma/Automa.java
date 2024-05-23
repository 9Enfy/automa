package it.univr.wordautoma;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Automa {
    ArrayList<Node> allNode;
    ArrayList<Arch> allArch;
    public Automa()
    {
        allNode = new ArrayList<Node>();
        allArch = new ArrayList<Arch>();
    }
    public void ReadAutomaFromFile(File fileDaLeggere) throws IOException {
        FileInputStream fs= new FileInputStream(fileDaLeggere);
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));
        for(int i = 0; i < 9; i++) //la prima riga nodo è la decima
            br.readLine();
        String lineIWant = br.readLine();
        String[] test =lineIWant.split(" ");
        while(!lineIWant.isEmpty())
        {
            test = lineIWant.split(" ");
            Node tempNode = new Node(test[3]);
            tempNode.setId(String.valueOf(test[0].charAt(0)));
            tempNode.setIsEnd(Objects.equals(test[6], "doublecircle"));
            allNode.add(tempNode);
            lineIWant = br.readLine();
        }
        lineIWant = br.readLine();
        lineIWant = br.readLine();
        while(!lineIWant.isEmpty())
        {
            test = lineIWant.split(" ");
            Arch tempArco = new Arch(allNode.get(Integer.parseInt(test[0])),allNode.get(Integer.parseInt(test[2])),test[5]);
            allArch.add(tempArco);
            lineIWant = br.readLine();
        }
        System.out.println("FINE");
    }
    public void Simulate(String simulateText)
    {
        Node selectedNode = allNode.get(0);
        int length=0;
        int index=0;
        int tempIndex=0;
        int finalIndex=-1;
        Boolean isValid;
        Node tempNode = null;
        int i;
        int c;
        int arcoTrovato=-1;
        while(true)
        {
            length=0;
            arcoTrovato=-1;
            for(i=0;i<allArch.size();i++)
            {
                if(allArch.get(i).getSenderNode()==selectedNode)
                {
                    isValid=true;
                    tempIndex=0;
                    for(c=0;c<allArch.get(i).getWeigth().length() && isValid;c++)
                    {
                        if(!(simulateText.charAt(index+tempIndex)==allArch.get(i).getWeigth().charAt(c)))
                        {
                            isValid=false;
                        }
                        tempIndex++;
                    }
                    if(isValid && allArch.get(i).getWeigth().length()>length)
                    {
                        tempNode = allNode.get(Integer.parseInt(allArch.get(i).getReceiverNode().getId()));
                        length = allArch.get(i).getWeigth().length();
                        arcoTrovato=i;
                    }
                }
            }
            if(tempNode==null)
            {
                System.out.println("Non è possibile proseguire");
                return;
            }
            else
            {
                System.out.println("Nodo successivo trovato!");
                index=index+allArch.get(arcoTrovato).getWeigth().length();
                selectedNode = tempNode;
                tempNode=null;
                if(index==simulateText.length())
                {
                    if(selectedNode.isEnd)
                    {
                        System.out.println("Arrivati alla fine");
                    }
                    else
                    {
                        System.out.println("Arrivati alla fine, ma non si è in un nodo fine");
                    }
                    return;
                }
            }
        }
    }
}
