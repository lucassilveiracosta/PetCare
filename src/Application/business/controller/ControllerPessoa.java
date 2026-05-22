package business.controller;

import business.interfaces.IControllerPessoa;

import exceptions.EmailNotFoundException;
import exceptions.PersonConflictException;
import exceptions.PersonNotFoundException;
import business.model.person.Person;
import data.interfaces.IRepositoryPerson;
import org.apache.commons.validator.routines.EmailValidator;


import java.util.ArrayList;

public class ControllerPessoa implements IControllerPessoa {

    private IRepositoryPerson repositoryPerson;

    public ControllerPessoa(IRepositoryPerson repositoryPerson) {
        this.repositoryPerson = repositoryPerson;
    }

    public Person getById(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        Person person = repositoryPerson.findById(id);

        if (person == null) throw new PersonNotFoundException("404 - ID not found");

        return person;
    }

    public Person getByEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        if(!validator.isValid(email)) throw new IllegalArgumentException("400 - Not in email format");
        Person exists = repositoryPerson.findByEmail(email);
        if(exists == null) throw new EmailNotFoundException("404 - Email not found");

        return exists;
    }

    public ArrayList<Person> getAll() {
        return repositoryPerson.findAll();
    }

    public void patch(int id, Person p) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        if (p == null) throw new IllegalArgumentException("400 - Person can't be null");
        Person person = repositoryPerson.findById(id);
        if (person == null) throw new PersonNotFoundException("404 - ID not found");

        repositoryPerson.update(id, p);
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        Person p = repositoryPerson.findById(id);
        if (p == null) throw new PersonNotFoundException("404 - ID not found");

        repositoryPerson.remove(p);
    }

    public void post(Person p) {
        Person exists = repositoryPerson.findById(p.getId());
        if (exists != null) throw new PersonConflictException("409 - This Person already exists");
        repositoryPerson.create(p);
    }
}