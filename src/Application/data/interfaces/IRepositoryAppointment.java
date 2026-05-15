package data.interfaces;
import business.model.prontuario.Appointment;
import java.util.ArrayList;

public interface IRepositoryAppointment {
        Appointment findById(int id);
        ArrayList<Appointment> findAll();
        void update(int index, Appointment c);
        void create(Appointment c);
        void remove(Appointment c);
}
