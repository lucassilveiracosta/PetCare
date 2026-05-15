package gui.tests;

import enums.*;
import business.model.Pessoas.Owner;
import business.model.Pessoas.Person;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.animal.Vaccine;
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

        Person donoTeste = new Owner("Laercio", "laercio@gmail.com", "larceio123", date.minus(4, ChronoUnit.DECADES), "111222333-99", "81-98888-0000", "Assoviador", "É um dono reponsável");

        ArrayList<Vaccine> vacinas = new ArrayList<>();
        vacinas.add(new Vaccine("Covid", date.minusDays(5),"Não se queixou"));
        Animal animalTeste = new DomesticAnimal("Bob", "Bulldog", "preto", date.minusYears(2), StageOfLife.ADULTO, 20.0, Size.GIGANTE, Sex.MACHO, ((Owner) donoTeste), vacinas, Temperament.DOCIL, true );


        Hydration hidratacao = new Hydration(true,null);
        VitalParameters parametrosVitais = new VitalParameters(50, 60, 34.3, Mucosa.NORMACORADAS, 50, hidratacao, "Paramtros estão normais");
        PhysicalExamination exameFisico = new PhysicalExamination(Conscience.ALERTA, parametrosVitais, "O animal se mostrou muito alerta");
        Anamnesis anamnese = new Anamnesis("Dor no ouvido", "Nenhuma", "Suspeita de ...");
        Appointment idaAoVeterinario = new Appointment(date, exameFisico, anamnese, "Foi um alarme falso, apenas uma dor temporaria");
        ArrayList<Appointment> idasAoVeterinario = new ArrayList<>();
        idasAoVeterinario.add(idaAoVeterinario);
        MedicalRecord prontuario = new MedicalRecord(idasAoVeterinario, "Compareceu uma vez", animalTeste);

        // --- Início dos Prints de Teste ---
        System.out.println("--- RELATÓRIO DO PRONTUÁRIO ---");
        System.out.println("ID do Prontuário: " + prontuario.hashCode()); // Apenas para identificação
        System.out.println("Observação Geral: " + prontuario.getDescricao());

        System.out.println("\n--- DADOS DO ANIMAL ---");
        System.out.println("Nome: " + prontuario.getAnimal().getName());
        System.out.println("Raça: " + prontuario.getAnimal().getRace());
        System.out.println("Porte: " + prontuario.getAnimal().getSize());
        System.out.println("Dono: " + ( (DomesticAnimal) prontuario.getAnimal()).getOwner().getName());

        System.out.println("\n--- HISTÓRICO DE VACINAS ---");
        for (Vaccine v : ((DomesticAnimal) prontuario.getAnimal()).getVaccines()) {
            System.out.println("- Vacina: " + v.getVaccineName() + " | Data: " + v.getVaccineDate().format(fmt));
        }

        System.out.println("\n--- DETALHES DA ÚLTIMA CONSULTA ---");
        for (Appointment consulta : prontuario.getIdasAoVeterinario()) {
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
