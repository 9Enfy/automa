package it.univr.wordautoma.ComponentiAutoma;

public class Node {
    String nome;
    String id;
    Boolean isEnd;

    public Node(String _nome)
    {
        nome = _nome;
    }

    public String getNome() {
        return nome;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setIsEnd(Boolean _isEnd)
    {
        isEnd= _isEnd;
    }
    public Boolean getIsEnd()
    {
        return isEnd;
    }

    public String getId() {
        return id;
    }
}
