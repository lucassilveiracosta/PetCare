package model;

import java.time.LocalDate;

public class Vacina {

    private String nomeDaVacina;
    private LocalDate dataDaVacina;


    public Vacina(String nomeDaVacina, LocalDate dataDaVacina) {
        this.nomeDaVacina = nomeDaVacina;
        this.dataDaVacina = dataDaVacina;
    }

    public String getNomeDaVacina() {
        return nomeDaVacina;
    }

    public void setNomeDaVacina(String nomeDaVacina) {
        this.nomeDaVacina = nomeDaVacina;
    }

    public LocalDate getDataDaVacina() {
        return dataDaVacina;
    }

    public void setDataDaVacina(LocalDate dataDaVacina) {
        this.dataDaVacina = dataDaVacina;
    }
}
