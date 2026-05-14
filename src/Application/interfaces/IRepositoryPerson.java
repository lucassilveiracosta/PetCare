package interfaces;

import model.Pessoas.Pessoa;

import java.util.ArrayList;

public interface IRepositoryPerson {

    Pessoa findById(int id);
    ArrayList<Pessoa> findAll();
    void update (int index, Pessoa p);
    void create (Pessoa p);
    void remove (Pessoa p);
}
