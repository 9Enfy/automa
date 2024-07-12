package it.univr.wordautoma.ComponentiAutoma;

import it.univr.wordautoma.FileManager;
import it.univr.wordautoma.Message;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

import java.io.*;
import java.util.*;

/**
 * Rappresenta il grafo di nodi e archi usato dall'applicazione.
 * Il grafo può avere al massimo 10000 nodi.
 * La classe utilizza l'approccio Singleton, quindi esiste una sola istanza della classe Automa.
 */
public class Automa {
    private static ArrayList<Node> allNode;
    private static ArrayList<Arch> allArch;
    private static final Automa instance = new Automa();
    static Boolean isFirstTime = true;
    static FileManager filemanager;
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private Automa()
    {

    }

    /**
     * Usare questo metodo per ottenere la istanza del grafo.
     * @return L'istanza dell'automa.
     */
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

    /**
     * Carica nell'istanza un grafo partendo da un file.
     * @param fileDaLeggere Il file che contiene le informazione dell'automa da caricare.
     * @throws IOException
     */
    public void ReadAutomaFromFile(File fileDaLeggere) throws IOException, NullPointerException, NumberFormatException {
        allNode = new ArrayList<Node>();
        allArch = new ArrayList<Arch>();
        FileInputStream fs;
        try{
            fs= new FileInputStream(fileDaLeggere);
        } catch(Exception e) {
            isFirstTime = true;
            getInstance();
            ShowAlert("Non è stato aperto alcun file. Creazione di un nuovo automa");
            System.out.println("File non aperto");
            return;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(fs));
        for(int i = 0; i < 9; i++) //la prima riga nodo è la decima
            br.readLine();
        String lineIWant = br.readLine();
        String[] test;
        while(!lineIWant.isEmpty())
        {
            test = lineIWant.split(" ");
            Node tempNode = new Node(test[3]);
            tempNode.setId(String.valueOf(test[0]));
            tempNode.setIsEnd(Objects.equals(test[6], "doublecircle"));
            allNode.add(tempNode);
            lineIWant = br.readLine();
        }
        lineIWant = br.readLine();
        lineIWant = br.readLine();
        while(!lineIWant.isEmpty())
        {
            test = lineIWant.split(" ");
            Node partenza = null;
            Node arrivo = null;
            for (Node node : allNode) {
                if (node.getId().equals(test[0]))
                    partenza = node;
                if (node.getId().equals(test[2]))
                    arrivo = node;
            }
            if(partenza==null || arrivo==null)
            {
                ShowAlert("Errore nella lettura del file");
                allNode.clear();
                allArch.clear();
                return;
            }
            Arch tempArco = new Arch(partenza,arrivo,test[5]);
            allArch.add(tempArco);
            lineIWant = br.readLine();
        }
        System.out.println("FINE");

    }

