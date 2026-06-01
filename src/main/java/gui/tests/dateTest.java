/*
package gui.tests;

import business.model.person.Specialty;
import business.model.person.Veterinarian;
import enums.*;
import business.model.person.Owner;
import business.model.person.Person;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.animal.Vaccine;
import business.model.appointment.*;

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

        LocalDate date1 = LocalDate.now();
        Person donoTeste = new Owner("Laercio", "laercio@gmail.com", "larceio123", date1.minus(4, ChronoUnit.DECADES), "111222333-99", "81-98888-0000", "Assoviador", "É um dono reponsável");

        ArrayList<Vaccine> vacinas = new ArrayList<>();
        vacinas.add(new Vaccine("Covid", date1,"Não se queixou",false,  date1.plusDays(90)));
        Animal animalTeste = new DomesticAnimal("Bob", "Bulldog", "preto", date1.minusYears(2), StageOfLife.ADULTO, 20.0, Size.GIGANTE, Sex.MACHO, ((Owner) donoTeste), Temperament.DOCIL,true,  vacinas );

        Specialty specialty = new Specialty("Cirurgiao", "Faz cirurgias");
        ArrayList arrayListSpecialty = new ArrayList<>();
        arrayListSpecialty.add(specialty);
        Veterinarian veterinarian = new Veterinarian("Lucas",  "laercio@gmail.com", "larceio123", date1.minus(4, ChronoUnit.DECADES), "111222333-99", "81-98888-0000", "92310231", arrayListSpecialty);


        Hydration hidratacao = new Hydration(true,null);
        VitalParameters parametrosVitais = new VitalParameters(50, 60, 34.3, Mucosa.NORMACORADAS, 50, hidratacao, "Paramtros estão normais");
        PhysicalExamination exameFisico = new PhysicalExamination(Conscience.ALERTA, parametrosVitais, "O animal se mostrou muito alerta");
        Anamnesis anamnese = new Anamnesis("Dor no ouvido", "Nenhuma", "Suspeita de ...");
        Appointment idaAoVeterinario = new Appointment(150.0, animalTeste, date.atStartOfDay(), "Desc",  veterinarian,"Diag", "Presc", new Anamnesis("Dor de ouvido", "Não", "issoai"), new PhysicalExamination(Conscience.COMATOSO, new VitalParameters(60, 60, 50.1, Mucosa.PALIDAS, 100, null, "top"), "Tudo tranks"));
        ArrayList<Appointment> idasAoVeterinario = new ArrayList<>();
        idasAoVeterinario.add(idaAoVeterinario);
        MedicalRecord prontuario = new MedicalRecord(idasAoVeterinario, "Compareceu uma vez", animalTeste);

        // --- Início dos Prints de Teste ---
        System.out.println("--- RELATÓRIO DO PRONTUÁRIO ---");
        System.out.println("ID do Prontuário: " + prontuario.hashCode()); // Apenas para identificação
        System.out.println("Observação Geral: " + prontuario.getDescription());

        System.out.println("\n--- DADOS DO ANIMAL ---");
        System.out.println("Nome: " + prontuario.getAnimal().getName());
        System.out.println("Raça: " + prontuario.getAnimal().getRace());
        System.out.println("Porte: " + prontuario.getAnimal().getSize());
        System.out.println("Dono: " + ( (DomesticAnimal) prontuario.getAnimal()).getOwner().getName());

        System.out.println("\n--- HISTÓRICO DE VACINAS ---");
        for (Vaccine v : ((DomesticAnimal) prontuario.getAnimal()).getVaccines()) {
            System.out.println("- Vacina: " + v.getVaccineName() + " | Data: " + v.getVaccineDate().format(fmt));
        }

        System.out.println("\n--- DETALHES DAS ÚLTIMAS CONSULTA ---");
        for (Appointment consulta : prontuario.getIdasAoVeterinario()) {
            System.out.println("Data da Consulta: " + consulta.getDateHourScheduled().format(fmt));
            System.out.println("Queixa Principal (Anamnese): " + consulta.getAnamnesis().getMainComplaint());
            System.out.println("Estado de Consciência: " + consulta.getPhisicalExam().getLevelOfConsciousness());
            System.out.println("Temperatura: " + consulta.getPhisicalExam().getVitalParameters().getCelciusTemperature() + "°C");
            System.out.println("Frequência Cardíaca: " + consulta.getPhisicalExam().getVitalParameters().getHeartRate() + " bpm");
            System.out.println("Diagnóstico/Conclusão: " + consulta.getDescription());
        }
        // --- Fim dos Prints de Teste ---

        sc.close();
    }
}
*/