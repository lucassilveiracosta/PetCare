package gui;

import business.interfaces.IControllerPessoa;
import business.model.person.Person;
import enums.TypePerson;
import exceptions.WrongPasswordOrEmailException;

import java.util.Scanner;

public class LoginUI {
    private IControllerPessoa controllerPessoa;
    private Scanner scanner;

    public LoginUI(IControllerPessoa controllerPessoa) {
        this.controllerPessoa = controllerPessoa;
        this.scanner = new Scanner(System.in);
    }

    public void exibir() {
        Login loginService = new Login(controllerPessoa);

        System.out.println("=================================");
        System.out.println("   SISTEMA PETCARE - LOGIN");
        System.out.println("=================================");
        
        while (true) {
            try {
                System.out.print("E-mail: ");
                String email = scanner.nextLine();
                
                System.out.print("Senha: ");
                String password = scanner.nextLine();

                Person pessoaLogada = loginService.logar(email, password);
                TypePerson tipo = loginService.loginPersonType(pessoaLogada);

                System.out.println("\nLogin efetuado com sucesso!");
                System.out.println("Bem-vindo(a), " + pessoaLogada.getName());
                System.out.println("Perfil: " + tipo + "\n");
                
                MenuPrincipal menu = new MenuPrincipal(pessoaLogada, tipo, controllerPessoa);
                menu.exibir();
                break;

            } catch (WrongPasswordOrEmailException e) {
                System.err.println("\nErro: " + e.getMessage());
                System.out.println("Tente novamente.\n");

            } catch (Exception e) {
                e.printStackTrace();
                // Removendo o break para permitir que tente logar novamente mesmo com erro genérico, 
                // a menos que queira fechar a aplicação
            }
        }
    }
}