    /**
     * Metodo per testare una stringa. Ci possono essere 3 casi:
     * 1) La stringa permette di arrivare ad uno stato finale e non ci sono altri caratteri nella stringa
     * 2) La stringa NON permette di arrivare ad uno stato finale e non ci sono altri caratteri nella stringa
     * 3) Non è possibile proseguire e ci sono altri caratteri nella stringa
     *
     * A seconda dei risultati, viene visualizzata una Alert Box contenente la sequenza di operazione svolte.
     * @param simulateText La stringa che si vuole testare.
     * @return
     */
    public Message Simulate(String simulateText) {
        Message resultText = new Message();
        List<String> sequence = new ArrayList<String>();
        Node selectedNode = allNode.get(0);
        sequence.add(allNode.get(0).getNome());
        List<String> weigthSequence = new ArrayList<String>();
        int length = 0;
        int index = 0;
        int tempIndex = 0;
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
                        tempNode = allArch.get(i).receiverNode;
                        length = allArch.get(i).getWeigth().length();
                        arcoTrovato = i;
                    }
                }
            }
            if (tempNode == null) {
                System.out.println("Non è possibile proseguire");
                resultText.setMessage("Non è possibile proseguire");
                resultText.setSequence(sequence,weigthSequence);
                return resultText;
            } else {
                System.out.println("Nodo successivo trovato!");
                index = index + allArch.get(arcoTrovato).getWeigth().length();
                selectedNode = tempNode;
                sequence.add(selectedNode.getNome());
                weigthSequence.add(allArch.get(arcoTrovato).getWeigth());
                tempNode = null;
                if (index == simulateText.length()) {
                    if (selectedNode.isEnd) {
                        System.out.println("Arrivati alla fine");
                        resultText.setMessage("Arrivati alla fine");
                    } else {
                        System.out.println("Arrivati alla fine, ma non si è in un nodo fine");
                        resultText.setMessage("Arrivati alla fine, ma non si è in un nodo fine");
                    }
                    resultText.setSequence(sequence,weigthSequence);
                    return resultText;
                }
            }
        }
    }
    public String toString()
    {
        StringBuilder stringaNodi= new StringBuilder();
        Node tempNode;
        //stringaNodi.append("0 [label = q0 ,shape = circle ]\n");
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

    /**
     * Funzione che permette di convertire il grafo in un immagine.
     * L'immagine è in formato PNG e si trova nella cartella temporanea creata dalla classe FileManager
     */
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

    /**
     * Permette di aggiungere un nodo al grafo. All'interno del metodo vengono effettuati tutti i controlli per stabilire se è possibile inserire il nodo o no
     * Nel caso in cui esiste già un arco con nome nodoDaAggiungere, l'operazione ha esito negativo
     * L'id del nodo viene generato casualmente
     * @param nodoDaAggiungere Il nome del nodo da aggiungere
     * @param isEnd Se il nodo è terminale (True) o no (False)
     * @return True se l'operazione è andata a buon fine, false altrimenti.
     */
    public Boolean addNode(String nodoDaAggiungere, Boolean isEnd) {
        if (!nodoDaAggiungere.matches("[a-zA-Z0-9]+")) {
            //System.out.println("Contiene caratteri diversi da lettere e numeri");
            ShowAlert("Contiene caratteri diversi da lettere e numeri");
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
           // System.out.println("C'è già un nodo con quel nome");
            ShowAlert("C'è già un nodo con quel nome");
            return false;
        }
        else
        {
            Node tmp = new Node(nodoDaAggiungere);
            tmp.setIsEnd(false);
            Random random = new Random();
            Boolean isDuplicate=true;
            String randomNumber = "";
            while(isDuplicate)
            {
                randomNumber = String.valueOf(random.nextInt(1,99999));
                isDuplicate=false;
                for(int i=0;i<allNode.size() && (!isDuplicate);i++)
                {
                    if(allNode.get(i).getId().equals(randomNumber))
                    {
                        isDuplicate = true;
                    }
                }
            }
            tmp.setId(randomNumber);
            tmp.setIsEnd(isEnd);
            allNode.add(tmp);
            return true;
        }


    }

    /**
     * Permette di modificare un nodo esistente in Automa. Nel caso in cui esiste già un arco con nome nuovoNome, l'operazione ha esito negativo
     * @param nodoDaModificare Il nome del nodo da modificare
     * @param nuovoNome Il nuovo nome del nodo da modificare
     * @param isEnd Se il nodo è terminale o no. Nel caso in cui non si voglia modificare bisogna inserire il valore corrente
     * @return True se l'operazione ha avuto successo, false altrimenti
     */
    public Boolean ModifyNode(String nodoDaModificare, String nuovoNome, Boolean isEnd )
    {
        Boolean trovato = false;
        for(int i=0;i<allNode.size() && !trovato;i++)
        {
            if(nodoDaModificare.equals(allNode.get(i).getNome()))
                trovato=true;
        }
        if(!trovato)
        {
            ShowAlert("Non esiste il nodo da modificare");
            return false;
        }
        for(int i=0;i<allNode.size();i++)
        {
            if(allNode.get(i).getNome().equals(nuovoNome) && !(nodoDaModificare.equals(nuovoNome))){
                ShowAlert("C'è già un nodo con quel nome");
                return false;
            }
        }
        for(int i=0;i<allNode.size();i++)
        {
            if(nodoDaModificare.equals(allNode.get(i).getNome()) && nuovoNome.matches("[a-zA-Z0-9]+"))
            {

                allNode.get(i).setNome(nuovoNome);
                allNode.get(i).setIsEnd(isEnd);
                return true;
            }
        }
        ShowAlert("Nome non valido");
        return false;
    }

    /**
     * Elimina il nodo con il nome passato da paramentro.
     * Se si è trovato il nodo da eliminare, vengono eliminati anche gli archi che hanno o come origine o come destinazione il nodo cancellato
     * @param nodoDaEleminare Nome del nodo da eliminare
     * @return True se l'operazione ha avuto successo, false altrimenti
     */
    public Boolean DeleteNode(String nodoDaEleminare)
    {
        if(nodoDaEleminare.isEmpty()) {
            ShowAlert("Specificare nodo da eliminare");
            return false;
        }
        Node nodoDaEleminareNode = null;
        boolean trovato = false;
        for(int i=0;i<allNode.size();i++)
        {
            if(nodoDaEleminare.equals(allNode.get(i).getNome()))
            {
                if(Objects.equals(allNode.get(i).getId(), "0"))
                {
                    ShowAlert("Non puoi eliminare il nodo iniziale");
                    return false;
                }
                trovato = true;
                nodoDaEleminareNode = allNode.remove(i);
            }
        }
        if(!trovato)
        {
            ShowAlert("Non è stato trovato il nodo specificato");
            return false;
        }

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

    /**
     * Aggiunge un arco al grafo.
     * Il metodo controlla se esiste già un arco che parte da nomePartenza e arriva a nomeArrivo e, se esistono archi che hanno lo stesso peso dell'arco  da inserire
     * e il nodo di partenza dell'arco trovato è uguale al nodo di partenza di nomePartenza.
     * Il peso dell'arco può avere solo valori alfanumerici (A-Z, a-z, 0-9)
     * @param nomePartenza Nome del nodo di partenza del nuovo arco
     * @param nomeArrivo Nome del nodo di arrivo del nodo di partenza
     * @param peso Il peso dell'arco
     * @return True se l'operazione ha avuto successo, false altrimenti
     */
    public Boolean AggiungiArco(String nomePartenza, String nomeArrivo, String peso)
    {
        Arch arcoTest = null;
        if(!peso.matches("[a-zA-Z0-9]+"))
        {
            ShowAlert("Peso dell'arco può contenere solo caratteri alfanumerici");
            return false;
        }
        for(int i=0;i<allArch.size();i++)
        {
            arcoTest = allArch.get(i);
            if(arcoTest.getSenderNode().getNome().equals(nomePartenza)&&arcoTest.getReceiverNode().getNome().equals(nomeArrivo)) {
                //System.out.println("Esiste già quell'arco");
                ShowAlert("Esiste già quell'arco");
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
            //System.out.println("Uno dei nodi non esiste");
            ShowAlert("Uno dei nodi non esiste");
            return false;
        }
        else
        {
            //controlla se c'è già un arco con lo stesso peso
            for (Arch arch : allArch) {
                if (arch.getSenderNode() == startNode && arch.getWeigth().equals(peso)) {
                    ShowAlert("Esiste già un arco con il peso inserito");
                    return false;
                }
            }
            arcoTest = new Arch(startNode,endNode,peso);
            allArch.add(arcoTest);
            return true;
        }
    }

    /**
     * Metodo per modificare il peso di un arco esistente.
     * Il metodo controlla se esistono archi che hanno lo stesso peso dell'arco  da inserire
     *      * e il nodo di partenza dell'arco trovato è uguale al nodo di partenza di nomePartenza.
     *      * Il peso dell'arco può avere solo valori alfanumerici (A-Z, a-z, 0-9)
     * @param nomePartenza
     * @param nomeArrivo
     * @param peso
     * @return True se l'operazione ha avuto successo, false altrimenti
     */
    public Boolean ModificaArco(String nomePartenza, String nomeArrivo, String peso)
    {

        if(!peso.matches("[a-zA-Z0-9]+"))
        {
            ShowAlert("Peso dell'arco può contenere solo caratteri alfanumerici");
            return false;
        }

        for (Arch arch : allArch) {
            if (arch.getSenderNode().getNome().equals(nomePartenza) && arch.weigth.equals(peso)) {
                ShowAlert("Esiste già arco con quel peso");
                return false;
            }
        }
        for (Arch arch : allArch) {
            if (arch.getSenderNode().getNome().equals(nomePartenza) && arch.getReceiverNode().getNome().equals(nomeArrivo)) {
                arch.setWeigth(peso);
                return true;
            }
        }
        ShowAlert("Non è stato trovato l'arco specificato");
        return false;
    }

    /**
     * Metodo per eliminare l'arco che unisce il nodo con nome nomePartenza con il nodo con nome nomeArrivo
     * @param nomePartenza
     * @param nomeArrivo
     * @return True se l'operazione ha avuto successo, false altrimenti
     */
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
        ShowAlert("Non è stato trovato l'arco specificato");
        return false;
    }
    private void ShowAlert(String message)
    {
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

}
