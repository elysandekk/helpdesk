package br.com.helpdesk.model;

public class Interacao {
    private final int id;
    private final String autor;
    private final String mensagem;

    public Interacao(int id, String autor, String mensagem) {
        this.id = id;
        this.autor = autor;
        this.mensagem = mensagem;
    }

    public int getId() { return id; }
    public String getMensagem() { return mensagem; }

    @Override
    public String toString() {
        return "#" + id + " | " + autor + ": " + mensagem;
    }
}
