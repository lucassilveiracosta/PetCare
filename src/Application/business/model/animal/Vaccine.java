package business.model.animal;

import java.time.LocalDate;

public class Vaccine {
    private String nomeDaVacina;
    private LocalDate dataDaVacina;
    private String descricao;

    public Vaccine(String nomeDaVacina, LocalDate dataDaVacina, String descricao) {
        this.nomeDaVacina = nomeDaVacina;
        this.dataDaVacina = dataDaVacina;
        this.descricao = descricao;
    }

    public String getNomeDaVacina() {
        return nomeDaVacina;
    }

    public void setNomeDaVacina(String nomeDaVacina) {
        if(nomeDaVacina == null || nomeDaVacina.isBlank()){
            throw new IllegalArgumentException("Vacina não pode ser nula!");
        }
        this.nomeDaVacina = nomeDaVacina;
    }

    public LocalDate getDataDaVacina() {
        return dataDaVacina;
    }

    public void setDataDaVacina(LocalDate dataDaVacina) {
        if(dataDaVacina == null){
            throw new IllegalArgumentException("Data da vacina não pode ser nula!");
        }
        this.dataDaVacina = dataDaVacina;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if(descricao == null || descricao.isBlank()){
            throw new IllegalArgumentException("Descrição da vacina não pode ser nula!");
        }
        this.descricao = descricao;
    }
}
