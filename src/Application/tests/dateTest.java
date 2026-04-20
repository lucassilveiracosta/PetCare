package tests;

import enums.Consciencia;
import enums.Mucosa;
import model.prontuario.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class dateTest {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite uma data: ");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(sc.next(), fmt); // ----> trecho de codigo para receber uma data no formato dd/MM/yyyy
        System.out.println(date);

        /*
        Hidratacao hidratacao = new Hidratacao(true,null);
        ParametrosVitais parametrosVitais = new ParametrosVitais(50, 60, 34.3, Mucosa.NORMACORADAS, 50, hidratacao, "Paramtros estão normais");
        ExameFisico exameFisico = new ExameFisico(Consciencia.ALERTA, parametrosVitais, "O animal se mostrou muito alerta");
        Anamnese anamnese = new Anamnese("Dor no ouvido", "Nenhuma", "Suspeita de ...");
        IdaAoVeterinario idaAoVeterinario = new IdaAoVeterinario(date, exameFisico, anamnese, "Foi um alarme falso, apenas uma dor temporaria");
        */
        sc.close();
    }
}
