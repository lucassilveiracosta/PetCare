package gui.tests;

import enums.*;
import business.model.Pessoas.Dono;
import business.model.Pessoas.Pessoa;
import business.model.animal.Animal;
import business.model.animal.AnimalDomestico;
import business.model.animal.Vacina;
import business.model.prontuario.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class dateTest {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite uma data: ");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(sc.next(), fmt); // ----> trecho de codigo para receber uma data no formato dd/MM/yyyy
        System.out.println(date);

        Pessoa donoTeste = new Dono("Laercio", date.minus(4, ChronoUnit.DECADES), "111222333-99", "81-98888-0000", "Assoviador", "É um dono reponsável");

        ArrayList<Vacina> vacinas = new ArrayList<>();
        vacinas.add(new Vacina("Covid", date.minusDays(5),"Não se queixou"));
        Animal animalTeste = new AnimalDomestico("Bob", "Bulldog", "preto", date.minusYears(2), FaseDaVida.ADULTO, 20.0, Porte.GIGANTE, Sexo.MACHO, ((Dono) donoTeste), vacinas, Temperamento.DOCIL, true );


        Hidratacao hidratacao = new Hidratacao(true,null);
        ParametrosVitais parametrosVitais = new ParametrosVitais(50, 60, 34.3, Mucosa.NORMACORADAS, 50, hidratacao, "Paramtros estão normais");
        ExameFisico exameFisico = new ExameFisico(Consciencia.ALERTA, parametrosVitais, "O animal se mostrou muito alerta");
        Anamnese anamnese = new Anamnese("Dor no ouvido", "Nenhuma", "Suspeita de ...");
        IdaAoVeterinario idaAoVeterinario = new IdaAoVeterinario(date, exameFisico, anamnese, "Foi um alarme falso, apenas uma dor temporaria");
        ArrayList<IdaAoVeterinario> idasAoVeterinario = new ArrayList<>();
        idasAoVeterinario.add(idaAoVeterinario);
        Prontuario prontuario = new Prontuario(idasAoVeterinario, "Compareceu uma vez", animalTeste);

        // --- Início dos Prints de Teste ---
        System.out.println("--- RELATÓRIO DO PRONTUÁRIO ---");
        System.out.println("ID do Prontuário: " + prontuario.hashCode()); // Apenas para identificação
        System.out.println("Observação Geral: " + prontuario.getDescricao());

        System.out.println("\n--- DADOS DO ANIMAL ---");
        System.out.println("Nome: " + prontuario.getAnimal().getNome());
        System.out.println("Raça: " + prontuario.getAnimal().getRaca());
        System.out.println("Porte: " + prontuario.getAnimal().getPorte());
        System.out.println("Dono: " + ( (AnimalDomestico) prontuario.getAnimal()).getDono().getNome());

        System.out.println("\n--- HISTÓRICO DE VACINAS ---");
        for (Vacina v : ((AnimalDomestico) prontuario.getAnimal()).getVacinas()) {
            System.out.println("- Vacina: " + v.getNomeDaVacina() + " | Data: " + v.getDataDaVacina().format(fmt));
        }

        System.out.println("\n--- DETALHES DA ÚLTIMA CONSULTA ---");
        for (IdaAoVeterinario consulta : prontuario.getIdasAoVeterinario()) {
            System.out.println("Data da Consulta: " + consulta.getDataDePresenca().format(fmt));
            System.out.println("Queixa Principal (Anamnese): " + consulta.getAnamnese().getQueixaPrincipal());
            System.out.println("Estado de Consciência: " + consulta.getExameFisico().getNivelDeConsciencia());
            System.out.println("Temperatura: " + consulta.getExameFisico().getParametrosVitais().getTemperaturaCelcius() + "°C");
            System.out.println("Frequência Cardíaca: " + consulta.getExameFisico().getParametrosVitais().getFrequenciaCardiaca() + " bpm");
            System.out.println("Diagnóstico/Conclusão: " + consulta.getDescricao());
        }
        // --- Fim dos Prints de Teste ---

        sc.close();
    }
}
