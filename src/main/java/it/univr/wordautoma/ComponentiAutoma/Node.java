package it.univr.wordautoma.ComponentiAutoma;

/**
 * Classe che rappresenta un singolo nodo.
 * Ogni nodo ha un nome (il nome che l'utente vede), un id (una stringa utile per identificare un nodo univocamente) e se è un nodo terminale o no.
 * Per implementazione in altre classi l'id è un numero che va da 0 a 99999.
 * L'id 0 è riservato per il nodo iniziale.
 */
public class Node {
    String nome;
    String id;
    Boolean isEnd;

    /**
     *
     * @param _nome Il nome del nodo. ID e se è un nodo terminale devono esssere settati con i metodi set
     */
    public Node(String _nome)
    {
        nome = _nome;
    }

    /**
     *
     * @return Il nome del nodo (String)
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @param id L'id del nodo
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @param nome Il nome del nodo
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @param _isEnd Se è un nodo terminale (true) o se non lo è (false)
     */
    public void setIsEnd(Boolean _isEnd)
    {
        isEnd= _isEnd;
    }

    /**
     *
     * @return Se è un nodo terminale (true) o se non lo è (false)
     */
    public Boolean getIsEnd()
    {
        return isEnd;
    }

    /**
     *
     * @return L'Id del nodo (String)
     */
    public String getId() {
        return id;
    }
}
