package business.controller;

import business.model.notaFiscal.Consulta;
import data.interfaces.IRepositoryAppointment;
import exceptions.AppointmentConflictException;
import exceptions.AppointmentNotFoundException;



import java.util.ArrayList;

public class ControllerAppointment {

    private final IRepositoryAppointment repositoryAppointment;

    public ControllerAppointment(IRepositoryAppointment repository) {
        this.repositoryAppointment = repository;
    }

    public Consulta getById(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        Consulta appointment = repositoryAppointment.findById(id);

        if (appointment == null) throw new AppointmentNotFoundException("404 - ID not found");

        return appointment;
    }

    public ArrayList<Consulta> getAll() {
        return repositoryAppointment.findAll();
    }

    public void patch(int id, Consulta newAppointment) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        if (newAppointment == null) throw new IllegalArgumentException("400 - Appointment can't be null");

        Consulta exists = repositoryAppointment.findById(id);
        if (exists == null) throw new AppointmentNotFoundException("404 - ID not found");

        repositoryAppointment.update(id, newAppointment);
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");

        Consulta appointment = repositoryAppointment.findById(id);
        if (appointment == null) throw new AppointmentNotFoundException("404 - ID not found");

        repositoryAppointment.remove(appointment);
    }

    public void post(Consulta newAppointment) {
        if (newAppointment == null) throw new IllegalArgumentException("400 - Appointment cannot be null");

        Consulta exists = repositoryAppointment.findById(newAppointment.getId());

        if (exists != null) {
            throw new AppointmentConflictException("409 - This appointment already exists");
        }

        repositoryAppointment.create(newAppointment);
    }
}