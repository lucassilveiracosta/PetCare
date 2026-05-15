package data.repository;

import business.model.Pessoas.Person;
import data.interfaces.IRepositoryPerson;
import exceptions.EmailConflictException;

import java.util.ArrayList;

public class RepositorioPessoa implements IRepositoryPerson {

    private final ArrayList<Person> pessoas;
    public RepositorioPessoa(ArrayList<Person> pessoas) {
        this.pessoas = pessoas;
    }


    @Override
    public Person findById(int id) {
        Person pessoa = null;
        for (Person p : pessoas) {
            if (p.getId() == id) {
                pessoa = p;
            }
        }
        return pessoa;
    }

    @Override
    public Person findByEmail(String email) {
        Person pessoa = null;
        for(Person p: pessoas) {
            if(p.getEmail().equals(email)) {
                pessoa = p;
            }
        }
        return pessoa;
    }

    @Override
    public ArrayList<Person> findAll() {
        return pessoas;
    }

    @Override
    public void update(int id, Person p) {
        for (int i = 0; i < pessoas.size(); i++) {
            if (pessoas.get(i).getId() == id) {
                pessoas.set(i, p);
                return;
            }
        }
    }

    @Override
    public void create(Person p) {
        Person exists = this.findByEmail(p.getEmail());
        if (exists != null) throw new EmailConflictException("Esse email ja existe");
        pessoas.add(p);
    }

    @Override
    public void remove(Person p) {
        if (p != null) {
            pessoas.remove(p);
        }
    }
}
