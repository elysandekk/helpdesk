package br.com.helpdesk.estruturas;

import br.com.helpdesk.model.Cliente;

public class ArvoreClientes {
    private static class No {
        Cliente cliente;
        No esquerda;
        No direita;

        No(Cliente cliente) {
            this.cliente = cliente;
        }
    }

    private No raiz;
    private int quantidade;

    public boolean inserir(Cliente cliente) {
        if (cliente == null) return false;
        if (raiz == null) {
            raiz = new No(cliente);
            quantidade++;
            return true;
        }

        No atual = raiz;
        while (true) {
            int comparacao = cliente.getDocumento().compareTo(atual.cliente.getDocumento());
            if (comparacao == 0) return false;
            if (comparacao < 0) {
                if (atual.esquerda == null) {
                    atual.esquerda = new No(cliente);
                    quantidade++;
                    return true;
                }
                atual = atual.esquerda;
            } else {
                if (atual.direita == null) {
                    atual.direita = new No(cliente);
                    quantidade++;
                    return true;
                }
                atual = atual.direita;
            }
        }
    }

    public Cliente buscar(String documento) {
        if (documento == null) return null;
        No atual = raiz;
        while (atual != null) {
            int comparacao = documento.compareTo(atual.cliente.getDocumento());
            if (comparacao == 0) return atual.cliente;
            atual = comparacao < 0 ? atual.esquerda : atual.direita;
        }
        return null;
    }

    public boolean remover(String documento) {
        if (buscar(documento) == null) return false;
        raiz = removerRecursivo(raiz, documento);
        quantidade--;
        return true;
    }

    private No removerRecursivo(No no, String documento) {
        if (no == null) return null;
        int comparacao = documento.compareTo(no.cliente.getDocumento());
        if (comparacao < 0) {
            no.esquerda = removerRecursivo(no.esquerda, documento);
        } else if (comparacao > 0) {
            no.direita = removerRecursivo(no.direita, documento);
        } else {
            if (no.esquerda == null) return no.direita;
            if (no.direita == null) return no.esquerda;

            No sucessor = menorNo(no.direita);
            no.cliente = sucessor.cliente;
            no.direita = removerRecursivo(no.direita, sucessor.cliente.getDocumento());
        }
        return no;
    }

    private No menorNo(No no) {
        while (no.esquerda != null) no = no.esquerda;
        return no;
    }

    public int getQuantidade() { return quantidade; }

    public void listarEmOrdem() {
        if (raiz == null) System.out.println("(arvore vazia)");
        emOrdem(raiz);
    }
    private void emOrdem(No no) {
        if (no == null) return;
        emOrdem(no.esquerda);
        System.out.println(no.cliente);
        emOrdem(no.direita);
    }

    public void listarPreOrdem() {
        if (raiz == null) System.out.println("(arvore vazia)");
        preOrdem(raiz);
    }
    private void preOrdem(No no) {
        if (no == null) return;
        System.out.println(no.cliente);
        preOrdem(no.esquerda);
        preOrdem(no.direita);
    }

    public void listarPosOrdem() {
        if (raiz == null) System.out.println("(arvore vazia)");
        posOrdem(raiz);
    }
    private void posOrdem(No no) {
        if (no == null) return;
        posOrdem(no.esquerda);
        posOrdem(no.direita);
        System.out.println(no.cliente);
    }

    public void percursos() {
        System.out.println("\n--- EM ORDEM (esquerda, raiz, direita) ---");
        listarEmOrdem();
        System.out.println("\n--- PRE-ORDEM (raiz, esquerda, direita) ---");
        listarPreOrdem();
        System.out.println("\n--- POS-ORDEM (esquerda, direita, raiz) ---");
        listarPosOrdem();
    }

}
