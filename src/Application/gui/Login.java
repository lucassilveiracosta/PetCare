package gui;

import business.interfaces.IControllerPessoa;
import business.model.Pessoas.Dono;
import business.model.Pessoas.Pessoa;
import business.model.Pessoas.ResponsavelPagador;
import business.model.Pessoas.Veterinario;
import business.model.Pessoas.Funcionario;

import enums.TypePerson;
import exceptions.ClassPersonNotExists;
import exceptions.LoginConflictException;
import exceptions.WrongPasswordException;

public class Login {

    private IControllerPessoa controllerPessoa;


    public Login(IControllerPessoa controllerPessoa) {
        this.controllerPessoa = controllerPessoa;
    }

    public Pessoa logar(String email, String password) {
        Pessoa emailPessoa = controllerPessoa.getByEmail(email);

        if (emailPessoa == null || !(emailPessoa.getPassword().equals(password))) {
            throw new WrongPasswordException("E-mail ou senha incorretos");
        }

        return emailPessoa;
    }

    public TypePerson loginPersonType(Pessoa person) {

        if (person instanceof Veterinario) {
            return TypePerson.VETERINARIO;
        }
        else if (person instanceof Dono) {
            return TypePerson.DONO;
        }
        else if (person instanceof ResponsavelPagador) {
            return TypePerson.RESPONSAVEL_PAGADOR;
        }
        else if (person instanceof Funcionario) {
            return TypePerson.FUNCIONARIO;
        }
        else {
            throw new ClassPersonNotExists("This person doesn't have a subclass");
        }
    }
}
