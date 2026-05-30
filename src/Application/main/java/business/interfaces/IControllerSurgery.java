package main.java.business.interfaces;



import main.java.business.model.invoice.Procedure;
import main.java.business.model.invoice.Surgery;

import java.util.ArrayList;

public interface IControllerSurgery {
    Procedure getById(int id);
    void patch(int id, Surgery partialData);
    void put(int id, Surgery newSurgery);
    ArrayList<Surgery> getAll();
    void delete(int id);
    void post(Surgery surgery);
}
