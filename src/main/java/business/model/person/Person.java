package business.model.person;

import exceptions.EmailFormatException;
import exceptions.PasswordException;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;

// Abstract base class for every person in the system (owners, employees, vets).
public abstract class Person {

    protected static int idCounter = 1;

    protected int id = idCounter++;
    protected String name;
    protected String email;
    protected String password;
    protected LocalDate birthDate;
    protected String cpf;
    protected String telephone;

    public Person(String name, String email, String password, LocalDate birthDate, String cpf, String telephone){
        setName(name);
        setEmail(email);
        setPassword(password);
        setBirthDate(birthDate);
        setCpf(cpf);
        setTelephone(telephone);

    }



    public int getId(){
        return id;
    }

    // Used when loading records from the CSV database.
    public void setId(int id){
        this.id = id;
        if (id >= idCounter) {
            idCounter = id + 1;
        }
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("400 - Invalid name");
        }
        this.name = name;
    }

    public LocalDate getBirthDate(){
        return birthDate;
    }

    private void setBirthDate(LocalDate birthDate){
        if(birthDate == null){
            throw new IllegalArgumentException("400 - Invalid birthDate");
        }
        this.birthDate = birthDate;
    }

    public String getCpf(){
        return cpf;
    }

    private void setCpf(String cpf){
        if(cpf == null || cpf.isBlank()){
            throw new IllegalArgumentException("400 - Invalid CPF");
        }
        this.cpf = cpf;
    }

    public String getTelephone(){
        return telephone;
    }

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
