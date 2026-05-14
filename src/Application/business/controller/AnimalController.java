package business.controller;

import business.ControllerAnimal;
import business.model.animal.Animal;

import java.util.List;

public class AnimalController implements IAnimalController {
    private final ControllerAnimal businessAnimal;

    public AnimalController(ControllerAnimal businessAnimal) {
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
