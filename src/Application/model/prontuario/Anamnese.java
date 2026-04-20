package model.prontuario;

public class Anamnese {

    private String queixaPrincipal;
    private String restricaoAlimentar;
    private String descricao;

    public Anamnese(String queixaPrincipal, String restricaoAlimentar, String descricao) {
        setQueixaPrinal(queixaPrincipal);
        setRestricaoAlimentar(restricaoAlimentar);
        setDescricao(descricao);
    }

    public String getQueixaPrincipal() {
        return queixaPrincipal;
    }

    public void setQueixaPrinal(String queixaPrincipal) {
        if (queixaPrincipal == null || queixaPrincipal.isBlank()){
            throw new IllegalArgumentException("A queixa não pode estar em branco ou sem resposta");
        }
        this.queixaPrincipal = queixaPrincipal;
    }

    public String getRestricaoAlimentar() {
        return restricaoAlimentar;
    }

    public void setRestricaoAlimentar(String restricaoAlimentar) {
        if (restricaoAlimentar == null || restricaoAlimentar.isBlank()){
            throw new IllegalArgumentException("A restrição alimentar não pode estar em branco ou sem resposta");
        }
        this.restricaoAlimentar = restricaoAlimentar;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
