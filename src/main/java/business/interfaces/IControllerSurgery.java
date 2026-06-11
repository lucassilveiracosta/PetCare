package business.interfaces;

import business.model.invoice.Surgery;

import java.util.List;

public interface IControllerSurgery {
    Surgery getById(int id);
    List<Surgery> getAll();
    void patch(int id, Surgery partialData);
    void put(int id, Surgery newSurgery);
    void delete(int id);
    void post(Surgery surgery);
}
