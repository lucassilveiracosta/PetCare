package business.model.notaFiscal;

import business.model.animal.Animal;

import java.time.LocalDateTime;

public abstract class Procedure {
    protected static int contadorId = 1;

    protected int id = contadorId++;
    private Animal paciente;
    private LocalDateTime dataHora;
    private String descricao;
    private Double preco;

    public Procedure(Double preco, Animal paciente, LocalDateTime dataHora, String descricao) {
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
        if (dataHora == null) {
            throw new IllegalArgumentException("A data não pode ser nula");
        }
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

    public int getId() {
        return id;
    }
}
