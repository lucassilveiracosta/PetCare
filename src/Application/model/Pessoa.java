package model;

import java.time.LocalDateTime;

public abstract class Pessoa {
    private int id;
    private String nome;
    private LocalDateTime dataNascimento;
    private String cpf;
    private String telefone;
    public Pessoa(String nome, LocalDateTime dataNascimento, String cpf, String Telefone){

    }
    public int getId(){
        return id;
    }


}
