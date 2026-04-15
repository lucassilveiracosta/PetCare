package model;

import java.time.LocalDate;
// Classe abstrata pessoa
public abstract class Pessoa {
    private int id;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String telefone;

    // Construtor de pessoa
    public Pessoa(String nome, LocalDate dataNascimento, String cpf, String telefone){
        setNome(nome);
        setDataNascimento(dataNascimento);
        setCpf(cpf);
        setTelefone(telefone);
    }

    // Metodo get do ID
    public int getId(){
        return id;
    }
    // Metodo get do Nome
    public String getNome(){
        return nome;
    }
    // Metodo Set do nome
    private void setNome(String nome){
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome inválido!");
        }
        this.nome = nome;
    }
    // Metodo get da data de nascimento
    public LocalDate getDataNascimento(){
        return dataNascimento;
    }
    // Metodo set da data de nascimento
    private void setDataNascimento(LocalDate dataNascimento){
        if(dataNascimento == null){
            throw new IllegalArgumentException("Data De nascimento inválida!");
        }
        this.dataNascimento = dataNascimento;
    }
    // Metodo get de Cpf
    public String getCpf(){
        return cpf;
    }
    // Metodo Set de Cpf
    private void setCpf(String cpf){
        if(cpf == null || cpf.isBlank()){
            throw new IllegalArgumentException("Cpf inválido!");
        }
        this.cpf = cpf;
    }
    // Metodo get de Telefone
    public String getTelefone(){
        return telefone;
    }
    // Metodo set de Telefone
    private void setTelefone(String telefone){
        if(telefone == null || telefone.isBlank()){
            throw new IllegalArgumentException("Número inválido!");
        }
        this.telefone = telefone;
    }

    private void setId(){

    }




}
