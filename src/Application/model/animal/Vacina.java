package model.animal;

import java.time.LocalDate;
import java.util.ArrayList;

public class Vacina {
    private String nomeDaVacina;
    private LocalDate dataDaVacina;
    ArrayList<String> vacinasPendentes = new ArrayList<>();
    ArrayList<String> vacinasTomadas = new ArrayList<>();
    ArrayList<String> vacinasAtrasadas = new ArrayList<>();

}
