package model.notaFiscal;

import model.Pessoas.ResponsavelPagador;
import model.animal.Animal;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class NotaFiscal {
    protected static int contadorId = 1;

    protected int id = contadorId++;
    private ResponsavelPagador responsavelPagador;
    private Animal paciente;
    private final LocalDateTime dataHora = LocalDateTime.now();
    private ArrayList<Procedimento> procedimentos;
    private ArrayList<Produto> produtos;

    public NotaFiscal(ResponsavelPagador responsavelPagador, Animal paciente, ArrayList<Procedimento> procedimentos, ArrayList<Produto> produtos) {
        setResponsavelPagador(responsavelPagador);
        setPaciente(paciente);
        this.procedimentos = procedimentos;
        this.produtos = produtos;
    }

    public ResponsavelPagador getResponsavelPagador() {
        return responsavelPagador;
    }

    public void setResponsavelPagador(ResponsavelPagador responsavelPagador) {
        if (responsavelPagador == null) {
            throw new IllegalArgumentException("O Responsavel Pagador não pode ser nulo");
        }
        this.responsavelPagador = responsavelPagador;
    }

    public Animal getPaciente() {
        return paciente;
    }

    public void setPaciente(Animal paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("O paciente não pode ser nulo");
        }
        this.paciente = paciente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public ArrayList<Procedimento> getProcedimentos() {
        return procedimentos;
    }

    public void setProcedimentos(ArrayList<Procedimento> procedimentos) {
        if (procedimentos == null) {
            throw new IllegalArgumentException("O procedimento não pode ser nulo");
        }
        this.procedimentos = procedimentos;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        if (produtos == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo");
        }
        this.produtos = produtos;
    }
}
