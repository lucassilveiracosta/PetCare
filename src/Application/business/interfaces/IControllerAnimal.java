package business.interfaces;

import business.model.animal.Animal;
import business.model.animal.Vaccine;

import java.util.ArrayList;
import java.util.List;

public interface IControllerAnimal {
    Animal getById(int id);
    void patch(int id, Animal partialData);
    List<Animal> getAll();
    void update(int id, Animal newData);
    void delete(int id);
    void post(Animal animal);
    boolean checkIfHaveRabbiesVaccine(int id);
    ArrayList<Vaccine> expiredVaccines(int id);
    ArrayList<Vaccine> upToDateVaccines(int id);

}
