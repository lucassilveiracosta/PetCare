package gui;

import business.interfaces.IControllerPessoa;
import business.model.Pessoas.Person;
import business.model.Pessoas.Veterinarian;
import business.model.Pessoas.Employee;

import enums.TypePerson;
import exceptions.ClassPersonNotExists;
import exceptions.WrongPasswordOrEmailException;

public class Login {

    private IControllerPessoa controllerPessoa;


    public Login(IControllerPessoa controllerPessoa) {
        this.controllerPessoa = controllerPessoa;
    }

    public Person logar(String email, String password) {
        Person emailPessoa = controllerPessoa.getByEmail(email);

        if (emailPessoa == null || !(emailPessoa.getPassword().equals(password))) {
            throw new WrongPasswordOrEmailException("E-mail ou senha incorretos");
        }

        return emailPessoa;
    }

    public TypePerson loginPersonType(Person person) {

        return switch (person) {
            case Veterinarian veterinario -> TypePerson.VETERINARIO;
            case Employee funcionario -> TypePerson.FUNCIONARIO;
            case null, default -> throw new ClassPersonNotExists("This person doesn't have a subclass");
        };
    }
}
