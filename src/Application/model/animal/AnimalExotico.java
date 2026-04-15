package model.animal;

import enums.Origem;

public class AnimalExotico {
    private String numeroDeRegistro;
    private String microChipId;
    private boolean requerAmbienteControlado;
    private String descricaoDieta;
    private Origem origem;

    public AnimalExotico(String numeroDeRegistro, String microChipId, boolean requerAmbienteControlado, String descricaoDieta, Origem origem) {
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
        this.descricaoDieta = descricaoDieta;
    }

    public String getMicroChipId() {
        return microChipId;
    }

    public void setMicroChipId(String microChipId) {
        this.microChipId = microChipId;
    }

    public boolean isRequerAmbienteControlado() {
        return requerAmbienteControlado;
    }

    public void setRequerAmbienteControlado(boolean requerAmbienteControlado) {
        this.requerAmbienteControlado = requerAmbienteControlado;
    }
}
