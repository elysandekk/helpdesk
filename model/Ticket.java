package br.com.helpdesk.model;

import br.com.helpdesk.estruturas.ListaInteracoes;

public class Ticket {
    private final int id;
    private final String documentoCliente;
    private final String assunto;
    private StatusTicket status;
    private final ListaInteracoes interacoes;

    public Ticket(int id, String documentoCliente, String assunto) {
        this.id = id;
        this.documentoCliente = documentoCliente;
        this.assunto = assunto;
        this.status = StatusTicket.AGUARDANDO;
        this.interacoes = new ListaInteracoes();
        this.interacoes.adicionar("Sistema", "Ticket aberto e inserido na fila de espera.");
    }

    public int getId() { return id; }
    public String getDocumentoCliente() { return documentoCliente; }
    public String getAssunto() { return assunto; }
    public StatusTicket getStatus() { return status; }
    public ListaInteracoes getInteracoes() { return interacoes; }
    public void setStatus(StatusTicket status) { this.status = status; }

    @Override
    public String toString() {
        return "#" + id + " | Documento: " + documentoCliente
                + " | Assunto: " + assunto + " | Status: " + status;
    }
}
