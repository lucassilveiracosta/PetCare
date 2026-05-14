package business;

import exceptions.AppointmentConflictException;
import exceptions.AppointmentNotFoundException;
import model.notaFiscal.Consulta;
import repository.Interface.IRepositoryAppointment;

import java.util.ArrayList;

public class BusinessAppointment {

    private final IRepositoryAppointment repositoryAppointment;

    public BusinessAppointment(IRepositoryAppointment repository) {
        this.repositoryAppointment = repository;
    }

    public Consulta getById(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");
        Consulta consulta = repositoryAppointment.findById(id);

        if (consulta == null) throw new AppointmentNotFoundException("404 - ID not found");

        return consulta;
    }

    public ArrayList<Consulta> getAll() {
        return repositoryAppointment.findAll();
    }

    public void patch(int id, Consulta novaConsulta) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");
        if (novaConsulta == null) throw new IllegalArgumentException("Appointment can't be null");

        Consulta existente = repositoryAppointment.findById(id);
        if (existente == null) throw new AppointmentNotFoundException("404 - ID not found");

        repositoryAppointment.update(id, novaConsulta);
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");

        Consulta consulta = repositoryAppointment.findById(id);
        if (consulta == null) throw new AppointmentNotFoundException("404 - ID not found");

        repositoryAppointment.remove(consulta);
    }

    public void post(Consulta novaConsulta) {
        if (novaConsulta == null) throw new IllegalArgumentException("\n" + "\"Appointment cannot be null and void.\"");

        Consulta exists = repositoryAppointment.findById(novaConsulta.getId());

        if (exists != null) {
            throw new AppointmentConflictException("This appointment already exists");
        }

        repositoryAppointment.create(novaConsulta);
    }
}