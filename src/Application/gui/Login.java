package gui;

import business.interfaces.IControllerPessoa;
import business.model.Pessoas.Pessoa;
import exceptions.LoginConflictException;

public class Login {

    private IControllerPessoa controllerPessoa;


    public Login(IControllerPessoa controllerPessoa) {
        this.controllerPessoa = controllerPessoa;
    }

    public Pessoa logar(String email, String password) {
        Pessoa emailPessoa = controllerPessoa.getByEmail(email);

        if (emailPessoa == null) {
            throw new LoginConflictException("Usuário não encontrado");
        }

        if (!(emailPessoa.getPassword().equals(password))) {
            throw new LoginConflictException("Wrong password");
        }

        return emailPessoa;
    }
    
}
