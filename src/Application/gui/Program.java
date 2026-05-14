package gui;

import business.BusinessAnimal;
import enums.FaseDaVida;
import model.animal.Animal;
import repository.RepositoryAnimal;
import interfaces.IRepositoryAnimal;
import enums.Porte;
import enums.Sexo;

import java.time.LocalDate;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        IRepositoryAnimal repo = new RepositoryAnimal();
        BusinessAnimal business = new BusinessAnimal(repo);

        try {
            System.out.println("--- Testando Cadastro de Animal com Auto-ID ---");

            // Criando animais (O ID é gerado internamente na classe Animal)
            // Ordem: nome, especie, raca, dataNascimento, peso, porte, sexo, tempoDeVida
            Animal pet1 = new Animal(
                    "Rex", "Cachorro", "Labrador",
                    LocalDate.of(2020, 5, 15), FaseDaVida.ADULTO, 25.5,
                    Porte.GRANDE, Sexo.MACHO
            );

            Animal pet2 = new Animal(
                    "Mingau", "Gato", "Siamês",
                    LocalDate.of(2022, 1, 10), FaseDaVida.ADULTO, 4.2,
                    Porte.PEQUENO, Sexo.MACHO
            );

            // Salvando
            business.post(pet1);
            business.post(pet2);

            System.out.println("Animais cadastrados com IDs: " + pet1.getId() + " e " + pet2.getId());

            // Listagem completa
            System.out.println("\n--- Lista de Animais no Sistema ---");
            List<Animal> lista = business.getAll();
            for (Animal a : lista) {
                System.out.println("ID [" + a.getId() + "] " + a.getNome() + " - " + a.getRaca());
            }

            // Teste de Busca por ID
            System.out.println("\nBuscando animal de ID 1...");
            Animal busca = business.getById(1);
            System.out.println("Encontrado: " + busca.getNome());

        } catch (Exception e) {
            System.err.println("Ocorreu um erro: " + e.getMessage());
        }
    }
}