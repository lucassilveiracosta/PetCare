package business.interfaces;

import business.model.animal.Animal;
import business.model.notaFiscal.Produto;
import java.util.List;

public interface IControllerStock {
    Produto getById(int id);
    List<Produto> getAll();
    void patch(int id, Animal partialData);
    void put(int id, Produto newProduct);
    void delete(int id);
    void post(Produto newProduct);
}
