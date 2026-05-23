package business.interfaces;

import business.model.invoice.Hospitalization;
import business.model.invoice.Procedure;

import java.util.ArrayList;

public interface IControllerHospitalization {

    Procedure getById(int id);
    void patch(int id, Hospitalization partialData);
    void put(int id, Hospitalization newHospitalization);
    ArrayList<Hospitalization> getAll();
    void delete(int id);
    void post(Hospitalization hospitalization);
}
