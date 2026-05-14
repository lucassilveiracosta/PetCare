package repository.Interface;
import model.notaFiscal.Consulta;
import java.util.ArrayList;

public interface IRepositoryAppointment {
        Consulta findById(int id);
        ArrayList<Consulta> findAll();
        void update(int index, Consulta c);
        void create(Consulta c);
        void  remove(Consulta c);
}
