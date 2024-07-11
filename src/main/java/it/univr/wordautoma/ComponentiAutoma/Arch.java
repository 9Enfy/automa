package it.univr.wordautoma.ComponentiAutoma;

/**
 * Classe che rappresenta un singolo arco.
 */
public class Arch {
    Node senderNode;
    Node receiverNode;
    String weigth;

    /**
     *
     * @param SN Il nodo di partenza
     * @param RN Il nodo di arrivo
     * @param w Peso dell'arco
     */
    public Arch(Node SN,Node RN, String w)
    {
        senderNode = SN;
        receiverNode=RN;
        weigth = w;
    }

    /**
     *
     * @return In nodo di partenza
     */
    public Node getSenderNode() {
        return senderNode;
    }

    /**
     *
     * @return Il nodo di arrivo
     */
    public Node getReceiverNode() {
        return receiverNode;
    }

    /**
     *
     * @return Il peso del nodo (String)
     */
    public String getWeigth() {
        return weigth;
    }

    /**
     *
     * @param SN Il nodo di partenza
     */
    public void setSenderNode(Node SN)
    {
        senderNode= SN;
    }

    /**
     *
     * @param RN Il nodo di arrivo
     */
    public void setReceiverNode(Node RN)
    {
        receiverNode = RN;
    }

    /**
     *
     * @param w Il peso
     */
    public void setWeigth(String w)
    {
        weigth = w;
    }
}
