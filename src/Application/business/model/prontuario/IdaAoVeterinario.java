package business.model.prontuario;

import java.time.LocalDate;

public class IdaAoVeterinario {

    private LocalDate dataDePresenca;
    private Anamnesis anamnese;
    private PhysicalExamination exameFisico;
    private String descricao;

    public IdaAoVeterinario(LocalDate dataDePresenca, PhysicalExamination exameFisico, Anamnesis anamnese, String descricao) {
        this.dataDePresenca = dataDePresenca;
        setExameFisico(exameFisico);
        setAnamnese(anamnese);
        setDescricao(descricao);
    }

    public LocalDate getDataDePresenca() {
        return dataDePresenca;
    }

    public void setDataDePresenca(LocalDate dataDePresenca) { // a data deve vir no formato dd/MM/yyyy
        this.dataDePresenca = dataDePresenca;
    }

    public Anamnesis getAnamnese() {
        return anamnese;
    }

    public void setAnamnese(Anamnesis anamnese) {
        if (anamnese == null) {
            throw new IllegalArgumentException("Anamnese não pode ser nula");
        }
        this.anamnese = anamnese;
    }

    public PhysicalExamination getExameFisico() {
        return exameFisico;
    }

    public void setExameFisico(PhysicalExamination exameFisico) {
        if (exameFisico == null) {
            throw new IllegalArgumentException("Exame físico não pode ser nulo");
        }
        this.exameFisico = exameFisico;
    }

    public String getDescricao() {
        return descricao;
    }

    //Setter ajustado
    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição não pode ficar em branco ou ser nulo");
        }
        this.descricao = descricao;
    }
}
