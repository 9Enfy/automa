package it.univr.wordautoma;

import java.io.*;
import java.util.*;

public class Automa {
    static ArrayList<Node> allNode;
    static ArrayList<Arch> allArch;
    private static final Automa instance = new Automa();
    static Boolean isFirstTime = true;
    static FileManager filemanager;
    private Automa()
    {

    }
    public static Automa getInstance()
    {
        if(isFirstTime)
        {
            allNode = new ArrayList<Node>();
            Node primo = new Node("q0");
            primo.isEnd = false;
            primo.setId("0");
            allNode.add(primo);
            allArch = new ArrayList<Arch>();
            filemanager = FileManager.getInstance();
            isFirstTime = false;
        }
        return instance;

    }
    public void ReadAutomaFromFile(File fileDaLeggere) throws IOException {
        allNode = new ArrayList<Node>();
        allArch = new ArrayList<Arch>();
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
    public void Simulate(String simulateText) {
        Node selectedNode = allNode.get(0);
        int length = 0;
        int index = 0;
        int tempIndex = 0;
        int finalIndex = -1;
        Boolean isValid;
        Node tempNode = null;
        int i;
        int c;
        int arcoTrovato = -1;
        while (true) {
            length = 0;
            arcoTrovato = -1;
            for (i = 0; i < allArch.size(); i++) {
                if (allArch.get(i).getSenderNode() == selectedNode) {
                    isValid = true;
                    tempIndex = 0;
                    for (c = 0; c < allArch.get(i).getWeigth().length() && isValid; c++) {
                        if (index + tempIndex >= simulateText.length()) {
                            isValid = false;
                        } else if (!(simulateText.charAt(index + tempIndex) == allArch.get(i).getWeigth().charAt(c))) {
                            isValid = false;
                        }
                        tempIndex++;
                    }
                    if (isValid && allArch.get(i).getWeigth().length() > length) {
                        Boolean trovato = false;
                        tempNode = allArch.get(i).receiverNode;
                        length = allArch.get(i).getWeigth().length();
                        arcoTrovato = i;
                    }
                }
            }
            if (tempNode == null) {
                System.out.println("Non è possibile proseguire");
                return;
            } else {
                System.out.println("Nodo successivo trovato!");
                index = index + allArch.get(arcoTrovato).getWeigth().length();
                selectedNode = tempNode;
                tempNode = null;
                if (index == simulateText.length()) {
                    if (selectedNode.isEnd) {
                        System.out.println("Arrivati alla fine");
                    } else {
                        System.out.println("Arrivati alla fine, ma non si è in un nodo fine");
                    }
                    return;
                }
            }
        }
    }
    public String toString()
    {
        StringBuilder stringaNodi= new StringBuilder();
        Node tempNode;
        stringaNodi.append("0 [label = q0 ,shape = circle ]\n");
        for(int i=0;i<allNode.size();i++)
        {
            tempNode= allNode.get(i);
            stringaNodi.append(tempNode.id).append(" [label = ").append(tempNode.nome).append(" ,shape = ");
            if(tempNode.isEnd)
                stringaNodi.append("doublecircle ]\n");
            else
                stringaNodi.append("circle ]\n");
        }
        //scrivi archi
        StringBuilder stringaArchi= new StringBuilder("start -> 0\n");
        Arch tempArch;
        for(int i=0;i<allArch.size();i++)
        {
            tempArch = allArch.get(i);
            stringaArchi.append(tempArch.getSenderNode().id).append(" -> ").append(tempArch.getReceiverNode().id).append(" [label = ").append(tempArch.getWeigth()).append(" ]\n");
        }
        String stringaFinale = stringaNodi+"\n"+stringaArchi+"\n}";
        return stringaFinale;
    }

    public void toImage()
    {
        //System.out.println("Test");
        File fileDaConvertire = filemanager.SaveToFileAutomatic(this.toString());
        fileDaConvertire.setReadable(true);
        File doveSalvare = new File(filemanager.getTemporaryWorkDirectory()+"/test.png");
        doveSalvare.setWritable(true,false);
        try {
            //String command = "dot -Tpng "+fileDaConvertire.getAbsolutePath()+" -o "+doveSalvare.getAbsolutePath();
            List<String> testListe = new ArrayList<String>();
            testListe.add("dot");
            testListe.add("-Tpng");
            //testListe.add("-Gratio=fill");
            testListe.add(fileDaConvertire.getAbsolutePath());
            testListe.add("-o");
            testListe.add(doveSalvare.getAbsolutePath());
            Process process = new ProcessBuilder(testListe).inheritIO().start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean addNode(String nodoDaAggiungere, Boolean isEnd) {
        if (!nodoDaAggiungere.matches("[a-zA-Z0-9]+")) {
            System.out.println("Contiene caratteri diversi da lettere e numeri");
            return false;
        }
        Boolean trovato=false;
        for(int i=0;i<allNode.size() && !trovato;i++)
        {
            if(allNode.get(i).getNome().equals(nodoDaAggiungere))
                trovato=true;
        }
        if(trovato)
        {
            System.out.println("C'è già un nodo con quel nome");
            return false;
        }
        else
        {
            Node tmp = new Node(nodoDaAggiungere);
            tmp.setIsEnd(false);
            Random random = new Random();
            tmp.setId(String.valueOf(random.nextInt(0,99999)));
            tmp.setIsEnd(isEnd);
            allNode.add(tmp);
            return true;
        }


    }

    public Boolean ModifyNode(String nodoDaModificare, String nuovoNome, Boolean isEnd )
    {
        for(int i=0;i<allNode.size();i++)
        {
            if(nodoDaModificare.equals(allNode.get(i).getNome()) && nuovoNome.matches("[a-zA-Z0-9]+"))
            {

                allNode.get(i).setNome(nuovoNome);
                allNode.get(i).setIsEnd(isEnd);
                return true;
            }
        }
        return false;
    }
    public Boolean DeleteNode(String nodoDaEleminare)
    {
        Node nodoDaEleminareNode = null;
        for(int i=0;i<allNode.size();i++)
        {
            if(nodoDaEleminare.equals(allNode.get(i).getNome()))
            {
                nodoDaEleminareNode = allNode.remove(i);
            }
        }
        if(nodoDaEleminare.isEmpty())
            return false;
        //cancella tutti gli archi che hanno il nodo
        for(int i=0;i<allArch.size();i++)
        {
            if(allArch.get(i).getReceiverNode().equals(nodoDaEleminareNode) || allArch.get(i).getSenderNode().equals(nodoDaEleminareNode))
            {
                allArch.remove(i);
                i--;
            }
        }
        return true;
    }
    public Boolean AggiungiArco(String nomePartenza, String nomeArrivo, String peso)
    {
        Arch arcoTest = null;
        for(int i=0;i<allArch.size();i++)
        {
            arcoTest = allArch.get(i);
            if(arcoTest.getSenderNode().getNome().equals(nomePartenza)&&arcoTest.getReceiverNode().getNome().equals(nomeArrivo)) {
                System.out.println("Esiste già quell'arco");
                return false;
            }
        }
        //ottieni nodo partenza
        Node startNode=null;
        Node endNode=null;
        for(int i=0;i<allNode.size();i++)
        {
            if(allNode.get(i).getNome().equals(nomePartenza))
            {
                startNode = allNode.get(i);
            }
            if(allNode.get(i).getNome().equals(nomeArrivo))
            {
                endNode = allNode.get(i);
            }
        }
        if(startNode==null || endNode==null)
        {
            System.out.println("Uno dei nodi non esiste");
            return false;
        }
        else
        {
            arcoTest = new Arch(startNode,endNode,peso);
            allArch.add(arcoTest);
            return true;
        }
    }
    public Boolean ModificaArco(String nomePartenza, String nomeArrivo, String peso)
    {
        Arch arcoTest = null;
        for(int i=0;i<allArch.size();i++)
        {
            arcoTest = allArch.get(i);
            if(arcoTest.getSenderNode().getNome().equals(nomePartenza)&&arcoTest.getReceiverNode().getNome().equals(nomeArrivo)) {
                allArch.get(i).setWeigth(peso);
                return true;
            }
        }
        return false;
    }
    public Boolean EliminaArco(String nomePartenza, String nomeArrivo)
    {
        Arch arcoTest = null;
        for(int i=0;i<allArch.size();i++)
        {
            arcoTest = allArch.get(i);
            if(arcoTest.getSenderNode().getNome().equals(nomePartenza)&&arcoTest.getReceiverNode().getNome().equals(nomeArrivo)) {
                allArch.remove(i);
                return true;
            }
        }
        return false;
    }

}
