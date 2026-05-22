package business.controller;

import business.interfaces.IControllerAnimal;

import business.model.animal.Vaccine;
import exceptions.AnimalConflictException;
import exceptions.AnimalNotFoundException;
import business.model.animal.Animal;
import data.interfaces.IRepositoryAnimal;
import exceptions.RabbiesVaccineExpired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ControllerAnimal implements IControllerAnimal {
    private final IRepositoryAnimal repositoryAnimal;


    public ControllerAnimal(IRepositoryAnimal repositoryAnimal) {
        this.repositoryAnimal = repositoryAnimal;
    }

    @Override
    public Animal getById(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");

        Animal animal = repositoryAnimal.findById(id);
        if (animal == null) {
            throw new AnimalNotFoundException("404 - Animal with ID " + id + " not found");
        }

        return animal;
    }

    @Override
    public void patch(int id, Animal partialData) {

        if (partialData == null) throw new IllegalArgumentException("400 - Animal can't be null");

        Animal animalExists = repositoryAnimal.findById(id);
        if (animalExists == null) {
            throw new AnimalNotFoundException("404 - Animal com ID " + id + " não encontrado.");
        }



        if (partialData.getName() != null && !partialData.getName().isBlank()) {
            animalExists.setName(partialData.getName());
        }

        if (partialData.getSpecies() != null && !partialData.getSpecies().isBlank()) {
            animalExists.setSpecies(partialData.getSpecies());
        }

        if (partialData.getRace() != null && !partialData.getRace().isBlank()) {
            animalExists.setRace(partialData.getRace());
        }

        if (partialData.getWeight() > 0.0) {
            animalExists.setWeight(partialData.getWeight());
        }

        if (partialData.getSize() != null) {
            animalExists.setSize(partialData.getSize());
        }

        if (partialData.getSex() != null) {
            animalExists.setSex(partialData.getSex());
        }


        int index = repositoryAnimal.findAll().indexOf(animalExists);
        repositoryAnimal.update(index, animalExists);
    }

    @Override
    public List<Animal> getAll() {
        return repositoryAnimal.findAll();
    }

    @Override
    public void update(int id, Animal newData) {
        Animal old = repositoryAnimal.findById(id);
        if (old == null) throw new AnimalNotFoundException("404 - ID not found");

        // Garante que o objeto novo terá o mesmo ID do old
        newData.setId(id);

        int index = repositoryAnimal.findAll().indexOf(old);
        repositoryAnimal.update(index, newData);
    }

    @Override
    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");

        Animal animal = repositoryAnimal.findById(id);
        if (animal == null) {
            throw new AnimalNotFoundException("404 - Cannot delete: Animal not found");
        }

        repositoryAnimal.delete(id);
    }

    @Override
    public void post(Animal animal) {
        if (animal == null) throw new IllegalArgumentException("400 - Animal cannot be null");


        Animal exists = repositoryAnimal.findById(animal.getId());
        if (exists != null) {
            throw new AnimalConflictException("409 - An animal with this ID already exists");
        }

        repositoryAnimal.create(animal);
    }

    public boolean checkIfHaveRabbiesVaccine(int id) {
        boolean check = false;
        Animal animal = repositoryAnimal.findById(id);

        if (animal == null) throw new AnimalNotFoundException("404 - Animal not found");

        for (Vaccine vaccine: animal.getVaccines()) {
            if (vaccine.isRabbiesVaccine()) {
                if (vaccine.getExpireVaccineDate().isBefore(LocalDate.now())) {
                            // colocar possivel mensagem de atraso.
                    continue;
                }
                check = true;
            }
        }
        return check;
    }

    public ArrayList<Vaccine> expiredVaccines(int id) {
        Animal animal = repositoryAnimal.findById(id);

        if (animal == null) throw new AnimalNotFoundException("404 - Animal not found");

        ArrayList<Vaccine> localVaccines = new ArrayList<>();

        for (Vaccine vaccine: animal.getVaccines()){
            if (vaccine.getExpireVaccineDate().isBefore(LocalDate.now())) {
                localVaccines.add(vaccine);
            }
        }

        return localVaccines;
    }

    public ArrayList<Vaccine> upToDateVaccines(int id) {
        Animal animal = repositoryAnimal.findById(id);

        if (animal == null) throw new AnimalNotFoundException("404 - Animal not found");

        ArrayList<Vaccine> localVaccines = new ArrayList<>();

        for (Vaccine vaccine: animal.getVaccines()){
            if (vaccine.getExpireVaccineDate().isAfter(LocalDate.now()) && vaccine.getExpireVaccineDate().compareTo(LocalDate.now()) < 20) {
                localVaccines.add(vaccine);
            }
        }

        return localVaccines;
    }
}