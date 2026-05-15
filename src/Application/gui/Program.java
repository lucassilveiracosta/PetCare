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
            Employee func = new Employee("Maria Func", "func@pet.com", "senha123", LocalDate.of(1995, 2, 2), "222.222.222-22", "8888-8888", "Atendente", "Manhã");
            Veterinarian vet = new Veterinarian("Ana Vet", "vet@pet.com", "senha123", LocalDate.of(1980, 4, 4), "444.444.444-44", "6666-6666", "CRMV-123", new ArrayList<>());
            Employee adm = new Employee("Admin", "admin@pet.com", "admin123", LocalDate.of(1980, 4, 4), "444.444.444-44","8888-8888", "Atendente", "Manhã"); // admin

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