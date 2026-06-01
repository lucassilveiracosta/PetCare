/*
package gui.tests;

import business.controller.ControllerPetCareServer;
import business.interfaces.*;
import business.model.person.Owner;
import business.model.person.Person;
import business.model.person.Veterinarian;
import business.model.animal.*;
import business.model.invoice.*;
import business.model.appointment.*;
import enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ControllerPetCareServerTest {

    public static void main(String[] args) {
        System.out.println("=== Inicializando o Servidor Central PetCare ===");
        ControllerPetCareServer server = ControllerPetCareServer.getInstance();

        popularDados(server);

        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=============================================");
            System.out.println("  PAINEL DE TESTES - PETCARE SERVER (CRUD)   ");
            System.out.println("=============================================");
            System.out.println("1. Gerenciar person (CRUD)");
            System.out.println("2. Gerenciar Animais (CRUD)");
            System.out.println("3. Gerenciar Estoque/Produtos (CRUD)");
            System.out.println("4. Listar todas as Faturas (Invoices)");
            System.out.println("0. Sair");
            System.out.print("Escolha um módulo: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Entrada inválida.");
                continue;
            }

            switch (opcao) {
                case 1:
                    menuPessoas(server.getPessoa(), scanner);
                    break;
                case 2:
                    menuAnimais(server.getAnimal(), scanner);
                    break;
                case 3:
                    menuEstoque(server.getStock(), scanner);
                    break;
                case 4:
                    listarInvoices(server.getInvoice());
                    break;
                case 0:
                    System.out.println("Encerrando testes...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }

    private static void popularDados(ControllerPetCareServer server) {
        System.out.println("-> Populando o banco de dados do servidor com dados iniciais...");
        try {
            // person
            Owner dono1 = new Owner("João Silva", "joao@email.com", "senha123", LocalDate.of(1985, 5, 20), "11122233344", "99999-1111", "Professor", "Adora cães");
            Owner dono2 = new Owner("Maria Souza", "maria@email.com", "senha123", LocalDate.of(1992, 8, 15), "22233344455", "99999-2222", "Engenheira", "Adora gatos");
            Veterinarian vet = new Veterinarian("Dr. Carlos", "carlos@vet.com", "veter123", LocalDate.of(1980, 1, 10), "55544433322", "99999-3333", "CRMV-999", new ArrayList<>());
            
            server.getPessoa().post(dono1);
            server.getPessoa().post(dono2);
            server.getPessoa().post(vet);

            // Animais
            Animal rex = new DomesticAnimal("Rex", "Pastor Alemão", "Capa Preta", LocalDate.now().minusYears(3), StageOfLife.ADULTO, 30.5, Size.GRANDE, Sex.MACHO, dono1, Temperament.DOCIL, true,  new ArrayList<>());
            Animal mimi = new DomesticAnimal("Mimi", "Siamês", "Branco", LocalDate.now().minusYears(1), StageOfLife.ADULTO, 4.2, Size.PEQUENO, Sex.FEMEA, dono2, Temperament.DOCIL, false, new ArrayList<>());
            
            server.getAnimal().post(rex);
            server.getAnimal().post(mimi);

            // Estoque
            Product prod1 = new Product("Ração DogSprint", 10, "Ração Premier 15kg", 250.0);
            Product prod2 = new Product("Influenza f876", 50, "Vacina V10", 80.0);
            Product prod3 = new Product("Clear Man CR7", 30, "Shampoo Antipulgas", 45.0);

            server.getStock().post(prod1);
            server.getStock().post(prod2);
            server.getStock().post(prod3);

            // Invoices (Faturas)
            ArrayList<Procedure> procedimentos = new ArrayList<>();
            Anamnesis anamnese = new Anamnesis("Vômito", "Ração", "N/A");
            VitalParameters vitais = new VitalParameters(80, 20, 38.5, Mucosa.NORMACORADAS, 2, null, "Normal");
            PhysicalExamination fisico = new PhysicalExamination(Conscience.ALERTA, vitais, "Abdômen sensível");
            
            Appointment consulta = new Appointment(150.0, rex, LocalDateTime.now(), "Consulta Clínica", vet, "Gastrite", "Tratamento iniciado", anamnese, fisico);
            procedimentos.add(consulta);

            ArrayList<Product> prodsFatura = new ArrayList<>();
            prodsFatura.add(prod3); // Comprou 1 shampoo

            Invoice fatura1 = new Invoice(dono1, rex, procedimentos, prodsFatura);
            server.getInvoice().post(fatura1);

            System.out.println("-> Dados populados com sucesso!\n");
        } catch (Exception e) {
            System.err.println("-> Erro ao popular dados: " + e.getMessage());
        }
    }

    // ==========================================================
    // MENUS CRUD SECUNDÁRIOS
    // ==========================================================

    private static void menuPessoas(IControllerPerson controller, Scanner scanner) {
        while (true) {
            System.out.println("\n--- CRUD PESSOAS ---");
            System.out.println("1. Listar person");
            System.out.println("2. Buscar Pessoa por ID");
            System.out.println("3. Remover Pessoa por ID");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            String op = scanner.nextLine();

            if (op.equals("0")) break;
            
            switch (op) {
                case "1":
                    List<Person> pessoas = controller.getAll();
                    System.out.println("\nLista de person:");
                    for (Person p : pessoas) {
                        System.out.println("ID: " + p.getId() + " | Nome: " + p.getName() + " | Email: " + p.getEmail());
                    }
                    break;
                case "2":
                    System.out.print("ID: ");
                    try {
                        Person p = controller.getById(Integer.parseInt(scanner.nextLine()));
                        if (p != null) System.out.println("Encontrado: " + p.getName() + " (" + p.getEmail() + ")");
                        else System.out.println("Não encontrado.");
                    } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
                    break;
                case "3":
                    System.out.print("ID a remover: ");
                    try {
                        controller.delete(Integer.parseInt(scanner.nextLine()));
                        System.out.println("Removido com sucesso (se existia).");
                    } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuAnimais(IControllerAnimal controller, Scanner scanner) {
        while (true) {
            System.out.println("\n--- CRUD ANIMAIS ---");
            System.out.println("1. Listar Animais");
            System.out.println("2. Buscar Animal por ID");
            System.out.println("3. Remover Animal por ID");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            String op = scanner.nextLine();

            if (op.equals("0")) break;
            
            switch (op) {
                case "1":
                    List<Animal> animais = controller.getAll();
                    System.out.println("\nLista de Animais:");
                    for (Animal a : animais) {
                        System.out.println("ID: " + a.getId() + " | Nome: " + a.getName() + " | Raça: " + a.getRace());
                    }
                    break;
                case "2":
                    System.out.print("ID: ");
                    try {
                        Animal a = controller.getById(Integer.parseInt(scanner.nextLine()));
                        if (a != null) System.out.println("Encontrado: " + a.getName() + " - " + a.getSpecies());
                        else System.out.println("Não encontrado.");
                    } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
                    break;
                case "3":
                    System.out.print("ID a remover: ");
                    try {
                        controller.delete(Integer.parseInt(scanner.nextLine()));
                        System.out.println("Removido com sucesso (se existia).");
                    } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuEstoque(IControllerStock controller, Scanner scanner) {
        while (true) {
            System.out.println("\n--- CRUD ESTOQUE ---");
            System.out.println("1. Listar Produtos");
            System.out.println("2. Buscar Produto por ID");
            System.out.println("3. Remover Produto por ID");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            String op = scanner.nextLine();

            if (op.equals("0")) break;
            
            switch (op) {
                case "1":
                    List<Product> produtos = controller.getAll();
                    System.out.println("\nLista de Produtos:");
                    for (Product p : produtos) {
                        System.out.println("ID: " + p.getId() + " | Nome: " + p.getName() + " | Preço: R$" + p.getPrice() + " | Qtd: " + p.getQuantity());
                    }
                    break;
                case "2":
                    System.out.print("ID: ");
                    try {
                        Product p = controller.getById(Integer.parseInt(scanner.nextLine()));
                        if (p != null) System.out.println("Encontrado: " + p.getName() + " - R$" + p.getPrice());
                        else System.out.println("Não encontrado.");
                    } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
                    break;
                case "3":
                    System.out.print("ID a remover: ");
                    try {
                        controller.delete(Integer.parseInt(scanner.nextLine()));
                        System.out.println("Removido com sucesso (se existia).");
                    } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void listarInvoices(IControllerInvoice controller) {
        List<Invoice> faturas = controller.getAll();
        System.out.println("\n--- TODAS AS FATURAS ---");
        if (faturas.isEmpty()) {
            System.out.println("Nenhuma fatura registrada.");
        } else {
            for (Invoice nf : faturas) {
                System.out.println("Fatura ID: " + nf.getId() + " | Pagador: " + nf.getOwner().getName() +
                                   " | Procedimentos: " + nf.getProcedures().size() + 
                                   " | Produtos: " + nf.getProducts().size());
            }
        }
    }
}
*/