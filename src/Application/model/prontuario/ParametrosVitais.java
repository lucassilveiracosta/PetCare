package model;

import enums.Mucosa;

public class ParametrosVitais {

    private Integer frequenciaCardiaca;
    private Integer frequenciaRespiratoria;
    private Double temperaturaCelcius;
    private Integer coagulacao;
    private Mucosa mucosa;
    private Hidratacao hidratacao;
    private String descricao;

    public ParametrosVitais(Integer frequenciaCardiaca, Integer frequenciaRespiratoria, Double temperaturaCelcius, Mucosa mucosa, Integer coagulacao, Hidratacao hidratacao, String descricao) {
        this.frequenciaCardiaca = frequenciaCardiaca;
        this.frequenciaRespiratoria = frequenciaRespiratoria;
        this.temperaturaCelcius = temperaturaCelcius;
        this.mucosa = mucosa;
        this.coagulacao = coagulacao;
        this.hidratacao = hidratacao;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getFrequenciaCardiaca() {
        return frequenciaCardiaca;
    }

    public void setFrequenciaCardiaca(Integer frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
    }

    public Integer getFrequenciaRespiratoria() {
        return frequenciaRespiratoria;
    }

    public void setFrequenciaRespiratoria(Integer frequenciaRespiratoria) {
        this.frequenciaRespiratoria = frequenciaRespiratoria;
    }

    public Double getTemperaturaCelcius() {
        return temperaturaCelcius;
    }

    public void setTemperaturaCelcius(Double temperaturaCelcius) {
        this.temperaturaCelcius = temperaturaCelcius;
    }

    public Integer getCoagulacao() {
        return coagulacao;
    }

    public void setCoagulacao(Integer coagulacao) {
        this.coagulacao = coagulacao;
    }

    public Mucosa getMucosa() {
        return mucosa;
    }

    public void setMucosa(Mucosa mucosa) {
        this.mucosa = mucosa;
    }

    public Hidratacao getHidratacao() {
        return hidratacao;
    }

    public void setHidratacao(Hidratacao hidratacao) {
        this.hidratacao = hidratacao;
    }
}
