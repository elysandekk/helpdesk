package br.com.helpdesk.estruturas;

import br.com.helpdesk.model.Ticket;
import br.com.helpdesk.model.StatusTicket;

public class ListaTickets {
    private static class No {
        Ticket ticket;
        No proximo;

        No(Ticket ticket) { this.ticket = ticket; }
    }

    private No inicio;
    private No fim;
    private int tamanho;

    public void adicionar(Ticket ticket) {
        if (ticket == null) throw new IllegalArgumentException("Ticket invalido.");
        No novo = new No(ticket);
        if (inicio == null) {
            inicio = novo;
            fim = novo;
        } else {
            fim.proximo = novo;
            fim = novo;
        }
        tamanho++;
    }

    public Ticket buscar(int id) {
        No atual = inicio;
        while (atual != null) {
            if (atual.ticket.getId() == id) return atual.ticket;
            atual = atual.proximo;
        }
        return null;
    }

    public boolean remover(int id) {
        No anterior = null;
        No atual = inicio;
        while (atual != null) {
            if (atual.ticket.getId() == id) {
                if (anterior == null) inicio = atual.proximo;
                else anterior.proximo = atual.proximo;
                if (atual == fim) fim = anterior;
                atual.proximo = null;
                tamanho--;
                return true;
            }
            anterior = atual;
            atual = atual.proximo;
        }
        return false;
    }

    public boolean possuiTicketNaoFinalizado() {
        No atual = inicio;
        while (atual != null) {
            if (atual.ticket.getStatus() != StatusTicket.FINALIZADO) return true;
            atual = atual.proximo;
        }
        return false;
    }

    public void listar() {
        if (inicio == null) {
            System.out.println("(cliente sem tickets)");
            return;
        }
        No atual = inicio;
        while (atual != null) {
            System.out.println(atual.ticket);
            atual = atual.proximo;
        }
    }

    public int getTamanho() { return tamanho; }

}
