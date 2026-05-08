package business;

import exceptions.AnimalConflictException;
import exceptions.AnimalNotFoundException;
import model.animal.Animal;
import repository.Interface.IRepositoryAnimal;

import java.util.List;

public class Businessanimal {
    private IRepositoryAnimal repositoryAnimal;


    public Businessanimal(IRepositoryAnimal repositoryAnimal) {
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

    public void update(Animal animal) {
        if (animal == null) throw new IllegalArgumentException("Animal cannot be null");


        Animal exists = repositoryAnimal.findById(animal.getId());
        if (exists == null) {
            throw new AnimalNotFoundException("404 - Cannot update: Animal not found");
        }

        repositoryAnimal.update(animal);
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