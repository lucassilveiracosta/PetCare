package gui.tests;

import business.controller.*;
import business.model.Pessoas.*;
import business.model.animal.*;
import business.model.notaFiscal.*;
import business.model.prontuario.*;
import data.repository.*;
import enums.*;
import exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Teste End-to-End do sistema PetCare.
 *
 * Cobre REQ01 a REQ10 conforme implementado na branch dev.
 * Cada seção corresponde a um ou mais requisitos do README.
 * Bugs conhecidos sao documentados inline com comentario [BUG].
 */
public class TestE2E {

    private static int passed = 0;
    private static int failed = 0;

    // =========================================================================
    //  MAIN
    // =========================================================================
    public static void main(String[] args) {
        line('=');
        System.out.println("  PETCARE - SUITE DE TESTES E2E (REQ01 a REQ10)");
        line('=');

        // ── Repositórios ──────────────────────────────────────────────────────
        RepositoryAnimal          repoAnimal       = new RepositoryAnimal();
        RepositorioPessoa         repoPessoa       = new RepositorioPessoa(new ArrayList<>());
        RepositoryAppointment     repoConsulta     = new RepositoryAppointment(new ArrayList<>());
        RepositoryInvoice         repoInvoice      = new RepositoryInvoice(new ArrayList<>());
        RepositoryStock repoStockGeral = new RepositoryStock(new ArrayList<>());
        RepositoryStock    repoStockVet   = new RepositoryStock(new ArrayList<>());

        // ── Controllers ───────────────────────────────────────────────────────
        ControllerAnimal               ctrlAnimal    = new ControllerAnimal(repoAnimal);
        ControllerPessoa               ctrlPessoa    = new ControllerPessoa(repoPessoa);
        ControllerAppointment          ctrlConsulta  = new ControllerAppointment(repoConsulta);
        ControllerInvoice              ctrlInvoice   = new ControllerInvoice(repoInvoice);
        ControllerStock ctrlStockGeral = new ControllerStock(repoStockGeral);
        ControllerStock     ctrlStockVet   = new ControllerStock(repoStockVet);

        // ── Dados compartilhados (criados uma única vez) ──────────────────────
        LocalDate hoje = LocalDate.now();
        LocalDateTime agora = LocalDateTime.now();

        Dono dono = new Dono(
                "Carlos Silva", "carlos@petcare.com", "senha123",
                hoje.minusYears(35), "11122233344", "81999990000",
                "Engenheiro", "Dono responsável"
        );
        ArrayList<Especialidade> especialidades = new ArrayList<>();
        especialidades.add(new Especialidade("Clinica Geral", "Atendimento geral"));
        Veterinario vet = new Veterinario(
                "Dra. Ana Lima", "ana@petcare.com", "senha456",
                hoje.minusYears(40), "99988877700", "81888880000",
                "CRMV-PE-1234", especialidades
        );

        DomesticAnimal rex = new DomesticAnimal(
                "Rex", "Cachorro", "Labrador", hoje.minusYears(4),
                FaseDaVida.ADULTO, 30.0, Porte.GRANDE, Sexo.MACHO,
                dono, new ArrayList<>(), Temperamento.DOCIL, true
        );
        ExoticAnimal iguana = new ExoticAnimal(
                "Igu", "Iguana", "Verde", hoje.minusYears(2),
                FaseDaVida.ADULTO, 1.5, Porte.PEQUENO, Sexo.MACHO,
                "REG-001", "CHIP-001", true, "Herbivora",
                Origem.CATIVEIRO  // [BUG] MainE2ETest original usava Origem.SILVESTRE (nao existe no enum)
        );

        // =====================================================================
        //  REQ01 / REQ02 - Cadastro de Animais com Heranca
        // =====================================================================
        secao("REQ01/REQ02 - Cadastro de Animais e Heranca");

        testar("Cadastrar Dono no sistema", () ->
                ctrlPessoa.post(dono));

        testar("Cadastrar Veterinario no sistema", () ->
                ctrlPessoa.post(vet));

        testar("Cadastrar AnimalDomestico (Rex)", () ->
                ctrlAnimal.post(rex));

        testar("Cadastrar AnimalExotico (Iguana)", () ->
                ctrlAnimal.post(iguana));

        testar("Total de animais deve ser 2", () -> {
            int total = ctrlAnimal.getAll().size();
            assertEquals(2, total, "numero de animais");
        });

        testar("Buscar Rex por ID retorna animal correto", () -> {
            Animal encontrado = ctrlAnimal.getById(rex.getId());
            assertEquals("Rex", encontrado.getName(), "nome do animal");
        });

        testar("Rex e instancia de AnimalDomestico (heranca REQ02)", () -> {
            if (!(rex instanceof DomesticAnimal))
                throw new RuntimeException("Rex deveria ser AnimalDomestico");
        });

        testar("Iguana e instancia de AnimalExotico (heranca REQ02)", () -> {
            if (!(iguana instanceof ExoticAnimal))
                throw new RuntimeException("Iguana deveria ser AnimalExotico");
        });

        testar("Ambos estendem Animal (polimorfismo)", () -> {
            if (!(rex instanceof Animal) || !(iguana instanceof Animal))
                throw new RuntimeException("Todos deveriam ser Animal");
        });

        // ─── Validacoes do modelo ─────────────────────────────────────────────
        testarExcecao("Nome nulo em Animal lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new DomesticAnimal(null, "Cachorro", "SRD", hoje.minusYears(1),
                                FaseDaVida.ADULTO, 10.0, Porte.MEDIO, Sexo.FEMEA,
                                dono, new ArrayList<>(), Temperamento.DOCIL, false));

