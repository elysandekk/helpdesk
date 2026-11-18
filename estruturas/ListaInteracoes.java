package br.com.helpdesk.estruturas;

import br.com.helpdesk.model.Interacao;

public class ListaInteracoes {
    private static class No {
        Interacao interacao;
        No proximo;

        No(Interacao interacao) {
            this.interacao = interacao;
        }
    }

    private No inicio;
    private No fim;
    private int tamanho;
    private int proximoId = 1;

    public Interacao adicionar(String autor, String mensagem) {
        if (autor == null || autor.trim().isEmpty()
                || mensagem == null || mensagem.trim().isEmpty()) {
            throw new IllegalArgumentException("Autor e mensagem devem ser informados.");
        }
        Interacao interacao = new Interacao(proximoId++, autor.trim(), mensagem.trim());
        No novo = new No(interacao);
        if (inicio == null) {
            inicio = novo;
            fim = novo;
        } else {
            fim.proximo = novo;
            fim = novo;
        }
        tamanho++;
        return interacao;
    }

    public Interacao buscar(int id) {
        No atual = inicio;
        while (atual != null) {
            if (atual.interacao.getId() == id) return atual.interacao;
            atual = atual.proximo;
        }
        return null;
    }

    public boolean remover(int id) {
        No anterior = null;
        No atual = inicio;
        while (atual != null) {
            if (atual.interacao.getId() == id) {
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

    public void listar() {
        if (inicio == null) {
            System.out.println("(nenhuma interacao)");
            return;
        }
        No atual = inicio;
        while (atual != null) {
            System.out.println(atual.interacao);
            atual = atual.proximo;
        }
    }

    public int getTamanho() { return tamanho; }

}
