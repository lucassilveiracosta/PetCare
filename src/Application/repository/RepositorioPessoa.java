package repository;

import model.Pessoas.Pessoa;
import repository.Interface.IRepositoryPerson;

import java.util.ArrayList;

public class RepositorioPessoa implements IRepositoryPerson {

    private ArrayList<Pessoa> pessoas;
    public RepositorioPessoa(ArrayList<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }


    @Override
    public Pessoa findById(int id) {
        Pessoa pessoa = null;
        for (Pessoa p : pessoas) {
            if (p.getId() == id) {
                pessoa = p;
            }
        }
        return pessoa;
    }

    @Override
    public ArrayList<Pessoa> findAll() {
        return pessoas;
    }

    @Override
    public void update(int id, Pessoa p) {
        pessoas.set(id, p);
    }

    @Override
    public void create(Pessoa p) {
        pessoas.add(p);
    }

    @Override
    public void remove(Pessoa p) {
        if (p != null) {
            pessoas.remove(p);
        }
    }
}
