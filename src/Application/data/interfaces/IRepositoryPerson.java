package data.interfaces;

import business.model.Pessoas.Person;

import java.util.ArrayList;

public interface IRepositoryPerson {

    Person findById(int id);
    Person findByEmail(String email);
    ArrayList<Person> findAll();
    void update (int index, Person p);
    void create (Person p);
    void remove (Person p);
}
