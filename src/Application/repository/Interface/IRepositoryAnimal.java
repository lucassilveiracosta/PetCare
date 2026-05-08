package repository.Interface;

import model.animal.Animal;
import java.util.List;
public interface IRepositoryAnimal {
    void create(Animal animal);
    Animal findById(int id);
    List<Animal> findAll();
    void  update( int index, Animal animal);


    void delete(int id);

}