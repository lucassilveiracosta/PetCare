package model.teste1.teste2;

import model.Anamnese;
import model.ExameFisico;

import java.time.LocalDate;

public class IdaAoVeterinario {

    private LocalDate dataDePresenca;
    private Anamnese anamnese;
    private ExameFisico exameFisico;
    private String descricao;

    public IdaAoVeterinario(LocalDate dataDePresenca, ExameFisico exameFisico, Anamnese anamnese, String descricao) {
        this.dataDePresenca = dataDePresenca;
        this.exameFisico = exameFisico;
        this.anamnese = anamnese;
        this.descricao = descricao;
    }

    public LocalDate getDataDePresenca() {
        return dataDePresenca;
    }

    public void setDataDePresenca(LocalDate dataDePresenca) {
        this.dataDePresenca = dataDePresenca;
    }

    public Anamnese getAnamnese() {
        return anamnese;
    }

    public void setAnamnese(Anamnese anamnese) {
        this.anamnese = anamnese;
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
