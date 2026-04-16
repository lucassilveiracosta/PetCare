package model.animal;

import enums.Origem;
import enums.Porte;
import enums.Sexo;

import java.time.LocalDate;

public class AnimalExotico extends Animal {  private boolean requerAmbienteControlado;
    private String descricaoDieta;
    private Origem origem;

    public AnimalExotico(int idAnimal, String nome, String especie, String raca, LocalDate dataNascimento, double peso, Porte porte, Sexo sexo,String numeroDeRegistro, String microChipId, boolean requerAmbienteControlado, String descricaoDieta, Origem origem) {
        super( idAnimal,  nome,  especie,  raca,  dataNascimento,  peso,  porte,  sexo);
        this.numeroDeRegistro = numeroDeRegistro;
        this.microChipId = microChipId;
        this.requerAmbienteControlado = requerAmbienteControlado;
        this.descricaoDieta = descricaoDieta;
        this.origem = origem;
    }

    public String getNumeroDeRegistro() {
        return numeroDeRegistro;
    }

    public void setNumeroDeRegistro(String numeroDeRegistro) {
        if(numeroDeRegistro == null || numeroDeRegistro.isBlank()){
            throw new IllegalArgumentException("Numero do registro inválido!");
        }
        this.numeroDeRegistro = numeroDeRegistro;
    }

    public Origem getOrigem() {
        return origem;
    }

    public void setOrigem(Origem origem) {
        if(origem == null){
            throw new IllegalArgumentException("A origem não pode ser nula!");
        }
        this.origem = origem;
    }

    public String getDescricaoDieta() {
        return descricaoDieta;
    }

    public void setDescricaoDieta(String descricaoDieta) {
        if(descricaoDieta == null || descricaoDieta.isBlank()){
            throw new IllegalArgumentException("Insira uma descrição válida!");
        }
        this.descricaoDieta = descricaoDieta;
    }

    public String getMicroChipId() {
        return microChipId;
    }

    public void setMicroChipId(String microChipId) {
        if(microChipId == null || microChipId.isBlank()){
            throw new IllegalArgumentException("Microchip inválido!");
        }
        this.microChipId = microChipId;
    }

    public boolean isRequerAmbienteControlado() {
        return requerAmbienteControlado;
    }

    public void setRequerAmbienteControlado(boolean requerAmbienteControlado) {

        this.requerAmbienteControlado = requerAmbienteControlado;
    }
}
