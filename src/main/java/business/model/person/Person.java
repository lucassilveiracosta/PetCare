package business.model.person;

import exceptions.EmailFormatException;
import exceptions.PasswordException;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;

// Classe abstrata pessoa
public abstract class Person {

    protected static int countId = 1;

    protected int id = countId++;
    protected String name;
    protected String email;
    protected String password;
    protected LocalDate birthDate;
    protected String cpf;
    protected String telephone;

    // Construtor de pessoa
    public Person(String name, String email, String password, LocalDate birthDate, String cpf, String telephone){
        setName(name);
        setEmail(email);
        setPassword(password);
        setBirthDate(birthDate);
        setCpf(cpf);
        setTelephone(telephone);

    }



    // Metodo get do ID
    public int getId(){
        return id;
    }
    
    // Metodo set do ID (usado ao carregar do CSV)
    public void setId(int id){
        this.id = id;
        if (id >= countId) {
            countId = id + 1;
        }
    }
    // Metodo get do Nome
    public String getName(){
        return name;
    }
    // Metodo Set do name
    public void setName(String name){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("400 - Invalid name");
        }
        this.name = name;
    }
    // Metodo get da data de nascimento
    public LocalDate getBirthDate(){
        return birthDate;
    }
    // Metodo set da data de nascimento
    private void setBirthDate(LocalDate birthDate){
        if(birthDate == null){
            throw new IllegalArgumentException("400 - Invalid birthDate");
        }
        this.birthDate = birthDate;
    }
    // Metodo get de Cpf
    public String getCpf(){
        return cpf;
    }
    // Metodo Set de Cpf
    private void setCpf(String cpf){
        if(cpf == null || cpf.isBlank()){
            throw new IllegalArgumentException("400 - Invalid CPF");
        }
        this.cpf = cpf;
    }
    // Metodo get de Telefone
    public String getTelephone(){
        return telephone;
    }
    // Metodo set de Telefone
    public void setTelephone(String telephone){
        if(telephone == null || telephone.isBlank()){
            throw new IllegalArgumentException("400 - Invalid telephone");
        }
        this.telephone = telephone;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(email)) throw new EmailFormatException("400 - Email must be in email format");

        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 8) {
            throw new PasswordException("400 - Password must be 8 or more characters");
        }
        this.password = password;
    }



}
