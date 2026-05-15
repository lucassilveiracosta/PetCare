package gui.tests;

import business.model.Pessoas.Owner;
import business.model.Pessoas.Specialty;
import business.model.Pessoas.Veterinarian;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.animal.Vaccine;
import business.model.notaFiscal.*;
import data.interfaces.IRepositoryInvoice;
import data.repository.RepositoryInvoice;
import enums.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class RepositoryInvoiceTest {

    public static void listarNotasFiscais(IRepositoryInvoice repository) {
        System.out.println("\n   -> Estado atual do repositório:");
        if (repository.findAll().isEmpty()) {
            System.out.println("      (Repositório vazio)");
        } else {
            for (Invoice nf : repository.findAll()) {
                System.out.println("      - NotaFiscal ID: " + nf.getId() + " | Pagador: " + nf.getDono().getNome() + " | Qtd Procedimentos: " + nf.getProcedimentos().size() + " | Qtd Produtos: " + nf.getProdutos().size());
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Iniciando Teste Interativo do RepositoryInvoice ===");

        // Setup - Banco de Dados
        ArrayList<Invoice> bancoDeDados = new ArrayList<>();
        IRepositoryInvoice repository = new RepositoryInvoice(bancoDeDados);

        // Setup - Criando dados robustos
        LocalDate data = LocalDate.now();
        LocalDateTime dataHora = LocalDateTime.now();


        ArrayList<Specialty> especialidades1 = new ArrayList<>();
        Veterinarian veterinario = new Veterinarian("Jorge", "jorge@gmail.com", "jorgecookies", data.minusYears(30), "12345678900", "8199999999", "Testado", especialidades1);
        Owner dono = new Owner("João Silva","joao@gmail.com", "12341234", data.minusYears(30), "12345678900", "81999999999", "Professor", "Dono dedicado");
        Owner dono1 = new Owner("Lucas Costa","lucas@gmail.com", "12341234", data.minusYears(30), "12345678900", "81999999999", "Professor", "Dono dedicado");
        Owner dono2 = new Owner("Laercio Carlos","laercio@gmail.com", "12341234", data.minusYears(30), "12345678900", "81999999999", "Professor", "Dono dedicado");
        Owner dono3 = new Owner("Vinicius Carlos","laercio@gmail.com", "12341234", data.minusYears(30), "12345678900", "81999999999", "Professor", "Dono dedicado");

        Animal animal1 = new DomesticAnimal("Rex", "Vira-lata", "Marrom", data.minusYears(3), FaseDaVida.ADULTO, 15.0, Porte.MEDIO, Sexo.MACHO, dono, new ArrayList<Vaccine>(), Temperamento.DOCIL, true);
        Animal animal2 = new DomesticAnimal("Mia", "Siamês", "Branco", data.minusYears(1), FaseDaVida.RECEMNASCIDO, 4.0, Porte.PEQUENO, Sexo.FEMEA, dono1, new ArrayList<Vaccine>(), Temperamento.DOCIL, false);
        Animal animal3 = new DomesticAnimal("Bob", "Siamês", "Branco", data.minusYears(1), FaseDaVida.RECEMNASCIDO, 4.0, Porte.PEQUENO, Sexo.FEMEA, dono2, new ArrayList<Vaccine>(), Temperamento.DOCIL, false);

        ArrayList<Procedure> procedimentos1 = new ArrayList<>();
        procedimentos1.add(new Appointment(150.0, animal1, dataHora, "Consulta de Rotina", veterinario,"Teste", "O animal está doente"));
        procedimentos1.add(new Appointment(80.0, animal1, dataHora, "Vacinação Anual", veterinario, "Teste", "O animal está doente"));
        ArrayList<Product> produtos1 = new ArrayList<>();
        produtos1.add(new Product(dataHora, "Ração Premium 15kg", 200.0));
        produtos1.add(new Product(dataHora, "Brinquedo de Borracha", 35.0));

        ArrayList<Procedure> procedimentos2 = new ArrayList<>();
        procedimentos2.add(new Surgery(300.0, animal2, dataHora, "Exame de Sangue", veterinario, "anestesia Geral", "Alto risco"));

        ArrayList<Product> produtos2 = new ArrayList<>();
        produtos2.add(new Product(dataHora, "Antibiótico Pet", 85.0));

        Invoice nf1 = new Invoice(dono, animal1, procedimentos1, produtos1);
        Invoice nf2 = new Invoice(dono1, animal2, procedimentos2, produtos2);
        Invoice nf3 = new Invoice(dono2, animal3, procedimentos2, produtos2);

        // Pre-populando para ter dados
        repository.create(nf1);
        repository.create(nf2);
        repository.create(nf3);

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=================================");
            System.out.println(" MENU - TESTE REPOSITÓRIO INVOICE");
            System.out.println("=================================");
            System.out.println("1. Adicionar nova Nota Fiscal (Dummy)");
            System.out.println("2. Listar todas as Notas Fiscais");
            System.out.println("3. Buscar Nota Fiscal por ID");
            System.out.println("4. Atualizar Nota Fiscal por Index");
            System.out.println("5. Remover Nota Fiscal por ID");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Digite um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.println("\n[AÇÃO] Adicionando uma nova Nota Fiscal...");
                    ArrayList<Procedure> novosProc = new ArrayList<>();
                    novosProc.add(new Appointment(200.0, animal1, dataHora, "Limpeza de Tártaro", veterinario, "Diagnostico", "Testado" ));
                    ArrayList<Product> novosProd = new ArrayList<>();
                    novosProd.add(new Product(dataHora, "Shampoo Pet", 45.0));
                    Invoice novaNf = new Invoice(dono3, animal3, novosProc, novosProd);
                    repository.create(novaNf);
                    System.out.println("Nota Fiscal criada e adicionada com sucesso!");
                    listarNotasFiscais(repository);
                    break;

                case 2:
                    System.out.println("\n[AÇÃO] Listando todas as Notas Fiscais...");
                    listarNotasFiscais(repository);
                    break;

                case 3:
                    System.out.print("\n[AÇÃO] Digite o ID da Nota Fiscal que deseja buscar: ");
                    try {
                        int idBusca = Integer.parseInt(scanner.nextLine());
                        Invoice encontrada = repository.findById(idBusca);
                        if (encontrada != null) {
                            System.out.println("   -> Nota Fiscal encontrada! ID: " + encontrada.getId() + " | Pagador: " + encontrada.getDono().getNome());
                            System.out.println("      Procedimentos: " + encontrada.getProcedimentos().size() + " | Produtos: " + encontrada.getProdutos().size());
                        } else {
                            System.out.println("   -> Nenhuma Nota Fiscal encontrada com o ID " + idBusca);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("   -> ID inválido.");
                    }
                    listarNotasFiscais(repository);
                    break;

                case 4:
                    System.out.print("\n[AÇÃO] Digite o ID da Nota Fiscal a ser atualizada: ");
                    try {
                        int id = Integer.parseInt(scanner.nextLine());

                        Invoice nfOriginal = repository.findById(id);

                        if (nfOriginal != null) {
                            System.out.println("   -> Atualizando Nota Fiscal ID " + nfOriginal.getId() + " mudando o pagador para Maria Oliveira...");

                            Invoice nfAtualizada = new Invoice(dono, nfOriginal.getPaciente(), nfOriginal.getProcedimentos(), nfOriginal.getProdutos());

                            int index = repository.findAll().indexOf(nfOriginal);

                            if (index != -1) {
                                repository.update(index, nfAtualizada);
                                System.out.println("   -> Atualização concluída com sucesso.");
                            } else {
                                System.out.println("   -> Erro interno: Nota Fiscal encontrada, mas não está mapeada na lista.");
                            }
                        } else {
                            System.out.println("   -> Nenhuma Nota Fiscal encontrada com o ID " + id + ".");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("   -> ID inválido. Por favor, digite um número inteiro.");
                    }
                    listarNotasFiscais(repository);
                    break;

                case 5:
                    System.out.print("\n[AÇÃO] Digite o ID da Nota Fiscal que deseja remover: ");
                    try {
                        int idRemover = Integer.parseInt(scanner.nextLine());
                        Invoice nfParaRemover = repository.findById(idRemover);
                        if (nfParaRemover != null) {
                            repository.remove(nfParaRemover);
                            System.out.println("   -> Nota Fiscal removida com sucesso!");
                        } else {
                            System.out.println("   -> Nenhuma Nota Fiscal encontrada com o ID " + idRemover + " para remoção.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("   -> ID inválido.");
                    }
                    listarNotasFiscais(repository);
                    break;

                case 0:
                    System.out.println("Saindo do teste interativo...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }

        scanner.close();
    }
}