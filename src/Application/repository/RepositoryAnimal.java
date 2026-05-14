package repository;

import model.animal.Animal;
import interfaces.IRepositoryAnimal;
import java.util.ArrayList;
import java.util.List;

public class RepositoryAnimal implements IRepositoryAnimal {

    private final List<Animal> animais;

    public RepositoryAnimal() {
        this.animais = new ArrayList<>();
    }

    @Override
    public void create(Animal animal) {
        this.animais.add(animal);
    }

    @Override
    public Animal findById(int id) {
        for (Animal a : animais) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    @Override
    public List<Animal> findAll() {
        return new ArrayList<>(animais);
    }

    @Override
    public void update( int index, Animal animal) {
        this.animais.set(index, animal);

    }


    @Override
    public void delete(int id) {
        Animal a = findById(id);
        if (a != null) {
            animais.remove(a);
        }


    }
}
