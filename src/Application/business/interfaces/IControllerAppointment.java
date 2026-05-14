package business.interfaces;
import java.util.List;
import business.model.notaFiscal.Procedimento;

public interface IControllerAppointment {
    Procedimento getById(int id);
    void patch(int id, Procedimento appointment);
    List<Procedimento> getAll();
    void update(int id, Procedimento appointment);
    void delete(int id);
    void post(Procedimento appointment);
}
