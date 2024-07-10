package it.univr.wordautoma.ComponentiAutoma;

public class Arch {
    Node senderNode;
    Node receiverNode;
    String weigth;

    public Arch(Node SN,Node RN, String w)
    {
        senderNode = SN;
        receiverNode=RN;
        weigth = w;
    }

    public Node getSenderNode() {
        return senderNode;
    }

    public Node getReceiverNode() {
        return receiverNode;
    }

    public String getWeigth() {
        return weigth;
    }
    public void setSenderNode(Node SN)
    {
        senderNode= SN;
    }
    public void setReceiverNode(Node RN)
    {
        receiverNode = RN;
    }
    public void setWeigth(String w)
    {
        weigth = w;
    }
}
