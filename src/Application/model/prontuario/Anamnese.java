package model.prontuario;

public class Anamnese {

    private String queixaPrinal;
    private String restricaoAlimentar;
    private String descricao;

    public Anamnese(String queixaPrinal, String restricaoAlimentar, String descricao) {
        setQueixaPrinal(queixaPrinal);
        setRestricaoAlimentar(restricaoAlimentar);
        setDescricao(descricao);
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
