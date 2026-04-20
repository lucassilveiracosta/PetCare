package model.animal;

import enums.Porte;
import enums.Sexo;
import enums.TempoDeVida;

import java.time.LocalDate;


public abstract class Animal {
    protected static int contadorId = 1;

    protected int id = contadorId++;
    protected String Nome;
    protected String especie;
    protected String raca;
    protected LocalDate DataNascimento;
    protected Double peso;
    protected Porte porte;
    protected Sexo sexo;

    public Animal(String nome, String especie, String raca, LocalDate dataNascimento, double peso, Porte porte, Sexo sexo, TempoDeVida tempodevida) {
        setNome(nome);
        setEspecie(especie);
        setRaca(raca);
        setDataNascimento(dataNascimento);
        setPeso(peso);
        setPorte(porte);
        setSexo(sexo);

    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome do Animal inválido!");
        }
        Nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        if(raca == null || raca.isBlank()){
            throw new IllegalArgumentException("Nome da raça do animal inválido!");
        }
        this.raca = raca;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        if(especie == null || especie.isBlank()){
            throw new IllegalArgumentException("Nome da especie do animal inválida!");
        }
        this.especie = especie;
    }

    public LocalDate getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {

        if(dataNascimento == null ){
            throw new IllegalArgumentException("Data de nascimento inválida!");
        } DataNascimento = dataNascimento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if(peso <= 0 ){
            throw new IllegalArgumentException("peso do animal inválido!");
        }
        this.peso = peso;
    }

    public Porte getPorte() {
        return porte;
    }

    public void setPorte(Porte porte) {
        if(porte == null ){
            throw new IllegalArgumentException("Porte do animal inválido!");
        }
        this.porte = porte;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        if(sexo == null ){
            throw new IllegalArgumentException("Sexo do animal inválido!");
        }
        this.sexo = sexo;
    }

    public int getId() {
        return id;
    }

/*
            try {
                // formato que o usuário digita
                DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                // converter string para data
                LocalDate data = LocalDate.parse(DataNascimento, formatoEntrada);

                // formato padrão desejado
                DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                // exibir formatado
                String dataFormatada = data.format(formatoSaida);

                System.out.println("Data formatada: " + dataFormatada);

            } catch (DateTimeParseException e) {
                System.out.println("Erro: Data inválida! Use o formato dd/MM/yyyy.");
            }
       */
}

