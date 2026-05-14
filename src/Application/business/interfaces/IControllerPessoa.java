package business.interfaces;

import business.model.Pessoas.Pessoa;
import java.util.List;

public interface IControllerPessoa {
    Pessoa getById(int id);
    List<Pessoa> getAll();
    void patch(int id, Pessoa p);
    void delete(int id);
    void post(Pessoa p);
}
