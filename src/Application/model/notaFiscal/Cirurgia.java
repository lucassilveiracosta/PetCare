package model.notaFiscal;
import model.Pessoas.Veterinario;
import model.animal.Animal;

import java.time.LocalDateTime;

public class Cirurgia extends Procedimento{

    private Veterinario veterinarioResponsavel;
    private String tipoAnestesia;
    private String riscoCirurgico;

    public Cirurgia(Double preco, Animal paciente, LocalDateTime dataHora, String descricao, Veterinario veterinarioResponsavel, String tipoAnestesia, String riscoCirurgico) {
        super(preco, paciente, dataHora, descricao);
        setRiscoCirurgico(riscoCirurgico);
        setTipoAnestesia(tipoAnestesia);
        setVeterinarioResponsavel(veterinarioResponsavel);

    }

    public String getRiscoCirurgico() {
        return riscoCirurgico;
    }

    public void setRiscoCirurgico(String riscoCirurgico) {
        this.riscoCirurgico = riscoCirurgico;
    }

    public Veterinario getVeterinarioResponsavel() {
        return veterinarioResponsavel;
    }

    public void setVeterinarioResponsavel(Veterinario veterinarioResponsavel) {
        this.veterinarioResponsavel = veterinarioResponsavel;
    }

    public String getTipoAnestesia() {
        return tipoAnestesia;
    }

    public void setTipoAnestesia(String tipoAnestesia) {
        this.tipoAnestesia = tipoAnestesia;
    }
}


