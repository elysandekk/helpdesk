package br.com.helpdesk;

import java.util.Scanner;
import br.com.helpdesk.model.Cliente;
import br.com.helpdesk.model.Ticket;
import br.com.helpdesk.model.Interacao;
import br.com.helpdesk.model.StatusTicket;
import br.com.helpdesk.service.SistemaHelpDesk;

public class Main {
    private static final Scanner entrada = new Scanner(System.in);
    private static final SistemaHelpDesk sistema = new SistemaHelpDesk();

    public static void main(String[] args) {
        while (true) {
            menu();
            try {
                int opcao = numero("Opcao: ");
                if (opcao == 0) break;
                executar(opcao);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            System.out.println();
        }
        System.out.println("Sistema encerrado.");
        entrada.close();
    }

    private static void menu() {
        System.out.println("\n======================================== HELP DESK ========================================");
        System.out.println("CLIENTES (ABB)");
        System.out.println(" 1 - Cadastrar     2 - Buscar");
        System.out.println(" 3 - Remover       4 - Percursos");
        System.out.println("TICKETS (FILA)");
        System.out.println(" 5 - Abrir         6 - Listar fila");
        System.out.println(" 7 - Buscar        8 - Cancelar");
        System.out.println("ATENDIMENTO");
        System.out.println(" 9 - Chamar proximo  10 - Ver atual");
        System.out.println("11 - Finalizar");
        System.out.println("INTERACOES (LISTA ENCADEADA)");
        System.out.println("12 - Adicionar    13 - Buscar");
        System.out.println("14 - Remover      15 - Listar");
        System.out.println("HISTORICO");
        System.out.println("16 - Tickets do cliente");
        System.out.println(" 0 - Sair");
        System.out.println("============================================================================================");
    }

    private static String texto(String pergunta) {
        System.out.print(pergunta);
        return entrada.nextLine().trim();
    }

    private static int numero(String pergunta) {
        try {
            return Integer.parseInt(texto(pergunta));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Digite um numero valido.");
        }
    }

    private static void executar(int opcao) {
        switch (opcao) {
            case 1 -> {
                boolean ok = sistema.cadastrarCliente(texto("CPF/CNPJ: "), texto("Nome: "));
                System.out.println(ok ? "Cliente cadastrado." : "CPF/CNPJ ja cadastrado.");
            }
            case 2 -> {
                Cliente cliente = sistema.buscarCliente(texto("CPF/CNPJ: "));
                System.out.println(cliente == null ? "Cliente nao encontrado." : cliente);
            }
            case 3 -> {
                boolean ok = sistema.removerCliente(texto("CPF/CNPJ: "));
                System.out.println(ok ? "Cliente removido." : "Cliente nao encontrado.");
            }
            case 4 -> sistema.getClientes().percursos();
            case 5 -> {
                Ticket ticket = sistema.abrirTicket(texto("CPF/CNPJ: "), texto("Assunto: "));
                System.out.println(ticket == null ? "Cliente nao encontrado." : "Aberto: " + ticket);
            }
            case 6 -> sistema.getFila().listar();
            case 7 -> {
                Ticket ticket = sistema.getFila().buscar(numero("ID do ticket: "));
                System.out.println(ticket == null ? "Ticket nao encontrado na fila." : ticket);
            }
            case 8 -> {
                boolean ok = sistema.cancelarTicket(numero("ID do ticket: "));
                System.out.println(ok ? "Ticket cancelado." : "Ticket nao encontrado na fila.");
            }
            case 9 -> {
                Ticket ticket = sistema.iniciarProximoAtendimento(texto("Nome do tecnico: "));
                System.out.println(ticket == null ? "Fila vazia ou ja existe atendimento." : "Atendendo: " + ticket);
            }
            case 10 -> {
                Ticket ticket = sistema.getEmAtendimento();
                System.out.println(ticket == null ? "Nenhum ticket em atendimento." : ticket);
            }
            case 11 -> System.out.println(sistema.finalizarAtendimento()
                    ? "Atendimento finalizado." : "Nenhum ticket em atendimento.");
            case 12 -> {
                Ticket ticket = selecionarTicket();
                if (ticket != null) {
                    if (ticket.getStatus() == StatusTicket.FINALIZADO) {
                        System.out.println("Ticket finalizado.");
                    } else {
                        Interacao interacao = ticket.getInteracoes().adicionar(texto("Autor: "), texto("Mensagem: "));
                        System.out.println("Adicionada: " + interacao);
                    }
                }
            }
            case 13 -> {
                Ticket ticket = selecionarTicket();
                if (ticket != null) {
                    Interacao interacao = ticket.getInteracoes().buscar(numero("ID da interacao: "));
                    System.out.println(interacao == null ? "Interacao nao encontrada." : interacao);
                }
            }
            case 14 -> {
                Ticket ticket = selecionarTicket();
                if (ticket != null) {
                    if (ticket.getStatus() == StatusTicket.FINALIZADO) {
                        System.out.println("Ticket finalizado.");
                    } else {
                        boolean ok = ticket.getInteracoes().remover(numero("ID da interacao: "));
                        System.out.println(ok ? "Interacao removida." : "Interacao nao encontrada.");
                    }
                }
            }
            case 15 -> {
                Ticket ticket = selecionarTicket();
                if (ticket != null) ticket.getInteracoes().listar();
            }
            case 16 -> {
                Cliente cliente = sistema.buscarCliente(texto("CPF/CNPJ: "));
                if (cliente == null) System.out.println("Cliente nao encontrado.");
                else cliente.getTickets().listar();
            }
            default -> System.out.println("Opcao invalida.");
        }
    }

    private static Ticket selecionarTicket() {
        Ticket ticket = sistema.buscarTicket(texto("CPF/CNPJ: "), numero("ID do ticket: "));
        if (ticket == null) System.out.println("Ticket nao encontrado para este cliente.");
        return ticket;
    }
}
