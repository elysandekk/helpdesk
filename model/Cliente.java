package br.com.helpdesk.model;

import br.com.helpdesk.estruturas.ListaTickets;

public class Cliente {
    private final String documento;
    private final String nome;
    private final ListaTickets tickets;

    public Cliente(String documento, String nome) {
        this.documento = documento;
        this.nome = nome;
        this.tickets = new ListaTickets();
    }

    public String getDocumento() { return documento; }
    public String getNome() { return nome; }
    public ListaTickets getTickets() { return tickets; }

    @Override
    public String toString() {
        return nome + " | CPF/CNPJ: " + documento + " | Tickets: " + tickets.getTamanho();
    }
}
