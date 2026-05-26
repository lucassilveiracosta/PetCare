package business.interfaces;

import business.model.invoice.Surgery;
import business.model.invoice.Procedure;

import java.util.ArrayList;

public interface IControllerSurgery {
    Procedure getById(int id);
    void patch(int id, Surgery partialData);
    void put(int id, Surgery newSurgery);
    ArrayList<Surgery> getAll();
    void delete(int id);
    void post(Surgery surgery);
}
