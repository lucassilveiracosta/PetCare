package model.notaFiscal;

import model.Pessoas.Veterinario;
import model.animal.Animal;

import java.time.LocalDateTime;

public class Consulta extends Procedimento {
    private Veterinario veterinarioResponsavel;
    private String diagnostico;
    private String prescricaoMedica;

    public Consulta(Double preco, Animal paciente, LocalDateTime dataHora, String descricao, Veterinario veterinarioResponsavel, String diagnostico, String prescricaoMedica) {
        super(preco, paciente, dataHora, descricao);
        setDiagnostico(diagnostico);
        setVeterinarioResponsavel(veterinarioResponsavel);
        setPrescricaoMedica(prescricaoMedica);

    }

    public Veterinario getVeterinarioResponsavel() {
        return veterinarioResponsavel;
    }

    private void setVeterinarioResponsavel(Veterinario veterinarioResponsavel) {
        this.veterinarioResponsavel = veterinarioResponsavel;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    private void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getPrescricaoMedica() {
        return prescricaoMedica;
    }

    private void setPrescricaoMedica(String prescricaoMedica) {
        this.prescricaoMedica = prescricaoMedica;
    }
}
