package model.prontuario;

import enums.Consciencia;

public class ExameFisico {

    private Consciencia nivelDeConsciencia;
    private ParametrosVitais parametrosVitais;
    private String descricao;

    public ExameFisico(Consciencia nivelDeConsciencia, ParametrosVitais parametrosVitais, String descricao) {
        this.nivelDeConsciencia = nivelDeConsciencia;
        this.parametrosVitais = parametrosVitais;
        this.descricao = descricao;
    }

    public Consciencia getNivelDeConsciencia() {
        return nivelDeConsciencia;
    }

    public void setNivelDeConsciencia(Consciencia nivelDeConsciencia) {
        this.nivelDeConsciencia = nivelDeConsciencia;
    }

    public ParametrosVitais getParametrosVitais() {
        return parametrosVitais;
    }

    public void setParametrosVitais(ParametrosVitais parametrosVitais) {
        this.parametrosVitais = parametrosVitais;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
