package tests;

import enums.*;
import model.Pessoas.Dono;
import model.Pessoas.ResponsavelPagador;
import model.animal.Animal;
import model.animal.AnimalDomestico;
import model.animal.Vacina;
import model.notaFiscal.NotaFiscal;
import model.notaFiscal.Procedimento;
import model.notaFiscal.Produto;
import repository.RepositoryInvoice;
import repository.Interface.IRepositoryInvoice;

import java.time.LocalDate;
import java.util.ArrayList;

public class RepositoryInvoiceTest {

    public static void listarNotasFiscais(IRepositoryInvoice repository) {
        System.out.println("   -> Estado atual do repositório:");
        if (repository.findAll().isEmpty()) {
            System.out.println("      (Repositório vazio)");
        } else {
            for (NotaFiscal nf : repository.findAll()) {
                System.out.println("      - NotaFiscal ID: " + nf.getId() + " | Pagador: " + nf.getResponsavelPagador().getNome());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Iniciando Testes do RepositoryInvoice ===");

        // 1. Setup - Criando objetos dummy necessários para a NotaFiscal
        ArrayList<NotaFiscal> bancoDeDados = new ArrayList<>();
        IRepositoryInvoice repository = new RepositoryInvoice(bancoDeDados);

        LocalDate data = LocalDate.now();
        ResponsavelPagador pagador = new ResponsavelPagador("João Silva", data.minusYears(30), "12345678900", "81999999999", "Professor", "Descrição Pagador");
        Dono dono = new Dono("João Silva", data.minusYears(30), "12345678900", "81999999999", "Professor", "Dono do animal");
        Animal animal = new AnimalDomestico("Rex", "Vira-lata", "Marrom", data.minusYears(3), 15.0, Porte.MEDIO, Sexo.MACHO, dono, new ArrayList<Vacina>(), Temperamento.DOCIL, true, TempoDeVida.ADULTO);

        NotaFiscal nf1 = new NotaFiscal(pagador, animal, new ArrayList<Procedimento>(), new ArrayList<Produto>());
        NotaFiscal nf2 = new NotaFiscal(pagador, animal, new ArrayList<Procedimento>(), new ArrayList<Produto>());

        // 2. Testando CREATE
        System.out.println("\n[TESTE] Adicionando Notas Fiscais...");
        repository.create(nf1);
        repository.create(nf2);
        listarNotasFiscais(repository);

        // 3. Testando FIND ALL
        System.out.println("\n[TESTE] Listando todas as Notas Fiscais...");
        listarNotasFiscais(repository);

        // 4. Testando FIND BY ID
        System.out.println("\n[TESTE] Buscando Nota Fiscal por ID...");
        int idBusca = 4;
        NotaFiscal encontrada = repository.findById(idBusca);
        if (encontrada != null) {
            System.out.println("Nota Fiscal encontrada com sucesso! ID: " + encontrada.getId());
        } else {
            System.out.println("Nota Fiscal não encontrada.");
        }
        listarNotasFiscais(repository);

        // 5. Testando UPDATE
        System.out.println("\n[TESTE] Atualizando Nota Fiscal...");
        ResponsavelPagador pagadorNovo = new ResponsavelPagador("Maria Souza", data.minusYears(25), "09876543211", "81888888888", "Médica", "Nova Pagadora");
        NotaFiscal nfAtualizada = new NotaFiscal(pagadorNovo, animal, new ArrayList<Procedimento>(), new ArrayList<Produto>());
        // O método update no repositório utiliza o index da lista, então vamos atualizar o index 0
        repository.update(0, nfAtualizada);
        listarNotasFiscais(repository);

        // 6. Testando REMOVE
        System.out.println("\n[TESTE] Removendo Nota Fiscal...");
        repository.remove(nfAtualizada);
        listarNotasFiscais(repository);

        System.out.println("\n=== Fim dos Testes do RepositoryInvoice ===");
    }
}
