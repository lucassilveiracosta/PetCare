package gui;

import business.interfaces.IControllerPessoa;
import business.model.Pessoas.Person;
import enums.TypePerson;

import java.util.Scanner;

public class MenuPrincipal {
    private Person usuarioLogado;
    private TypePerson tipo;
    private IControllerPessoa controllerPessoa;
    private Scanner scanner;

    public MenuPrincipal(Person usuarioLogado, TypePerson tipo, IControllerPessoa controllerPessoa) {
        this.usuarioLogado = usuarioLogado;
        this.tipo = tipo;
        this.controllerPessoa = controllerPessoa;
        this.scanner = new Scanner(System.in);
    }

    public void exibir() {
        CrudPessoaUI crudPessoaUI = new CrudPessoaUI(controllerPessoa);
        
        while (true) {
            boolean temAcessoCrud = (tipo == TypePerson.FUNCIONARIO || tipo == TypePerson.VETERINARIO);

            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Ver meu perfil");
            
            if (temAcessoCrud) {
                System.out.println("2. Gerenciar Usuários (CRUD Pessoas)");
            }
            
            System.out.println("0. Sair (Logout)");
            System.out.print("Escolha uma opção: ");
            
            String opcao = scanner.nextLine();
            
            switch (opcao) {
                case "1":
                    exibirPerfil();
                    break;
                case "2":
                    if (temAcessoCrud) {
                        crudPessoaUI.exibir();
                    } else {
                        System.out.println("Opção inválida ou acesso negado! Tente novamente.");
                    }
                    break;
                case "0":
                    System.out.println("Fazendo logout...");
                    return; // Volta para a tela de login
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void exibirPerfil() {
        System.out.println("\n-- Meu Perfil --");
        System.out.println("Nome: " + usuarioLogado.getName());
        System.out.println("Email: " + usuarioLogado.getEmail());
        System.out.println("Telefone: " + usuarioLogado.getTelephone());
        System.out.println("CPF: " + usuarioLogado.getCpf());
        System.out.println("Tipo de Conta: " + tipo);
    }
}
