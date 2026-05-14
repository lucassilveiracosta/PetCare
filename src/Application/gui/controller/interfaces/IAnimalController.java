package gui.controller.interfaces;

import model.animal.Animal;

import java.util.ArrayList;

public interface IAnimalController {
    Animal listAnimalById(int id);
    ArrayList<Animal> listAll();
    void postAnimal(Animal animal);
    void updateAnimal(int id, Animal animal);
    void deleteAnimal(int id);
}
