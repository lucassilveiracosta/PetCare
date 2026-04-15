package model;

import enums.Consciencia;

public class ExameFisico {

    private Consciencia nivelDeConsciencia;
    private ExameFisico exameFisico;
    private String descricao;

    public ExameFisico(Consciencia nivelDeConsciencia, ExameFisico exameFisico, String descricao) {
        this.nivelDeConsciencia = nivelDeConsciencia;
        this.exameFisico = exameFisico;
        this.descricao = descricao;
    }

    public Consciencia getNivelDeConsciencia() {
        return nivelDeConsciencia;
    }

    public void setNivelDeConsciencia(Consciencia nivelDeConsciencia) {
        this.nivelDeConsciencia = nivelDeConsciencia;
    }

    public ExameFisico getExameFisico() {
        return exameFisico;
    }

    public void setExameFisico(ExameFisico exameFisico) {
        this.exameFisico = exameFisico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
