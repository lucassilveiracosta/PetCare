package data.repository;

import business.model.Pessoas.Pessoa;
import data.interfaces.IRepositoryPerson;
import exceptions.EmailNotFoundException;

import java.util.ArrayList;

public class RepositorioPessoa implements IRepositoryPerson {

    private final ArrayList<Pessoa> pessoas;
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
    public Pessoa findByEmail(String email) {
        Pessoa pessoa = null;
        for(Pessoa p: pessoas) {
            if(p.getEmail().equals(email)) {
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
    public void update(int index, Pessoa p) {
        pessoas.set(index, p);
    }

    @Override
    public void create(Pessoa p) {
        Pessoa exists = this.findByEmail(p.getEmail());
        if (exists != null) throw new EmailNotFoundException("Esse email ja existe");
        pessoas.add(p);
    }

    @Override
    public void remove(Pessoa p) {
        if (p != null) {
            pessoas.remove(p);
        }
    }
}
