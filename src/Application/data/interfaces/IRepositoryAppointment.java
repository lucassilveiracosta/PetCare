package data.interfaces;
import business.model.notaFiscal.Consulta;
import java.util.ArrayList;

public interface IRepositoryAppointment {
        Consulta findById(int id);
        ArrayList<Consulta> findAll();
        void update(int index, Consulta c);
        void create(Consulta c);
        void remove(Consulta c);
}
