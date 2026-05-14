package business.interfaces;

import business.model.animal.Animal;
import java.util.List;

public interface IBusinessAnimal {
    Animal getById(int id);
    void patch(int id, Animal dadosParciais);
    List<Animal> getAll();
    void update(int id, Animal novosDados);
    void delete(int id);
    void post(Animal animal);
}
