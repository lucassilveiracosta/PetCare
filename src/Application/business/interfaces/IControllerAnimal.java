package business.interfaces;

import business.model.animal.Animal;
import java.util.List;

public interface IControllerAnimal {
    Animal getById(int id);
    void patch(int id, Animal partialData);
    List<Animal> getAll();
    void update(int id, Animal newData);
    void delete(int id);
    void post(Animal animal);
}
