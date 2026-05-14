package data.interfaces;

import business.model.Pessoas.Pessoa;

import java.util.ArrayList;

public interface IRepositoryPerson {

    Pessoa findById(int id);
    Pessoa findByEmail(String email);
    ArrayList<Pessoa> findAll();
    void update (int index, Pessoa p);
    void create (Pessoa p);
    void remove (Pessoa p);
}
