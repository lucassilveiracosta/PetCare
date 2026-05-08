package business;

import exceptions.AnimalConflictException;
import exceptions.AnimalNotFoundException;
import model.animal.Animal;
import repository.Interface.IRepositoryAnimal;

import java.util.List;

public class BusinessAnimal {
    private IRepositoryAnimal repositoryAnimal;


    public BusinessAnimal(IRepositoryAnimal repositoryAnimal) {
        this.repositoryAnimal = repositoryAnimal;
    }

    public Animal getById(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");

        Animal animal = repositoryAnimal.findById(id);
        if (animal == null) {
            throw new AnimalNotFoundException("404 - Animal with ID " + id + " not found");
        }

        return animal;
    }

    public List<Animal> getAll() {
        return repositoryAnimal.findAll();
    }

    public void update(int id, Animal novosDados) {
        Animal antigo = repositoryAnimal.findById(id);
        if (antigo == null) throw new AnimalNotFoundException("...");

        // Garante que o objeto novo terá o mesmo ID do antigo
        novosDados.setId(id);

        int index = repositoryAnimal.findAll().indexOf(antigo);
        repositoryAnimal.update(index, novosDados);
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");

        Animal animal = repositoryAnimal.findById(id);
        if (animal == null) {
            throw new AnimalNotFoundException("404 - Cannot delete: Animal not found");
        }

        repositoryAnimal.delete(id);
    }

    public void post(Animal animal) {
        if (animal == null) throw new IllegalArgumentException("Animal cannot be null");


        Animal exists = repositoryAnimal.findById(animal.getId());
        if (exists != null) {
            throw new AnimalConflictException("An animal with this ID already exists");
        }

        repositoryAnimal.create(animal);
    }
}