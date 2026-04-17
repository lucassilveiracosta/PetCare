package model.prontuario;

import enums.Consciencia;

public class ExameFisico {

    private Consciencia nivelDeConsciencia;
    private ParametrosVitais parametrosVitais;
    private String descricao;

    public ExameFisico(Consciencia nivelDeConsciencia, ParametrosVitais parametrosVitais, String descricao) {
        setNivelDeConsciencia(nivelDeConsciencia);
        this.parametrosVitais = parametrosVitais;
        this.descricao = descricao;
    }

    public Consciencia getNivelDeConsciencia() {
        return nivelDeConsciencia;
    }

    public void setNivelDeConsciencia(Consciencia nivelDeConsciencia) {
        if (nivelDeConsciencia == null) {
            throw new IllegalArgumentException("Deve ser preenchido um nivel de consciencia!");
        }
        this.nivelDeConsciencia = nivelDeConsciencia;
    }

    public ParametrosVitais getParametrosVitais() {
        return parametrosVitais;
    }

    public void setParametrosVitais(ParametrosVitais parametrosVitais) {
        if (parametrosVitais == null) {
            throw new IllegalArgumentException("Deve ser preenchido os parametros vitais!");
        }
        this.parametrosVitais = parametrosVitais;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição não pode ser nula ou ficar em branco!");
        }
        this.descricao = descricao;
    }
}
