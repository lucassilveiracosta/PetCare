package gui;

import business.interfaces.IControllerPerson;
import business.model.person.Person;
import business.model.person.Veterinarian;
import business.model.person.Employee;

import enums.TypePerson;
import exceptions.ClassPersonNotExists;
import exceptions.WrongPasswordOrEmailException;

public class Login {

    private IControllerPerson controllerPessoa;


    public Login(IControllerPerson controllerPessoa) {
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
