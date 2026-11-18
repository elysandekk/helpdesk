package br.com.helpdesk.estruturas;

import br.com.helpdesk.model.Ticket;

public class FilaTickets {
    private static class No {
        Ticket ticket;
        No proximo;

        No(Ticket ticket) {
            this.ticket = ticket;
        }
    }

    private No inicio;
    private No fim;
    private int tamanho;

    public void adicionar(Ticket ticket) {
        if (ticket == null) throw new IllegalArgumentException("Ticket invalido.");
        No novo = new No(ticket);
        if (fim == null) {
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

    public Ticket retirarProximo() {
        if (inicio == null) return null;
        No removido = inicio;
        inicio = inicio.proximo;
        if (inicio == null) fim = null;
        removido.proximo = null;
        tamanho--;
        return removido.ticket;
    }

    public Ticket remover(int id) {
        No anterior = null;
        No atual = inicio;
        while (atual != null) {
            if (atual.ticket.getId() == id) {
                if (anterior == null) inicio = atual.proximo;
                else anterior.proximo = atual.proximo;
                if (atual == fim) fim = anterior;
                atual.proximo = null;
                tamanho--;
                return atual.ticket;
            }
            anterior = atual;
            atual = atual.proximo;
        }
        return null;
    }

    public void listar() {
        if (inicio == null) {
            System.out.println("(fila vazia)");
            return;
        }
        No atual = inicio;
        int posicao = 1;
        while (atual != null) {
            System.out.println("Posicao " + posicao + ": " + atual.ticket);
            atual = atual.proximo;
            posicao++;
        }
    }

    public int getTamanho() { return tamanho; }
    public boolean estaVazia() { return inicio == null; }

}
