package business.interfaces;
import java.util.ArrayList;
import java.util.List;

import business.model.notaFiscal.Consulta;
import business.model.notaFiscal.Procedimento;

public interface IControllerAppointment {
    Procedimento getById(int id);
    void patch(int id, Consulta partialData);
    void put(int id, Consulta newAppointment);
    ArrayList<Consulta> getAll();
    void delete(int id);
    void post(Consulta appointment);
}
