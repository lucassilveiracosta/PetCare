package business.model.notaFiscal;
import business.model.Pessoas.Veterinario;
import business.model.animal.Animal;

import java.time.LocalDateTime;

public class Surgery extends Procedure {

    private Veterinario veterinarioResponsavel;
    private String tipoAnestesia;
    private String riscoCirurgico;

    public Surgery(Double preco, Animal paciente, LocalDateTime dataHora, String descricao, Veterinario veterinarioResponsavel, String tipoAnestesia, String riscoCirurgico) {
        super(preco, paciente, dataHora, descricao);
        setRiscoCirurgico(riscoCirurgico);
        setTipoAnestesia(tipoAnestesia);
        setVeterinarioResponsavel(veterinarioResponsavel);

    }

    public String getRiscoCirurgico() {
        return riscoCirurgico;
    }

    public void setRiscoCirurgico(String riscoCirurgico) {
        if(riscoCirurgico == null || riscoCirurgico.isBlank()){
            throw new IllegalArgumentException("O risco cirurgico não pode ser nulo!");
        }
        this.riscoCirurgico = riscoCirurgico;
    }

    public Veterinario getVeterinarioResponsavel() {
        return veterinarioResponsavel;
    }

    public void setVeterinarioResponsavel(Veterinario veterinarioResponsavel) {
        if(veterinarioResponsavel == null){
            throw new IllegalArgumentException("Veterinario responsável não pode ser nulo!");
        }
        this.veterinarioResponsavel = veterinarioResponsavel;
    }

    public String getTipoAnestesia() {
        return tipoAnestesia;
    }

    public void setTipoAnestesia(String tipoAnestesia) {
        if(tipoAnestesia == null || tipoAnestesia.isBlank()){
            throw new IllegalArgumentException("O tipo da anestesia não pode ser nula!");
        }
        this.tipoAnestesia = tipoAnestesia;
    }
}


