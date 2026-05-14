package business;

import exceptions.PersonConflictException;
import exceptions.PersonNotFoundException;
import model.Pessoas.Pessoa;
import interfaces.IRepositoryPerson;

import java.util.ArrayList;

public class BusinessPessoa {

        private IRepositoryPerson repositoryPerson;

    public BusinessPessoa(IRepositoryPerson repositoryPerson) {
        this.repositoryPerson = repositoryPerson;
    }

    public Pessoa getById(int id) {
            if (id < 0) throw new IllegalArgumentException("ID must be positive");
            Pessoa nf = repositoryPerson.findById(id);

            if (nf == null) throw new PersonNotFoundException("404 - ID not found");

            return nf;
        }

        public ArrayList<Pessoa> getAll() {
            return repositoryPerson.findAll();
        }

        public void patch(int id, Pessoa p) {
            if (id < 0) throw new IllegalArgumentException("ID must be positive");
            if (p == null) throw new IllegalArgumentException("Person can't be null");
            Pessoa pessoa = repositoryPerson.findById(id);
            if (pessoa == null) throw new PersonNotFoundException("404 - ID not found");

            repositoryPerson.update(id, p);
        }

        public void delete(int id) {
            if (id < 0) throw new IllegalArgumentException("ID must be positive");
            Pessoa p = repositoryPerson.findById(id);
            if (p == null) throw new PersonNotFoundException("404 - ID not found");

            repositoryPerson.remove(p);
        }

        public void post(Pessoa p) {
            Pessoa exists = repositoryPerson.findById(p.getId());
            if (p == exists) throw new PersonConflictException("This Person already exists");
            repositoryPerson.create(p);
        }
    }