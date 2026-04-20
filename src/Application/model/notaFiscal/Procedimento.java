package model.notaFiscal;

import model.animal.Animal;

import java.time.LocalDateTime;

public class Procedimento {

    private Animal paciente;
    private LocalDateTime dataHora;
    private String descricao;
    private Double preco;

    public Procedimento(Double preco, Animal paciente, LocalDateTime dataHora, String descricao) {
        this.preco = preco;
        setPaciente(paciente);
        this.dataHora = dataHora;
        setDescricao(descricao);
    }

    public Animal getPaciente() {
        return paciente;
    }

    public void setPaciente(Animal paciente) {
        if(paciente == null){
            throw new IllegalArgumentException("O paciente nao pode ser nulo");
        }
        this.paciente = paciente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição do procedimento não pode ser nula");
        }
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
