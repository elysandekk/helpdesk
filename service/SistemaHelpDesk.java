package br.com.helpdesk.service;

import br.com.helpdesk.model.Cliente;
import br.com.helpdesk.model.Ticket;
import br.com.helpdesk.model.StatusTicket;
import br.com.helpdesk.estruturas.ArvoreClientes;
import br.com.helpdesk.estruturas.FilaTickets;

public class SistemaHelpDesk {
    private final ArvoreClientes clientes = new ArvoreClientes();
    private final FilaTickets fila = new FilaTickets();
    private int proximoIdTicket = 1;
    private Ticket emAtendimento;

    public static String normalizarDocumento(String documento) {
        if (documento == null) return null;
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < documento.length(); i++) {
            char c = documento.charAt(i);
            if (c >= '0' && c <= '9') {
                resultado.append(c);
            } else if (c != '.' && c != '-' && c != '/' && c != ' ') {
                return null;
            }
        }
        if (resultado.length() != 11 && resultado.length() != 14) return null;
        return resultado.toString();
    }

    private String documentoObrigatorio(String documento) {
        String normalizado = normalizarDocumento(documento);
        if (normalizado == null) {
            throw new IllegalArgumentException("Informe CPF (11 digitos) ou CNPJ (14 digitos).");
        }
        return normalizado;
    }

    private void textoObrigatorio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo " + campo + " nao pode ficar vazio.");
        }
    }

    public boolean cadastrarCliente(String documento, String nome) {
        String doc = documentoObrigatorio(documento);
        textoObrigatorio(nome, "nome");
        return clientes.inserir(new Cliente(doc, nome.trim()));
    }

    public Cliente buscarCliente(String documento) {
        return clientes.buscar(documentoObrigatorio(documento));
    }

    public boolean removerCliente(String documento) {
        String doc = documentoObrigatorio(documento);
        Cliente cliente = clientes.buscar(doc);
        if (cliente == null) return false;
        if (cliente.getTickets().possuiTicketNaoFinalizado()) {
            throw new IllegalStateException(
                    "Cliente possui ticket pendente. Finalize ou cancele antes de excluir.");
        }
        return clientes.remover(doc);
    }

    public Ticket abrirTicket(String documento, String assunto) {
        String doc = documentoObrigatorio(documento);
        textoObrigatorio(assunto, "assunto");
        Cliente cliente = clientes.buscar(doc);
        if (cliente == null) return null;

        Ticket ticket = new Ticket(proximoIdTicket++, doc, assunto.trim());
        cliente.getTickets().adicionar(ticket);
        fila.adicionar(ticket);
        return ticket;
    }

    public Ticket buscarTicket(String documento, int id) {
        Cliente cliente = buscarCliente(documento);
        return cliente == null ? null : cliente.getTickets().buscar(id);
    }

    public boolean cancelarTicket(int id) {
        Ticket retirado = fila.remover(id);
        if (retirado == null) return false;
        Cliente cliente = clientes.buscar(retirado.getDocumentoCliente());

        cliente.getTickets().remover(id);
        return true;
    }

    public Ticket iniciarProximoAtendimento(String tecnico) {
        textoObrigatorio(tecnico, "nome do tecnico");
        if (emAtendimento != null) return null;
        Ticket ticket = fila.retirarProximo();
        if (ticket == null) return null;
        ticket.setStatus(StatusTicket.EM_ATENDIMENTO);
        ticket.getInteracoes().adicionar(tecnico.trim(), "Atendimento iniciado.");
        emAtendimento = ticket;
        return ticket;
    }

    public boolean finalizarAtendimento() {
        if (emAtendimento == null) return false;
        emAtendimento.getInteracoes().adicionar("Sistema", "Atendimento finalizado.");
        emAtendimento.setStatus(StatusTicket.FINALIZADO);
        emAtendimento = null;
        return true;
    }

    public ArvoreClientes getClientes() { return clientes; }
    public FilaTickets getFila() { return fila; }
    public Ticket getEmAtendimento() { return emAtendimento; }
}
