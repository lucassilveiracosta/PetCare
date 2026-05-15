package business.controller;

import business.interfaces.IControllerAnimal;

import exceptions.AnimalConflictException;
import exceptions.AnimalNotFoundException;
import business.model.animal.Animal;
import data.interfaces.IRepositoryAnimal;

import java.util.List;

public class ControllerAnimal implements IControllerAnimal {
    private final IRepositoryAnimal repositoryAnimal;


    public ControllerAnimal(IRepositoryAnimal repositoryAnimal) {
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

    public void patch(int id, Animal partialData) {

        Animal animalExists = repositoryAnimal.findById(id);
        if (animalExists == null) {
            throw new AnimalNotFoundException("404 - Animal com ID " + id + " não encontrado.");
        }



        if (partialData.getNome() != null && !partialData.getNome().isBlank()) {
            animalExists.setNome(partialData.getNome());
        }

        if (partialData.getEspecie() != null && !partialData.getEspecie().isBlank()) {
            animalExists.setEspecie(partialData.getEspecie());
        }

        if (partialData.getRaca() != null && !partialData.getRaca().isBlank()) {
            animalExists.setRaca(partialData.getRaca());
        }

        if (partialData.getPeso() > 0.0) {
            animalExists.setPeso(partialData.getPeso());
        }

        if (partialData.getPorte() != null) {
            animalExists.setPorte(partialData.getPorte());
        }

        if (partialData.getSexo() != null) {
            animalExists.setSexo(partialData.getSexo());
        }


        int index = repositoryAnimal.findAll().indexOf(animalExists);
        repositoryAnimal.update(index, animalExists);
    }

    public List<Animal> getAll() {
        return repositoryAnimal.findAll();
    }

    public void update(int id, Animal newData) {
        Animal old = repositoryAnimal.findById(id);
        if (old == null) throw new AnimalNotFoundException("404 - ID not found");

        // Garante que o objeto novo terá o mesmo ID do old
        newData.setId(id);

        int index = repositoryAnimal.findAll().indexOf(old);
        repositoryAnimal.update(index, newData);
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