        testarExcecao("Peso zero em Animal lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new DomesticAnimal("Luna", "Cachorro", "SRD", hoje.minusYears(1),
                                FaseDaVida.ADULTO, 0.0, Porte.MEDIO, Sexo.FEMEA,
                                dono, new ArrayList<>(), Temperamento.DOCIL, false));

        testarExcecao("Porte null em Animal lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new DomesticAnimal("Bidu", "Cachorro", "SRD", hoje.minusYears(1),
                                FaseDaVida.ADULTO, 5.0, null, Sexo.MACHO,
                                dono, new ArrayList<>(), Temperamento.DOCIL, false));

        testarExcecao("Sexo null em Animal lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new DomesticAnimal("Bidu", "Cachorro", "SRD", hoje.minusYears(1),
                                FaseDaVida.ADULTO, 5.0, Porte.PEQUENO, null,
                                dono, new ArrayList<>(), Temperamento.DOCIL, false));

        testarExcecao("Dono null em AnimalDomestico lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new DomesticAnimal("Bidu", "Cachorro", "SRD", hoje.minusYears(1),
                                FaseDaVida.ADULTO, 5.0, Porte.PEQUENO, Sexo.MACHO,
                                null, new ArrayList<>(), Temperamento.DOCIL, false));

        testarExcecao("Numero de registro nulo em AnimalExotico lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new ExoticAnimal("Cobra", "Cobra", "Jiboia", hoje.minusYears(1),
                                FaseDaVida.ADULTO, 5.0, Porte.MEDIO, Sexo.MACHO,
                                null, "CHIP-999", false, "Carnivora", Origem.CATIVEIRO));

        // ─── Erros de CRUD de Animal ──────────────────────────────────────────
        testarExcecao("Conflito: cadastrar Rex novamente (AnimalConflictException)",
                AnimalConflictException.class, () ->
                        ctrlAnimal.post(rex));

        testarExcecao("Nao encontrado: ID 99999 (AnimalNotFoundException)",
                AnimalNotFoundException.class, () ->
                        ctrlAnimal.getById(99999));

        testarExcecao("ID negativo lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        ctrlAnimal.getById(-1));

        testarExcecao("Animal null lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        ctrlAnimal.post(null));

        // =====================================================================
        //  REQ06 - Registro de Vacinas
        // =====================================================================
        secao("REQ06 - Registro de Vacinas");

        testar("Adicionar vacina antirabica a Rex", () -> {
            Vaccine vacinaRabica = new Vaccine("Antirabica", hoje.minusMonths(3), "Dose anual obrigatoria");
            rex.getVaccines().add(vacinaRabica);
            assertEquals(1, rex.getVaccines().size(), "quantidade de vacinas");
        });

        testar("Adicionar vacina V10 a Rex", () -> {
            Vaccine v10 = new Vaccine("V10", hoje.minusMonths(6), "Multipla anual");
            rex.getVaccines().add(v10);
            assertEquals(2, rex.getVaccines().size(), "quantidade de vacinas apos segunda");
        });

        testar("Verificar nome da primeira vacina registrada", () -> {
            String nome = rex.getVaccines().get(0).getNomeDaVacina();
            assertEquals("Antirabica", nome, "nome da vacina");
        });

        testar("Verificar data de vacina e alerta de reforco (12 meses)", () -> {
            LocalDate dataVacina = rex.getVaccines().get(0).getDataDaVacina();
            LocalDate reforco = dataVacina.plusMonths(12);
            if (reforco.isBefore(hoje))
                throw new RuntimeException("Vacina vencida! Reforco deveria ter sido em " + reforco);
        });

        // =====================================================================
        //  REQ03 - Prontuario Medico (Composicao)
        // =====================================================================
        secao("REQ03 - Prontuario Medico");

        Prontuario prontuarioRex;
        {
            Hidratacao hidratacao = new Hidratacao(true, null);
            ParametrosVitais parametros = new ParametrosVitais(
                    80, 20, 38.5, Mucosa.NORMACORADAS, 5, hidratacao, "Parametros normais"
            );
            ExameFisico exame = new ExameFisico(Consciencia.ALERTA, parametros, "Animal responsivo e alerta");
            Anamnese anamnese = new Anamnese("Sem apetite", "Sem restricao", "Consulta de rotina");
            IdaAoVeterinario ida = new IdaAoVeterinario(hoje, exame, anamnese, "Primeira consulta do ano");
            ArrayList<IdaAoVeterinario> idas = new ArrayList<>();
            idas.add(ida);
            prontuarioRex = new Prontuario(idas, "Prontuario do Rex", rex);
        }

        testar("Criar prontuario com IdaAoVeterinario completa", () -> {
            assertEquals(1, prontuarioRex.getIdasAoVeterinario().size(), "idas ao vet");
        });

        testar("Prontuario vinculado ao animal correto", () -> {
            assertEquals("Rex", prontuarioRex.getAnimal().getName(), "animal no prontuario");
        });

        testar("Adicionar segunda consulta ao prontuario", () -> {
            Hidratacao h2 = new Hidratacao(false, 5.0);
            ParametrosVitais pv2 = new ParametrosVitais(95, 28, 39.2, Mucosa.PALIDAS, 4, h2, "Leve desidratacao");
            ExameFisico ex2 = new ExameFisico(Consciencia.APATICO, pv2, "Animal apático, desidratacao leve");
            Anamnese an2 = new Anamnese("Vomitos frequentes", "Dieta leve recomendada", "Retorno apos tratamento");
            IdaAoVeterinario ida2 = new IdaAoVeterinario(hoje.plusDays(7), ex2, an2, "Retorno em 7 dias");
            prontuarioRex.getIdasAoVeterinario().add(ida2);
            assertEquals(2, prontuarioRex.getIdasAoVeterinario().size(), "idas ao vet apos retorno");
        });

        testarExcecao("Anamnese com queixa nula lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Anamnese(null, "Sem restricao", "descricao"));

        testarExcecao("Anamnese com restricao nula lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Anamnese("Dor", null, "descricao"));

        testarExcecao("ExameFisico sem nivel de consciencia lanca IllegalArgumentException",
                IllegalArgumentException.class, () -> {
                    Hidratacao h = new Hidratacao(true, null);
                    ParametrosVitais pv = new ParametrosVitais(80, 20, 38.5, Mucosa.NORMACORADAS, 5, h, "ok");
                    new ExameFisico(null, pv, "descricao");
                });

        testarExcecao("ExameFisico sem parametros vitais lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new ExameFisico(Consciencia.ALERTA, null, "descricao"));

        testarExcecao("IdaAoVeterinario sem ExameFisico lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new IdaAoVeterinario(hoje, null, new Anamnese("dor", "nenhuma", "desc"), "desc"));

        testarExcecao("IdaAoVeterinario sem Anamnese lanca IllegalArgumentException",
                IllegalArgumentException.class, () -> {
                    Hidratacao h = new Hidratacao(true, null);
                    ParametrosVitais pv = new ParametrosVitais(80, 20, 38.5, Mucosa.NORMACORADAS, 5, h, "ok");
                    ExameFisico ex = new ExameFisico(Consciencia.ALERTA, pv, "ok");
                    new IdaAoVeterinario(hoje, ex, null, "desc");
                });

        // =====================================================================
        //  REQ04 - Agendamento de Consultas
        // =====================================================================
        secao("REQ04 - Agendamento de Consultas");

        Appointment consulta1 = new Appointment(
                150.0, rex, agora.plusDays(1),
                "Consulta de Rotina", vet, "Animal saudavel", "Vitamina A"
        );
        Appointment consulta2 = new Appointment(
                200.0, iguana, agora.plusDays(3),
                "Avaliacao Reptiliana", vet, "Analise comportamental", "Nenhuma"
        );

        testar("Agendar consulta para Rex", () ->
                ctrlConsulta.post(consulta1));

        testar("Agendar consulta para Iguana", () ->
                ctrlConsulta.post(consulta2));

        testar("Total de consultas deve ser 2", () ->
                assertEquals(2, ctrlConsulta.getAll().size(), "consultas agendadas"));

        testar("Buscar consulta por ID retorna paciente correto", () -> {
            Appointment c = ctrlConsulta.getById(consulta1.getId());
            assertEquals("Rex", c.getPaciente().getName(), "paciente da consulta");
        });

        testar("Veterinario responsavel esta correto", () -> {
            Appointment c = ctrlConsulta.getById(consulta1.getId());
            assertEquals("Dra. Ana Lima", c.getVeterinarioResponsavel().getName(), "vet da consulta");
        });

        testarExcecao("Conflito: agendar mesma consulta (AppointmentConflictException)",
                AppointmentConflictException.class, () ->
                        ctrlConsulta.post(consulta1));

        testarExcecao("Nao encontrado: consulta ID 99999 (AppointmentNotFoundException)",
                AppointmentNotFoundException.class, () ->
                        ctrlConsulta.getById(99999));

        testarExcecao("Consulta null lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        ctrlConsulta.post(null));

        testarExcecao("ID negativo lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        ctrlConsulta.getById(-1));

        // ─── Update e delete de consulta ──────────────────────────────────────
        testar("Atualizar consulta via patch (novo valor e data)", () -> {
            Appointment consultaAtualizada = new Appointment(
                    175.0, rex, agora.plusDays(2),
                    "Consulta de Revisao", vet, "Revisao pos-tratamento", "Nenhuma"
            );
            ctrlConsulta.patch(consulta1.getId(), consultaAtualizada);
            // Verifica que a lista ainda tem 2 elementos apos update
            assertEquals(2, ctrlConsulta.getAll().size(), "consultas apos patch");
        });

        testar("Remover consulta da Iguana", () -> {
            ctrlConsulta.delete(consulta2.getId());
            assertEquals(1, ctrlConsulta.getAll().size(), "consultas apos delete");
        });

        testarExcecao("Deletar consulta inexistente (AppointmentNotFoundException)",
                AppointmentNotFoundException.class, () ->
                        ctrlConsulta.delete(99999));

        testarExcecao("Patch com ID negativo (IllegalArgumentException)",
                IllegalArgumentException.class, () ->
                        ctrlConsulta.patch(-1, consulta1));

        // ─── Validacoes do modelo Consulta ────────────────────────────────────
        testarExcecao("Veterinario null em Consulta lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Appointment(150.0, rex, agora.plusDays(1), "Desc", null, "Diag", "Presc"));

        testarExcecao("Diagnostico nulo em Consulta lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Appointment(150.0, rex, agora.plusDays(1), "Desc", vet, null, "Presc"));

        testarExcecao("Paciente null em Consulta lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Appointment(150.0, null, agora.plusDays(1), "Desc", vet, "Diag", "Presc"));

        // =====================================================================
        //  REQ08 - Cirurgia
        // =====================================================================
        secao("REQ08 - Procedimento Cirurgico");

        testar("Criar objeto Cirurgia vinculado ao veterinario", () -> {
            Surgery cirurgia = new Surgery(
                    800.0, rex, agora.plusDays(7),
                    "Castracao eletiva", vet, "Anestesia geral inalatoria", "Baixo"
            );
            assertEquals("Dra. Ana Lima", cirurgia.getVeterinarioResponsavel().getName(), "vet da cirurgia");
            assertEquals("Baixo", cirurgia.getRiscoCirurgico(), "risco cirurgico");
        });

        testar("Cirurgia e instancia de Procedimento (polimorfismo)", () -> {
            Surgery c = new Surgery(800.0, rex, agora.plusDays(7), "Desc", vet, "Geral", "Baixo");
            if (!(c instanceof Procedure))
                throw new RuntimeException("Cirurgia deveria ser Procedimento");
        });

        testarExcecao("Cirurgia sem veterinario lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Surgery(800.0, rex, agora.plusDays(7), "Desc", null, "Geral", "Baixo"));

        testarExcecao("Risco cirurgico nulo lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Surgery(800.0, rex, agora.plusDays(7), "Desc", vet, "Geral", null));

        testarExcecao("Tipo de anestesia nulo lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Surgery(800.0, rex, agora.plusDays(7), "Desc", vet, null, "Baixo"));

        testarExcecao("Paciente null em Cirurgia lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Surgery(800.0, null, agora.plusDays(7), "Desc", vet, "Geral", "Baixo"));

        // =====================================================================
        //  REQ09 / REQ10 - Estoque de Produtos
        // =====================================================================
        secao("REQ09/REQ10 - Estoque de Produtos (Geral e Veterinario)");

        Product racaoPremium = new Product(agora, "Racao Premium 15kg", 180.0);
        Product shampooAnt   = new Product(agora, "Shampoo Antisseptico", 28.0);
        Product antibiotico  = new Product(agora, "Amoxicilina Vet 500mg", 45.0);
        Product antifungico  = new Product(agora, "Antifungico Vet 250mg", 60.0);

        // -- Estoque Geral
        testar("Cadastrar Racao no estoque geral", () ->
                ctrlStockGeral.post(racaoPremium));

        testar("Cadastrar Shampoo no estoque geral", () ->
                ctrlStockGeral.post(shampooAnt));

        testar("Estoque geral deve ter 2 produtos", () ->
                assertEquals(2, ctrlStockGeral.getAll().size(), "produtos no estoque geral"));

        testar("Buscar produto geral por ID retorna produto correto", () -> {
            Product p = ctrlStockGeral.getById(racaoPremium.getId());
            assertEquals("Racao Premium 15kg", p.getDescricao(), "descricao do produto");
        });

        testarExcecao("Produto duplicado no estoque geral (StockGeneralConflictException)",
                StockGeneralProductsConflictException.class, () ->
                        ctrlStockGeral.post(racaoPremium));

        testarExcecao("Produto null no estoque geral (IllegalArgumentException)",
                IllegalArgumentException.class, () ->
                        ctrlStockGeral.post(null));

        testarExcecao("ID inexistente no estoque geral (StockGeneralNotFoundException)",
                StockGeneralProductsNotFoundException.class, () ->
                        ctrlStockGeral.getById(99999));

        testar("Atualizar produto no estoque geral (patch)", () -> {
            Product racaoAtualizada = new Product(agora, "Racao Premium 20kg", 220.0);
            ctrlStockGeral.put(racaoPremium.getId(), racaoAtualizada);
            assertEquals(2, ctrlStockGeral.getAll().size(), "tamanho do estoque apos patch");
        });

        testar("Remover Shampoo do estoque geral", () -> {
            ctrlStockGeral.delete(shampooAnt.getId());
            assertEquals(1, ctrlStockGeral.getAll().size(), "estoque geral apos delete");
        });

        testarExcecao("Deletar produto inexistente no estoque geral (NotFoundException)",
                StockGeneralProductsNotFoundException.class, () ->
                        ctrlStockGeral.delete(99999));

        testarExcecao("ID negativo no estoque geral (IllegalArgumentException)",
                IllegalArgumentException.class, () ->
                        ctrlStockGeral.getById(-5));

        // -- Estoque Veterinario
        testar("Cadastrar Antibiotico no estoque veterinario", () ->
                ctrlStockVet.post(antibiotico));

        testar("Cadastrar Antifungico no estoque veterinario", () ->
                ctrlStockVet.post(antifungico));

        testar("Estoque veterinario deve ter 2 produtos", () ->
                assertEquals(2, ctrlStockVet.getAll().size(), "produtos no estoque vet"));

        testar("Buscar medicamento por ID no estoque vet", () -> {
            Product p = ctrlStockVet.getById(antibiotico.getId());
            assertEquals("Amoxicilina Vet 500mg", p.getDescricao(), "descricao do medicamento");
        });

        testarExcecao("Produto duplicado no estoque vet (StockVetConflictException)",
                StockVetProductsConflictException.class, () ->
                        ctrlStockVet.post(antibiotico));

        testarExcecao("Produto null no estoque vet (IllegalArgumentException)",
                IllegalArgumentException.class, () ->
                        ctrlStockVet.post(null));

        testarExcecao("ID inexistente no estoque vet (StockVetNotFoundException)",
                StockVetProductsNotFoundException.class, () ->
                        ctrlStockVet.getById(99999));

        testar("Remover antifungico do estoque vet", () -> {
            ctrlStockVet.delete(antifungico.getId());
            assertEquals(1, ctrlStockVet.getAll().size(), "estoque vet apos delete");
        });

        testarExcecao("Deletar medicamento inexistente no vet (NotFoundException)",
                StockVetProductsNotFoundException.class, () ->
                        ctrlStockVet.delete(99999));

        // =====================================================================
        //  REQ09 / REQ11 - Nota Fiscal
        // =====================================================================
        secao("REQ09/REQ11 - Emissao de Nota Fiscal");

        Dono pagador = new Dono(
                "Carlos Silva", "pagador@petcare.com", "senha789",
                hoje.minusYears(35), "11122233344", "81999990001",
                "Engenheiro", "Responsavel financeiro"
        );

        testar("Emitir NF com Consulta e Produto", () -> {
            ArrayList<Procedure> procs = new ArrayList<>();
            procs.add(new Appointment(150.0, rex, agora, "Consulta Rotina", vet, "Saudavel", "Vitamina A"));

            ArrayList<Product> prods = new ArrayList<>();
            prods.add(new Product(agora, "Suplemento Vitaminico", 55.0));

            Invoice nf = new Invoice(pagador, rex, procs, prods);
            ctrlInvoice.post(nf);
        });

        testar("Emitir NF com Cirurgia (REQ08+REQ11)", () -> {
            Surgery cirurgia = new Surgery(
                    800.0, rex, agora.plusDays(7), "Castracao", vet, "Inalatoria", "Baixo"
            );
            ArrayList<Procedure> procs = new ArrayList<>();
            procs.add(cirurgia);

            Invoice nfCirurgia = new Invoice(pagador, rex, procs, new ArrayList<>());
            ctrlInvoice.post(nfCirurgia);
        });

        testar("Listar notas fiscais deve retornar 2", () ->
                assertEquals(2, ctrlInvoice.getAll().size(), "notas fiscais emitidas"));

        testar("Buscar NF por ID retorna pagador correto", () -> {
            Invoice nf = ctrlInvoice.getAll().get(0);
            Invoice encontrada = ctrlInvoice.getById(nf.getId());
            assertEquals(pagador.getName(), encontrada.getDono().getName(), "pagador da NF");
        });

        testar("Remover primeira NF da lista", () -> {
            Invoice nf = ctrlInvoice.getAll().get(0);
            ctrlInvoice.delete(nf.getId());
            assertEquals(1, ctrlInvoice.getAll().size(), "NFs apos delete");
        });

        testarExcecao("Deletar NF inexistente (InvoiceNotFoundException)",
                InvoiceNotFoundException.class, () ->
                        ctrlInvoice.delete(99999));

        testarExcecao("ID negativo em NF (IllegalArgumentException)",
                IllegalArgumentException.class, () ->
                        ctrlInvoice.getById(-1));

        testarExcecao("Pagador null em NotaFiscal lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Invoice(null, rex, new ArrayList<>(), new ArrayList<>()));

        testarExcecao("Paciente null em NotaFiscal lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Invoice(pagador, null, new ArrayList<>(), new ArrayList<>()));

        // =====================================================================
        //  CRUD Completo de Animais (Patch / Update / Delete)
        // =====================================================================
        secao("CRUD de Animal - Patch, Update e Delete");

        testar("Atualizar peso de Rex via patch()", () -> {
            DomesticAnimal rexComNovaRaca = new DomesticAnimal(
                    "Rex", "Cachorro", "Labrador Retriever", hoje.minusYears(4),
                    FaseDaVida.ADULTO, 32.0, Porte.GRANDE, Sexo.MACHO,
                    dono, rex.getVaccines(), Temperamento.DOCIL, true
            );
            ctrlAnimal.patch(rex.getId(), rexComNovaRaca);
            Animal verificado = ctrlAnimal.getById(rex.getId());
            assertEquals(32.0, verificado.getWeight(), "peso atualizado via patch");
        });

        testar("Substituir Rex completamente via update()", () -> {
            DomesticAnimal rexAtualizado = new DomesticAnimal(
                    "Rex II", "Cachorro", "Golden Retriever", hoje.minusYears(5),
                    FaseDaVida.ADULTO, 35.0, Porte.GRANDE, Sexo.MACHO,
                    dono, new ArrayList<>(), Temperamento.DOCIL, true
            );
            ctrlAnimal.update(rex.getId(), rexAtualizado);
            Animal verificado = ctrlAnimal.getById(rex.getId());
            assertEquals("Rex II", verificado.getName(), "nome apos update");
        });

        testar("Excluir Iguana do sistema", () -> {
            ctrlAnimal.delete(iguana.getId());
            assertEquals(1, ctrlAnimal.getAll().size(), "animais apos delete iguana");
        });

        testarExcecao("Excluir animal ja removido (AnimalNotFoundException)",
                AnimalNotFoundException.class, () ->
                        ctrlAnimal.delete(iguana.getId()));

        // =====================================================================
        //  CRUD Completo de Pessoas
        // =====================================================================
        secao("CRUD de Pessoas - Consultas e Exclusao");

        testar("Buscar dono por email valido", () -> {
            Pessoa p = ctrlPessoa.getByEmail("carlos@petcare.com");
            assertEquals("Carlos Silva", p.getNome(), "nome da pessoa por email");
        });

        testar("Listar todas as pessoas deve retornar 2", () ->
                assertEquals(2, ctrlPessoa.getAll().size(), "total de pessoas"));

        testar("Buscar pessoa por ID retorna objeto correto", () -> {
            Pessoa p = ctrlPessoa.getById(dono.getId());
            assertEquals("Carlos Silva", p.getNome(), "nome por ID");
        });

        // [BUG] O repositorio lanca EmailNotFoundException (nome errado) ao inves de EmailConflictException.
        // O controller nunca lanca PersonConflictException (bug de comparacao por referencia: p == exists).
        // O teste valida o comportamento REAL (EmailNotFoundException) para documentar o bug.
        testarExcecao("[BUG] Email duplicado lanca EmailNotFoundException (deveria ser EmailConflictException)",
                EmailNotFoundException.class, () -> {
                    Dono duplicado = new Dono(
                            "Carlos Outro", "carlos@petcare.com", "senha123",
                            hoje.minusYears(30), "99999999999", "81900000000",
                            "Dev", "Desc"
                    );
                    ctrlPessoa.post(duplicado);
                });

        testarExcecao("Email invalido lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        ctrlPessoa.getByEmail("isso-nao-e-email"));

        testarExcecao("Email inexistente lanca EmailNotFoundException",
                EmailNotFoundException.class, () ->
                        ctrlPessoa.getByEmail("inexistente@petcare.com"));

        testarExcecao("ID negativo em getById de Pessoa (IllegalArgumentException)",
                IllegalArgumentException.class, () ->
                        ctrlPessoa.getById(-1));

        testarExcecao("ID inexistente em getById de Pessoa (PersonNotFoundException)",
                PersonNotFoundException.class, () ->
                        ctrlPessoa.getById(99999));

        testar("Excluir veterinario do sistema", () -> {
            ctrlPessoa.delete(vet.getId());
            assertEquals(1, ctrlPessoa.getAll().size(), "pessoas apos delete vet");
        });

        testarExcecao("Deletar pessoa inexistente (PersonNotFoundException)",
                PersonNotFoundException.class, () ->
                        ctrlPessoa.delete(99999));

        testarExcecao("ID negativo em delete de Pessoa (IllegalArgumentException)",
                IllegalArgumentException.class, () ->
                        ctrlPessoa.delete(-1));

        // =====================================================================
        //  Validacoes de Modelo - Pessoa
        // =====================================================================
        secao("Validacoes de Modelo - Pessoa");

        testarExcecao("Nome null em Dono lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Dono(null, "t@petcare.com", "senha123", hoje.minusYears(30),
                                "00000000000", "81900000000", "Dev", "Desc"));

        // [BUG] Validacao de password: limite e < 7 mas mensagem diz "8 or more"
        testarExcecao("[BUG] Senha curta (6 chars) lanca PasswordException",
                PasswordException.class, () ->
                        new Dono("Teste", "t2@petcare.com", "abc123", hoje.minusYears(30),
                                "00000000001", "81900000001", "Dev", "Desc"));

        testarExcecao("CPF nulo em Dono lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Dono("Teste", "t3@petcare.com", "senha123", hoje.minusYears(30),
                                null, "81900000002", "Dev", "Desc"));

        testarExcecao("CRMV vazio em Veterinario lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Veterinario("Vet", "v@petcare.com", "senha123", hoje.minusYears(35),
                                "11111111111", "81900000003", "", new ArrayList<>()));

        testarExcecao("Especialidades null em Veterinario lanca IllegalArgumentException",
                IllegalArgumentException.class, () ->
                        new Veterinario("Vet", "v2@petcare.com", "senha123", hoje.minusYears(35),
                                "22222222222", "81900000004", "CRMV-001", null));

        // =====================================================================
        //  Resultado
        // =====================================================================
        resumo();
    }

    // =========================================================================
    //  UTILITARIOS DE TESTE
    // =========================================================================

    @FunctionalInterface
    interface Bloco {
        void executar() throws Exception;
    }

    private static void testar(String nome, Bloco bloco) {
        try {
            bloco.executar();
            System.out.println("  [PASS] " + nome);
            passed++;
        } catch (Exception e) {
            System.out.println("  [FAIL] " + nome);
            System.out.println("         -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
            failed++;
        }
    }

    private static <T extends Exception> void testarExcecao(String nome, Class<T> esperada, Bloco bloco) {
        try {
            bloco.executar();
            System.out.println("  [FAIL] " + nome);
            System.out.println("         -> Esperava " + esperada.getSimpleName() + " mas nenhuma excecao foi lancada");
            failed++;
        } catch (Exception e) {
            if (esperada.isInstance(e)) {
                System.out.println("  [PASS] " + nome + " (" + e.getClass().getSimpleName() + ")");
                passed++;
            } else {
                System.out.println("  [FAIL] " + nome);
                System.out.println("         -> Esperava " + esperada.getSimpleName()
                        + " mas recebeu " + e.getClass().getSimpleName() + ": " + e.getMessage());
                failed++;
            }
        }
    }

    private static void assertEquals(Object esperado, Object atual, String campo) {
        if (esperado instanceof Double && atual instanceof Double) {
            if (((Double) esperado).compareTo((Double) atual) != 0)
                throw new RuntimeException("Falha em '" + campo + "': esperado " + esperado + " mas foi " + atual);
        } else if (!esperado.equals(atual)) {
            throw new RuntimeException("Falha em '" + campo + "': esperado '" + esperado + "' mas foi '" + atual + "'");
        }
    }

    private static void secao(String titulo) {
        System.out.println();
        line('-');
        System.out.println("  " + titulo);
        line('-');
    }

    private static void line(char c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 62; i++) sb.append(c);
        System.out.println(sb.toString());
    }

    private static void resumo() {
        int total = passed + failed;
        System.out.println();
        line('=');
        System.out.println("  RESULTADO FINAL");
        line('=');
        System.out.printf("  Total  : %d%n", total);
        System.out.printf("  PASS   : %d%n", passed);
        System.out.printf("  FAIL   : %d%n", failed);
        line('=');
        if (failed == 0) {
            System.out.println("  Todos os testes passaram!");
        } else {
            System.out.println("  " + failed + " teste(s) falharam. Ver [FAIL] acima.");
        }
        line('=');
    }
}