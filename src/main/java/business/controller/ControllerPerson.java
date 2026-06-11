package business.controller;

import business.interfaces.IControllerPerson;

import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.person.Employee;
import business.model.person.Owner;
import business.model.person.Veterinarian;
import data.SaveData;
import data.interfaces.IRepositoryAnimal;
import exceptions.EmailNotFoundException;
import exceptions.PersonConflictException;
import exceptions.PersonNotFoundException;
import business.model.person.Person;
import data.interfaces.IRepositoryPerson;
import org.apache.commons.validator.routines.EmailValidator;


import java.util.ArrayList;

public class ControllerPerson implements IControllerPerson {

    private IRepositoryPerson repositoryPerson;
    private IRepositoryAnimal repositoryAnimal;

    public ControllerPerson(IRepositoryPerson repositoryPerson) {
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
        persist();
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        Person p = repositoryPerson.findById(id);
        if (p == null) throw new PersonNotFoundException("404 - ID not found");

        repositoryPerson.remove(p);
        persist();
    }

    public void post(Person p) {
        Person exists = repositoryPerson.findById(p.getId());
        if (exists != null) throw new PersonConflictException("409 - This Person already exists");
        repositoryPerson.create(p);
        persist();
    }

    private void persist() {
        new data.SaveData().saveAllPersons(repositoryPerson.findAll());
    }

    @Override
    public ArrayList<Person> filterByName(String name) {
        return repositoryPerson.filterByName(name);
    }

    /**
     *
     * @param email
     * @return Essa função retorna todos os animais domésticos relacionados ao dono do email selecionado
     */
    public ArrayList<Animal> filterOwnersByEmail(String email) {
        ArrayList<Animal> filter = new ArrayList<>();

        for (Animal a: repositoryAnimal.findAll()) {
            if (a instanceof DomesticAnimal da) {
                if (da.getOwner().getEmail().contains(email)) {
                    filter.add(a);
                }
            }
        }

        return filter;
    }

    public ArrayList<Veterinarian> getAllVets(){
        ArrayList<Veterinarian> filter = new ArrayList<>();

        for (Person p: repositoryPerson.findAll()) {
            if (p instanceof Veterinarian vet) {
                filter.add(vet);
            }
        }
        return filter;
    }

    public ArrayList<Employee> getAllEmployees(){
        ArrayList<Employee> filter = new ArrayList<>();

        for (Person p: repositoryPerson.findAll()) {
            if (p instanceof Employee e) {
                filter.add(e);
            }
        }
        return filter;
    }

    public ArrayList<Owner> getAllOwners(){
        ArrayList<Owner> filter = new ArrayList<>();

        for (Person p: repositoryPerson.findAll()) {
            if (p instanceof Owner o) {
                filter.add(o);
            }
        }
        return filter;
    }
}