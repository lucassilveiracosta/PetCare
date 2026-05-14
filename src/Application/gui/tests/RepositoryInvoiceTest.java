package gui.tests;

import business.model.Pessoas.Dono;
import business.model.Pessoas.Especialidade;
import business.model.Pessoas.Veterinario;
import business.model.animal.Animal;
import business.model.animal.AnimalDomestico;
import business.model.animal.Vacina;
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
            for (NotaFiscal nf : repository.findAll()) {
                System.out.println("      - NotaFiscal ID: " + nf.getId() + " | Pagador: " + nf.getDono().getNome() + " | Qtd Procedimentos: " + nf.getProcedimentos().size() + " | Qtd Produtos: " + nf.getProdutos().size());
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Iniciando Teste Interativo do RepositoryInvoice ===");

        // Setup - Banco de Dados
        ArrayList<NotaFiscal> bancoDeDados = new ArrayList<>();
        IRepositoryInvoice repository = new RepositoryInvoice(bancoDeDados);

        // Setup - Criando dados robustos
        LocalDate data = LocalDate.now();
        LocalDateTime dataHora = LocalDateTime.now();


        ArrayList<Especialidade> especialidades1 = new ArrayList<>();
        Veterinario veterinario = new Veterinario("Jorge", "jorge@gmail.com", "jorgecookies", data.minusYears(30), "12345678900", "8199999999", "Testado", especialidades1);
        Dono dono = new Dono("João Silva","joao@gmail.com", "12341234", data.minusYears(30), "12345678900", "81999999999", "Professor", "Dono dedicado");
        Dono dono1 = new Dono("Lucas Costa","lucas@gmail.com", "12341234", data.minusYears(30), "12345678900", "81999999999", "Professor", "Dono dedicado");
        Dono dono2 = new Dono("Laercio Carlos","laercio@gmail.com", "12341234", data.minusYears(30), "12345678900", "81999999999", "Professor", "Dono dedicado");
        Dono dono3 = new Dono("Vinicius Carlos","laercio@gmail.com", "12341234", data.minusYears(30), "12345678900", "81999999999", "Professor", "Dono dedicado");

        Animal animal1 = new AnimalDomestico("Rex", "Vira-lata", "Marrom", data.minusYears(3), FaseDaVida.ADULTO, 15.0, Porte.MEDIO, Sexo.MACHO, dono, new ArrayList<Vacina>(), Temperamento.DOCIL, true);
        Animal animal2 = new AnimalDomestico("Mia", "Siamês", "Branco", data.minusYears(1), FaseDaVida.RECEMNASCIDO, 4.0, Porte.PEQUENO, Sexo.FEMEA, dono1, new ArrayList<Vacina>(), Temperamento.DOCIL, false);
        Animal animal3 = new AnimalDomestico("Bob", "Siamês", "Branco", data.minusYears(1), FaseDaVida.RECEMNASCIDO, 4.0, Porte.PEQUENO, Sexo.FEMEA, dono2, new ArrayList<Vacina>(), Temperamento.DOCIL, false);

        ArrayList<Procedimento> procedimentos1 = new ArrayList<>();
        procedimentos1.add(new Consulta(150.0, animal1, dataHora, "Consulta de Rotina", veterinario,"Teste", "O animal está doente"));
        procedimentos1.add(new Consulta(80.0, animal1, dataHora, "Vacinação Anual", veterinario, "Teste", "O animal está doente"));
        ArrayList<Produto> produtos1 = new ArrayList<>();
        produtos1.add(new Produto(dataHora, "Ração Premium 15kg", 200.0));
        produtos1.add(new Produto(dataHora, "Brinquedo de Borracha", 35.0));

        ArrayList<Procedimento> procedimentos2 = new ArrayList<>();
        procedimentos2.add(new Cirurgia(300.0, animal2, dataHora, "Exame de Sangue", veterinario, "anestesia Geral", "Alto risco"));

        ArrayList<Produto> produtos2 = new ArrayList<>();
        produtos2.add(new Produto(dataHora, "Antibiótico Pet", 85.0));

        NotaFiscal nf1 = new NotaFiscal(dono, animal1, procedimentos1, produtos1);
        NotaFiscal nf2 = new NotaFiscal(dono1, animal2, procedimentos2, produtos2);
        NotaFiscal nf3 = new NotaFiscal(dono2, animal3, procedimentos2, produtos2);

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
                    ArrayList<Procedimento> novosProc = new ArrayList<>();
                    novosProc.add(new Consulta(200.0, animal1, dataHora, "Limpeza de Tártaro", veterinario, "Diagnostico", "Testado" ));
                    ArrayList<Produto> novosProd = new ArrayList<>();
                    novosProd.add(new Produto(dataHora, "Shampoo Pet", 45.0));
                    NotaFiscal novaNf = new NotaFiscal(dono3, animal3, novosProc, novosProd);
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
                        NotaFiscal encontrada = repository.findById(idBusca);
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

                        NotaFiscal nfOriginal = repository.findById(id);

                        if (nfOriginal != null) {
                            System.out.println("   -> Atualizando Nota Fiscal ID " + nfOriginal.getId() + " mudando o pagador para Maria Oliveira...");

                            NotaFiscal nfAtualizada = new NotaFiscal(dono, nfOriginal.getPaciente(), nfOriginal.getProcedimentos(), nfOriginal.getProdutos());

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
                        NotaFiscal nfParaRemover = repository.findById(idRemover);
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