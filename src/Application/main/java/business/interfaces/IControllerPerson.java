package main.java.business.interfaces;


import main.java.business.model.animal.Animal;
import main.java.business.model.person.Person;

import java.util.ArrayList;
import java.util.List;

public interface IControllerPerson {
    Person getByEmail(String email);
    Person getById(int id);
    List<Person> getAll();
    void patch(int id, Person p);
    void delete(int id);
    void post(Person p);

    ArrayList<Person> filterByName(String name);
    ArrayList<Animal> filterOwnersByEmail(String email);
}
