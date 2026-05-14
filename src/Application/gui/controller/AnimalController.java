package gui.controller;

import gui.controller.interfaces.IAnimalController;
import model.animal.Animal;

import java.util.ArrayList;

public class AnimalController implements IAnimalController {
    public Animal listAnimalById(int id){}
    public ArrayList<Animal> listAll(){}
    public void postAnimal(Animal animal){}
    public void updateAnimal(int id, Animal animal){}
    public void deleteAnimal(int id){}
}
