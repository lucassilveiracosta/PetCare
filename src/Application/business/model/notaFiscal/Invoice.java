package business.model.notaFiscal;

import business.model.Pessoas.Owner;
import business.model.animal.Animal;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Invoice {
    protected static int contadorId = 1;

    protected int id;
    private Owner pagador;
    private Animal paciente;
    private final LocalDateTime dataHora = LocalDateTime.now();
    private ArrayList<Procedure> procedimentos;
    private ArrayList<Product> produtos;

    public Invoice(Owner pagador, Animal paciente, ArrayList<Procedure> procedimentos, ArrayList<Product> produtos) {
        this.id = contadorId++;
        setDono(pagador);
        setPaciente(paciente);
        this.procedimentos = procedimentos;
        this.produtos = produtos;
    }

    public int getId() {
        return id;
    }

    public Owner getDono() {
        return pagador;
    }

    public void setDono(Owner pagador) {
        if (pagador == null) {
            throw new IllegalArgumentException("O Responsavel Pagador não pode ser nulo");
        }
        this.pagador = pagador;
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

    public ArrayList<Procedure> getProcedimentos() {
        return procedimentos;
    }

    public void setProcedimentos(ArrayList<Procedure> procedimentos) {
        if (procedimentos == null) {
            throw new IllegalArgumentException("O procedimento não pode ser nulo");
        }
        this.procedimentos = procedimentos;
    }

    public ArrayList<Product> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Product> produtos) {
        if (produtos == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo");
        }
        this.produtos = produtos;
    }
}