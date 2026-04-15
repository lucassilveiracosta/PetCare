package model.prontuario;

import repository.RepositorioVacinas;

public class Anamnese {

    private String queixaPrinal;
    private String restricaoAlimentar;
    private RepositorioVacinas vacinas;
    private String descricao;

    public Anamnese(String queixaPrinal, String restricaoAlimentar, RepositorioVacinas vacinas, String descricao) {
        this.queixaPrinal = queixaPrinal;
        this.restricaoAlimentar = restricaoAlimentar;
        this.vacinas = vacinas;
        this.descricao = descricao;
    }

    public String getQueixaPrinal() {
        return queixaPrinal;
    }

    public void setQueixaPrinal(String queixaPrinal) {
        this.queixaPrinal = queixaPrinal;
    }

    public String getRestricaoAlimentar() {
        return restricaoAlimentar;
    }

    public void setRestricaoAlimentar(String restricaoAlimentar) {
        this.restricaoAlimentar = restricaoAlimentar;
    }

    public RepositorioVacinas getVacinas() {
        return vacinas;
    }

    public void setVacinas(RepositorioVacinas vacinas) {
        this.vacinas = vacinas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
