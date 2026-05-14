package gui.controller;

import business.BusinessAnimal;
import gui.controller.interfaces.IAnimalController;
import model.animal.Animal;

import java.util.List;

public class AnimalController implements IAnimalController {
    private final BusinessAnimal businessAnimal;

    public AnimalController(BusinessAnimal businessAnimal) {
        this.businessAnimal = businessAnimal;
    }


    @Override
    public Animal listAnimalById(int id){
        return businessAnimal.getById(id);
    }

    @Override
    public List<Animal> listAll(){
        return businessAnimal.getAll();
    }

    @Override
    public void postAnimal(Animal animal){
        businessAnimal.post(animal);
    }

    @Override
    public void updateAnimal(int id, Animal animal){
        businessAnimal.patch(id, animal);
    }

    @Override
    public void deleteAnimal(int id){
        businessAnimal.delete(id);
    }
}
