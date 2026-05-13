package model.animal;

import java.time.LocalDate;

public class Vacina {
    private String nomeDaVacina;
    private LocalDate dataDaVacina;
    private String descricao;

    public Vacina(String nomeDaVacina, LocalDate dataDaVacina, String descricao) {
        this.nomeDaVacina = nomeDaVacina;
        this.dataDaVacina = dataDaVacina;
        this.descricao = descricao;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
