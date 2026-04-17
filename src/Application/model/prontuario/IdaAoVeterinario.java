package model.prontuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public void setDataDePresenca(LocalDate dataDePresenca) { // a data deve vir no formato dd/MM/yyyy
        this.dataDePresenca = dataDePresenca;
    }

    public Anamnese getAnamnese() {
        return anamnese;
    }

    public void setAnamnese(Anamnese anamnese) {
        if (anamnese == null) {
            throw new IllegalArgumentException("Anamnese não pode ser nula");
        }
        this.anamnese = anamnese;
    }

    public ExameFisico getExameFisico() {
        return exameFisico;
    }

    public void setExameFisico(ExameFisico exameFisico) {
        if (exameFisico == null) {
            throw new IllegalArgumentException("Anamnese não pode ser nula");
        }
        this.exameFisico = exameFisico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Anamnese não pode ser nula");
        }
        this.descricao = descricao;
    }
}
