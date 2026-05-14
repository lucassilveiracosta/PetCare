package gui;

import business.controller.ControllerPessoa;
import business.interfaces.IControllerPessoa;
import business.model.Pessoas.*;
import data.repository.RepositorioPessoa;

import java.time.LocalDate;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        
        // 1. Instanciando Repositório e Controller
        RepositorioPessoa repoPessoa = new RepositorioPessoa(new ArrayList<>());
        IControllerPessoa controllerPessoa = new ControllerPessoa(repoPessoa);

        // 2. Mock de Dados (criando 1 usuário de cada tipo)
        try {
            Dono dono = new Dono("João Dono", "dono@pet.com", "senha123", LocalDate.of(1990, 1, 1), "111.111.111-11", "9999-9999", "Professor", "Ama cachorros");
            Funcionario func = new Funcionario("Maria Func", "func@pet.com", "senha123", LocalDate.of(1995, 2, 2), "222.222.222-22", "8888-8888", "Atendente", "Manhã");
            Veterinario vet = new Veterinario("Ana Vet", "vet@pet.com", "senha123", LocalDate.of(1980, 4, 4), "444.444.444-44", "6666-6666", "CRMV-123", new ArrayList<>());

            controllerPessoa.post(dono);
            controllerPessoa.post(func);
            controllerPessoa.post(vet);
            
        } catch (Exception e) {
            System.err.println("Aviso: Erro ao gerar dados mockados: " + e.getMessage());
        }

        // 3. Iniciando a Interface CLI
        LoginUI loginUI = new LoginUI(controllerPessoa);
        loginUI.exibir();
    }
}