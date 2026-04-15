package model;

import java.time.LocalDateTime;
// Classe abstrata pessoa
public abstract class Pessoa {
    private int id;
    private String nome;
    private LocalDateTime dataNascimento;
    private String cpf;
    private String telefone;

    // Construtor de pessoa
    public Pessoa(String nome, LocalDateTime dataNascimento, String cpf, String telefone){
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
        this.nome = nome;
    }
    // Metodo get da data de nascimento
    public LocalDateTime getDataNascimento(){
        return dataNascimento;
    }
    // Metodo set da data de nascimento
    private void setDataNascimento(LocalDateTime dataNascimento){
        this.dataNascimento = dataNascimento;
    }
    // Metodo get de Cpf
    public String getCpf(){
        return cpf;
    }
    // Metodo Set de Cpf
    private void setCpf(String cpf){
        this.cpf = cpf;
    }
    // Metodo get de Telefone
    public String getTelefone(){
        return telefone;
    }
    // Metodo set de Telefone
    private void setTelefone(String telefone){
        this.telefone = telefone;
    }




}
