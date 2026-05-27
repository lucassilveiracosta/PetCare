package data;

// ... (seus imports de business, enums, etc)
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.animal.Vaccine;
import business.model.appointment.*;
import business.model.person.Owner;
import business.model.person.Person;
import business.model.person.Specialty;
import business.model.person.Veterinarian;
import enums.Conscience;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class FixedData {

    // Listas que guardarão seus dados fixos
    private static List<Person> pessoas = new ArrayList<>();
    private static List<Animal> animais = new ArrayList<>();
    private static List<Appointment> consultas = new ArrayList<>();

    // O bloco 'static' roda automaticamente assim que a classe for chamada pela 1ª vez
    static {
        LocalDate dataAtual = LocalDate.now();

        // --- 1. CRIANDO AS PESSOAS (Donos e Veterinários) ---
        Owner donoTeste = new Owner("Laercio", "laercio@gmail.com", "larceio123", dataAtual.minus(4, ChronoUnit.DECADES), "111222333-99", "81-98888-0000", "Assoviador", "É um dono reponsável");
        pessoas.add(donoTeste);

        Specialty specialty = new Specialty("Cirurgiao", "Faz cirurgias");
        ArrayList<Specialty> arrayListSpecialty = new ArrayList<>();
        arrayListSpecialty.add(specialty);
        Veterinarian vetTeste = new Veterinarian("Lucas",  "lucas@gmail.com", "lucas123", dataAtual.minus(4, ChronoUnit.DECADES), "111222333-99", "81-98888-0000", "92310231", arrayListSpecialty);
        pessoas.add(vetTeste);

        // --- 2. CRIANDO OS ANIMAIS ---
        ArrayList<Vaccine> vacinas = new ArrayList<>();
        vacinas.add(new Vaccine("Covid", dataAtual,"Não se queixou",false,  dataAtual.plusDays(90)));
        Animal animalTeste = new DomesticAnimal("Bob", "Bulldog", "preto", dataAtual.minusYears(2), StageOfLife.ADULTO, 20.0, Size.GIGANTE, Sex.MACHO, donoTeste, Temperament.DOCIL, true, vacinas);
        animais.add(animalTeste);

        // --- 3. CRIANDO AS CONSULTAS (Appointments) ---
        Hydration hidratacao = new Hydration(true,null);
        VitalParameters parametrosVitais = new VitalParameters(50, 60, 34.3, Mucosa.NORMACORADAS, 50, hidratacao, "Parâmetros estão normais");
        PhysicalExamination exameFisico = new PhysicalExamination(Conscience.ALERTA, parametrosVitais, "O animal se mostrou muito alerta");
        Anamnesis anamnese = new Anamnesis("Dor no ouvido", "Nenhuma", "Suspeita de otite");

        Appointment idaAoVeterinario = new Appointment(150.0, animalTeste, dataAtual.atStartOfDay(), "Descrição da consulta", vetTeste, "Diagnóstico final", "Prescrição médica", anamnese, exameFisico);
        consultas.add(idaAoVeterinario);
    }

    public static List<Person> getPessoas() { return pessoas; }
    public static List<Animal> getAnimais() { return animais; }
    public static List<Appointment> getConsultas() { return consultas; }
}