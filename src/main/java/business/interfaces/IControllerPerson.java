package business.interfaces;

import business.model.animal.Animal;
import business.model.person.Employee;
import business.model.person.Owner;
import business.model.person.Person;
import business.model.person.Veterinarian;

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
    ArrayList<Veterinarian> getAllVets();
    ArrayList<Employee> getAllEmployees();
    ArrayList<Owner> getAllOwners();
}
