package interfaces;

import model.animal.Animal;

import java.util.List;

public interface IAnimalController {
    Animal listAnimalById(int id);
    List<Animal> listAll();
    void postAnimal(Animal animal);
    void updateAnimal(int id, Animal animal);
    void deleteAnimal(int id);
}
