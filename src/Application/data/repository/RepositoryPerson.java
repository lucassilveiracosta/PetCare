package data.repository;

import business.model.person.Person;
import data.interfaces.IRepositoryPerson;
import exceptions.EmailConflictException;

import java.util.ArrayList;

public class RepositoryPerson implements IRepositoryPerson {

    private final ArrayList<Person> persons;
    public RepositoryPerson(ArrayList<Person> persons) {
        this.persons = persons;
    }


    @Override
    public Person findById(int id) {
        Person pessoa = null;
        for (Person p : persons) {
            if (p.getId() == id) {
                pessoa = p;
            }
        }
        return pessoa;
    }

    @Override
    public Person findByEmail(String email) {
        Person pessoa = null;
        for(Person p: persons) {
            if(p.getEmail().equals(email)) {
                pessoa = p;
            }
        }
        return pessoa;
    }

    @Override
    public ArrayList<Person> findAll() {
        return persons;
    }

    @Override
    public void update(int id, Person p) {
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getId() == id) {
                persons.set(i, p);
                return;
            }
        }
    }

    @Override
    public void create(Person p) {
        Person exists = this.findByEmail(p.getEmail());
        if (exists != null) throw new EmailConflictException("Esse email ja existe");
        persons.add(p);
    }

    @Override
    public void remove(Person p) {
        if (p != null) {
            persons.remove(p);
        }
    }

    public ArrayList<Person> filterByName(String name){
        ArrayList<Person> filter = new ArrayList<>();
        for (Person p: persons) {
            if (p.getName().contains(name)) {
                filter.add(p);
            }
        }

        return filter;
    }
}
