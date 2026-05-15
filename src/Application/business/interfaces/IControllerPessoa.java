package business.interfaces;

import business.model.Pessoas.Person;
import java.util.List;

public interface IControllerPessoa {
    Person getByEmail(String email);
    Person getById(int id);
    List<Person> getAll();
    void patch(int id, Person p);
    void delete(int id);
    void post(Person p);
}
