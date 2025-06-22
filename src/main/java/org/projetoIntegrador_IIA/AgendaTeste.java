package org.projetoIntegrador_IIA;

import java.util.ArrayList;
import java.util.Scanner;

public class AgendaTeste {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Contato contato = new Contato();
        AgendaTelefonica agendaTelefonica = new AgendaTelefonica();

        ArrayList<Contato> agenda = agendaTelefonica.listarContatos();

        while (true) {
            mostrarMenu();
            int opcao;

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1:
                    Contato novo = new Contato();
                    System.out.print("Nome: ");
                    novo.setNome(sc.nextLine());
                    System.out.print("Telefone: ");
                    novo.setTelefone(sc.nextLine());
                    System.out.print("Email: ");
                    novo.setEmail(sc.nextLine());

                    agendaTelefonica.adicionarContato(novo);
                    System.out.println("Contato adicionado com sucesso!");
                    break;
                case 2:
                    Contato remover = new Contato();
                    System.out.print("Digite o nome do contato a ser removido: ");
                    String entradaRemocao = sc.nextLine();
                    remover.setNome(entradaRemocao);

                    agendaTelefonica.removerContato(remover);
                    System.out.println("Contato removido (se existente).");
                    break;
                case 3:

                    Contato busca = new Contato();
                    System.out.print("Digite o nome do contato a buscar: ");
                    String entradaBusca = sc.nextLine();
                    busca.setNome(entradaBusca);

                    agendaTelefonica.buscarContato(busca);
                    if (busca.getTelefone() != null){
                        System.out.println("Resultado da busca:");
                        System.out.println("ID: " + busca.getIdcon());
                        System.out.println("Nome: " + busca.getNome());
                        System.out.println("Telefone: " + busca.getTelefone());
                        System.out.println("Email: " + busca.getEmail());
                    }

                    break;
                case 4:
                    ArrayList<Contato> contatos = agendaTelefonica.listarContatos();

                    if (contatos == null || contatos.isEmpty()) {
                        System.out.println("Nenhum contato cadastrado ainda.");
                    } else {
                        System.out.println("---------------------------");
                        System.out.println("Contatos cadastrados:");
                        for (Contato c : contatos) {
                            System.out.println("ID: " + c.getIdcon());
                            System.out.println("Nome: " + c.getNome());
                            System.out.println("Telefone: " + c.getTelefone());
                            System.out.println("Email: " + c.getEmail());
                            System.out.println("---------------------------");
                        }
                    }
                    break;

                case 5:
                    System.out.println("Saindo...");
                    sc.close();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }

        }

    }

    public static void mostrarMenu() {
        System.out.println("\nMenu da Agenda de Contatos:");
        System.out.println("[1] Adicionar Contato");
        System.out.println("[2] Remover Contato");
        System.out.println("[3] Buscar Contato");
        System.out.println("[4] Listar todos os contatos");
        System.out.println("[5] Sair");
        System.out.print("Escolha uma opção: ");
    }
}