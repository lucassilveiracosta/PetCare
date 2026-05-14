package gui;

import business.interfaces.IControllerPessoa;
import business.model.Pessoas.Dono;
import business.model.Pessoas.Pessoa;
import business.model.Pessoas.Veterinario;
import business.model.Pessoas.Funcionario;

import enums.TypePerson;
import exceptions.ClassPersonNotExists;
import exceptions.WrongPasswordOrEmailException;

public class Login {

    private IControllerPessoa controllerPessoa;


    public Login(IControllerPessoa controllerPessoa) {
        this.controllerPessoa = controllerPessoa;
    }

    public Pessoa logar(String email, String password) {
        Pessoa emailPessoa = controllerPessoa.getByEmail(email);

        if (emailPessoa == null || !(emailPessoa.getPassword().equals(password))) {
            throw new WrongPasswordOrEmailException("E-mail ou senha incorretos");
        }

        return emailPessoa;
    }

    public TypePerson loginPersonType(Pessoa person) {

        return switch (person) {
            case Veterinario veterinario -> TypePerson.VETERINARIO;
            case Dono dono -> TypePerson.DONO;
            case Funcionario funcionario -> TypePerson.FUNCIONARIO;
            case null, default -> throw new ClassPersonNotExists("This person doesn't have a subclass");
        };
    }
}
