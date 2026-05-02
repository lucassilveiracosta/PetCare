package repository.Interface;

import model.animal.Animal;
import java.util.List;
public interface IRepositorioAnimal {
    void cadastrar(Animal animal);
    Animal buscar(int id);
    List<Animal> listarTodos();
    void  atualizar(Animal animal);
    void excluir(int id);

}