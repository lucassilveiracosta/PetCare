package gui.tests;

import business.controller.ControllerAnimal;
import business.interfaces.IControllerAnimal;
import business.model.person.Owner;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.animal.Vaccine;
import data.repository.RepositoryAnimal;
import enums.Sex;
import enums.Size;
import enums.StageOfLife;
import enums.Temperament;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class ControllerAnimalTest {

    private static Scanner scanner = new Scanner(System.in);
    private static IControllerAnimal controller = new ControllerAnimal(new RepositoryAnimal());
    
    // Owner dummy para facilitar a criação
    private static Owner donoPadrao = new Owner("Dono Teste", "teste@email.com", "senha123", LocalDate.of(1990, 1, 1), "000", "000", "N/A", "N/A");

    public static void main(String[] args) {
        int opcao = -1;

        System.out.println("=== TESTE INTERATIVO: ControllerAnimal ===");

        while (opcao != 0) {
            System.out.println("\n--- MENU DE TESTES ---");
            System.out.println("1. Criar Animal (POST)");
            System.out.println("2. Listar Animais (GET ALL)");
            System.out.println("3. Buscar Animal (GET BY ID)");
            System.out.println("4. Atualização Parcial (PATCH)");
            System.out.println("5. Atualização Completa (UPDATE / PUT)");
            System.out.println("6. Excluir Animal (DELETE)");
            System.out.println("7. Testar Regras de Vacina (Rabbies e UpToDate)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Opção inválida.");
                continue;
            }

            try {
                switch (opcao) {
                    case 1:
                        testarPost();
                        break;
                    case 2:
                        testarGetAll();
                        break;
                    case 3:
                        testarGetById();
                        break;
                    case 4:
                        testarPatch();
                        break;
                    case 5:
                        testarUpdate();
                        break;
                    case 6:
                        testarDelete();
                        break;
                    case 7:
                        testarVacinas();
                        break;
                    case 0:
                        System.out.println("Encerrando testes.");
                        break;
                    default:
                        System.out.println("Opção inexistente.");
                }
            } catch (Exception e) {
                System.err.println("Erro durante a operação: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void testarPost() {
        System.out.println("\n-- Criando um Novo Animal --");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Espécie (ex: Cachorro): ");
        String especie = scanner.nextLine();
        System.out.print("Raça: ");
        String raca = scanner.nextLine();
        System.out.print("Peso (ex: 15.5): ");
        double peso = Double.parseDouble(scanner.nextLine());
        System.out.print("Porte (1. PEQUENO, 2. MEDIO, 3. GRANDE): ");
        int porteOp = Integer.parseInt(scanner.nextLine());
        Size porte = (porteOp == 1) ? Size.PEQUENO : (porteOp == 2) ? Size.MEDIO : Size.GRANDE;
        
        System.out.print("Sexo (1. MACHO, 2. FEMEA): ");
        int sexOp = Integer.parseInt(scanner.nextLine());
        Sex sexo = (sexOp == 1) ? Sex.MACHO : Sex.FEMEA;

        Animal novoAnimal = new DomesticAnimal(nome, especie, raca, LocalDate.now().minusYears(2), StageOfLife.ADULTO, peso, porte, sexo, donoPadrao, Temperament.DOCIL, false, new ArrayList<>());
        
        controller.post(novoAnimal);
        System.out.println("Animal criado com sucesso! ID gerado automaticamente: " + novoAnimal.getId());
    }

    private static void testarGetAll() {
        System.out.println("\n-- Listando Todos os Animais --");
        List<Animal> animais = controller.getAll();
        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado.");
            return;
        }
        for (Animal a : animais) {
            System.out.println("ID [" + a.getId() + "] " + a.getName() + " | Espécie: " + a.getSpecies() + " | Raça: " + a.getRace() + " | Peso: " + a.getWeight());
        }
    }

    private static void testarGetById() {
        System.out.print("\nDigite o ID do animal para buscar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Animal a = controller.getById(id);
        System.out.println("Encontrado: " + a.getName() + " (" + a.getSpecies() + ")");
    }

    private static void testarPatch() {
        System.out.println("\n-- Atualização Parcial (PATCH) --");
        System.out.print("Digite o ID do animal: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Animal a = controller.getById(id); // Confirma que existe
        System.out.println("Animal atual: " + a.getName());
        
        System.out.print("Novo Nome (deixe em branco para não alterar): ");
        String nome = scanner.nextLine();
        
        System.out.print("Novo Peso (digite 0 para não alterar): ");
        double peso = Double.parseDouble(scanner.nextLine());

        // Criando objeto parcial
        Animal parcial = new DomesticAnimal(null, null, null, null, null, 0.0, null, null, null, null, false, new ArrayList<>());
        if (!nome.isBlank()) parcial.setName(nome);
        if (peso > 0) parcial.setWeight(peso);

        controller.patch(id, parcial);
        System.out.println("Animal atualizado (PATCH) com sucesso!");
    }

    private static void testarUpdate() {
        System.out.println("\n-- Atualização Completa (UPDATE) --");
        System.out.print("Digite o ID do animal: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Animal a = controller.getById(id); // Confirma que existe
        System.out.println("Reescrevendo dados de: " + a.getName());
        
        System.out.print("Novo Nome OBRIGATÓRIO: ");
        String nome = scanner.nextLine();
        System.out.print("Nova Espécie OBRIGATÓRIA: ");
        String especie = scanner.nextLine();
        System.out.print("Nova Raça OBRIGATÓRIA: ");
        String raca = scanner.nextLine();
        
        Animal novoDado = new DomesticAnimal(nome, especie, raca, LocalDate.now().minusYears(1), StageOfLife.ADULTO, 10.0, Size.PEQUENO, Sex.MACHO, donoPadrao, Temperament.REATIVO, true, new ArrayList<>());
        controller.update(id, novoDado);
        System.out.println("Animal atualizado (UPDATE/PUT) com sucesso!");
    }

    private static void testarDelete() {
        System.out.print("\nDigite o ID do animal para excluir: ");
        int id = Integer.parseInt(scanner.nextLine());
        controller.delete(id);
        System.out.println("Animal excluído com sucesso!");
    }

    private static void testarVacinas() {
        System.out.println("\n-- Teste de Funções de Vacina --");
        System.out.print("Digite o ID do animal: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Animal a = controller.getById(id);
        System.out.println("Animal selecionado: " + a.getName());
        
        System.out.print("Deseja injetar vacinas de teste (uma válida da Raiva, uma vencida)? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            Vaccine vRaiva = new Vaccine("Anti-Rábica", LocalDate.now().minusMonths(1), "MarcaX", true, LocalDate.now().plusYears(1));
            Vaccine vEmDia = new Vaccine("Coronavac", LocalDate.now().minusDays(18), "MarcaX", false, LocalDate.now().plusDays(18));
            Vaccine vVencida = new Vaccine("V8", LocalDate.now().minusYears(2), "MarcaY", false, LocalDate.now().minusMonths(1));
            
            ArrayList<Vaccine> vacinas = a.getVaccines();
            vacinas.add(vRaiva);
            vacinas.add(vEmDia);
            vacinas.add(vVencida);
            a.setVaccines(vacinas);
            System.out.println("Vacinas injetadas na memória.");
        }

        boolean temRaiva = controller.checkIfHaveRabbiesVaccine(id);
        System.out.println("\n-> [1/3] Possui vacina da raiva válida? " + (temRaiva ? "SIM" : "NÃO"));

        ArrayList<Vaccine> vencidas = controller.expiredVaccines(id);
        System.out.println("-> [2/3] Vacinas VENCIDAS (expiredVaccines):");
        if (vencidas.isEmpty()) {
            System.out.println("   (Nenhuma vacina vencida)");
        } else {
            for (Vaccine v : vencidas) {
                System.out.println("   - " + v.getVaccineName() + " (Venceu em: " + v.getExpireVaccineDate() + ")");
            }
        }

        ArrayList<Vaccine> emDia = controller.closeToExpire(id);
        System.out.println("-> [3/3] Perto de Expirar (closeToExpire):");
        if (emDia.isEmpty()) {
            System.out.println("   (Tudo tranquilo!)");
        } else {
            for (Vaccine v : emDia) {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                System.out.println("   - " + v.getVaccineName() + " (Vence em: " + v.getExpireVaccineDate().format(fmt) + ")");
            }
        }


    }
}
