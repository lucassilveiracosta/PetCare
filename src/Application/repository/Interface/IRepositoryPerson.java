package repository.Interface;

import model.Pessoas.Pessoa;

import java.util.ArrayList;

public interface IRepositoryPerson {

    public Pessoa findById(int id);
    public ArrayList<Pessoa> findAll();
    public void update (int id, Pessoa p);
    public void create (Pessoa p);
    public void  remove (Pessoa p);
}
