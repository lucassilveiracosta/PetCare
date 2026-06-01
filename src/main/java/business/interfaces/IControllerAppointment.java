package business.interfaces;
import java.util.ArrayList;

import business.model.appointment.Appointment;
import business.model.invoice.Procedure;
import enums.AppointmentStatus;

public interface IControllerAppointment {
    Procedure getById(int id);
    void patch(int id, Appointment partialData);
    void put(int id, Appointment newAppointment);
    ArrayList<Appointment> getAll();
    void delete(int id);
    void post(Appointment appointment);

    ArrayList<Appointment> filterByAppointmentStatus(AppointmentStatus status);
}
