package gui;

import business.interfaces.IControllerPerson;
import business.model.person.Person;

import java.util.List;
import java.util.Scanner;

public class CrudPessoaUI {
    private IControllerPerson controllerPessoa;
    private Scanner scanner;

    public CrudPessoaUI(IControllerPerson controllerPessoa) {
        this.controllerPessoa = controllerPessoa;
        this.scanner = new Scanner(System.in);
    }

    public void exibir() {
        while (true) {
            System.out.println("\n--- CRUD de Pessoas ---");
            System.out.println("1. Listar todos os usuários");
            System.out.println("2. Buscar usuário por ID");
            System.out.println("3. Excluir usuário (por ID)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            String opcao = scanner.nextLine();
            
            switch (opcao) {
                case "1":
                    listarPessoas();
                    break;
                case "2":
                    buscarPorId();
                    break;
                case "3":
                    deletarUsuario();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void listarPessoas() {
        List<Person> pessoas = controllerPessoa.getAll();
        System.out.println("\n-- Usuários Cadastrados --");
        if (pessoas.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            for (Person p : pessoas) {
                System.out.println("ID [" + p.getId() + "] " + p.getName() + " | Email: " + p.getEmail());
            }
        }
    }

    private void buscarPorId() {
        System.out.print("\nDigite o ID do usuário: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Person p = controllerPessoa.getById(id);
            if (p != null) {
                System.out.println("Usuário encontrado: " + p.getName() + " (" + p.getEmail() + ")");
                System.out.println("Telefone: " + p.getTelephone());
                System.out.println("CPF: " + p.getCpf());
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Digite um número.");
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    private void deletarUsuario() {
        System.out.print("\nDigite o ID do usuário que deseja excluir: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            controllerPessoa.delete(id);
            System.out.println("Usuário deletado com sucesso (se existia).");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Digite um número.");
        } catch (Exception e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}
