package business.model.Pessoas;

import exceptions.PasswordException;

import java.time.LocalDate;
// Classe abstrata pessoa
public abstract class Pessoa {

    protected static int contadorId = 1;

    protected int id;
    protected String nome;
    protected String email;
    protected String password;
    protected LocalDate dataNascimento;
    protected String cpf;
    protected String telefone;

    // Construtor de pessoa
    public Pessoa(String nome, String email, String password, LocalDate dataNascimento, String cpf, String telefone){
        this.id = contadorId++; // pensei nessa forma de passar o Id
        setNome(nome);
        setEmail(email);
        setPassword(password);
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
    public void setNome(String nome){
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
    public void setTelefone(String telefone){
        if(telefone == null || telefone.isBlank()){
            throw new IllegalArgumentException("Número inválido!");
        }
        this.telefone = telefone;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou ficar em branco");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 8) {
            throw new PasswordException("Password must be 8 or more characters");
        }
        this.password = password;
    }



}
