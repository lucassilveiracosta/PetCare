package business.model.notaFiscal;

import business.model.Pessoas.Dono;
import business.model.animal.Animal;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class NotaFiscal {
    protected static int contadorId = 1;

    protected int id;
    private Dono pagador;
    private Animal paciente;
    private final LocalDateTime dataHora = LocalDateTime.now();
    private ArrayList<Procedimento> procedimentos;
    private ArrayList<Produto> produtos;

    public NotaFiscal(Dono pagador, Animal paciente, ArrayList<Procedimento> procedimentos, ArrayList<Produto> produtos) {
        this.id = contadorId++;
        setResponsavelPagador(NotaFiscal.this.pagador);
        setPaciente(paciente);
        this.procedimentos = procedimentos;
        this.produtos = produtos;
    }

    public int getId() {
        return id;
    }

    public Dono getResponsavelPagador() {
        return pagador;
    }

    public void setResponsavelPagador(Dono pagador) {
        if (NotaFiscal.this.pagador == null) {
            throw new IllegalArgumentException("O Responsavel Pagador não pode ser nulo");
        }
        this.pagador = NotaFiscal.this.pagador;
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