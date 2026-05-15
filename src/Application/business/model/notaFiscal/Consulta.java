package business.model.notaFiscal;



import business.model.Pessoas.Veterinario;
import business.model.animal.Animal;

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

    public void setVeterinarioResponsavel(Veterinario veterinarioResponsavel) {
        if(veterinarioResponsavel == null){
            throw new IllegalArgumentException("Veterinario Responsável não pode ser nulo!");
        }
        this.veterinarioResponsavel = veterinarioResponsavel;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        if(diagnostico == null || diagnostico.isBlank()){
            throw new IllegalArgumentException("Diagnóstico não pode ser nulo!");
        }
        this.diagnostico = diagnostico;
    }

    public String getPrescricaoMedica() {
        return prescricaoMedica;
    }

    public void setPrescricaoMedica(String prescricaoMedica) {

        this.prescricaoMedica = prescricaoMedica;
    }
}
