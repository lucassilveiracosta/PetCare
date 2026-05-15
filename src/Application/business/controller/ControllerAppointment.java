package business.controller;

import business.interfaces.IControllerAppointment;
import business.model.notaFiscal.Appointment;
import data.interfaces.IRepositoryAppointment;
import exceptions.AppointmentConflictException;
import exceptions.AppointmentNotFoundException;



import java.util.ArrayList;

public class ControllerAppointment implements IControllerAppointment {

    private final IRepositoryAppointment repositoryAppointment;

    public ControllerAppointment(IRepositoryAppointment repository) {
        this.repositoryAppointment = repository;
    }

    @Override
    public Appointment getById(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        Appointment appointment = repositoryAppointment.findById(id);

        if (appointment == null) throw new AppointmentNotFoundException("404 - ID not found");

        return appointment;
    }

    @Override
    public ArrayList<Appointment> getAll() {
        return repositoryAppointment.findAll();
    }


    @Override
    public void patch(int id, Appointment partialData) {

        if (partialData == null) throw new IllegalArgumentException("400 - Appointment can't be null");

        Appointment exists = repositoryAppointment.findById(id);
        if (exists == null) {
            throw new AppointmentNotFoundException("404 - Appointment with ID " + id + " not found");
        }



        if (partialData.getDiagnostico() != null && !partialData.getDiagnostico().isBlank()) {
            exists.setDiagnostico(partialData.getDiagnostico());
        }

        if (partialData.getPrescricaoMedica() != null && !partialData.getPrescricaoMedica().isBlank()) {
            exists.setPrescricaoMedica(partialData.getPrescricaoMedica());
        }

        if (partialData.getVeterinarioResponsavel() != null) {
            exists.setVeterinarioResponsavel(partialData.getVeterinarioResponsavel());
        }

        if (partialData.getPreco() > 0.0) {
            exists.setPreco(partialData.getPreco());
        }

        if (partialData.getPaciente() != null) {
            exists.setPaciente(partialData.getPaciente());
        }

        if (partialData.getDataHora() != null) {
            exists.setDataHora(partialData.getDataHora());
        }


        int index = repositoryAppointment.findAll().indexOf(exists);
        repositoryAppointment.update(index, exists);
    }

    @Override
    public void put(int id, Appointment newAppointment) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        if (newAppointment == null) throw new IllegalArgumentException("400 - Appointment can't be null");

        Appointment exists = repositoryAppointment.findById(id);
        if (exists == null) throw new AppointmentNotFoundException("404 - ID not found");

        repositoryAppointment.update(id, newAppointment);
    }

    @Override
    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");

        Appointment appointment = repositoryAppointment.findById(id);
        if (appointment == null) throw new AppointmentNotFoundException("404 - ID not found");

        repositoryAppointment.remove(appointment);
    }

    @Override
    public void post(Appointment appointment) {
        if (appointment == null) throw new IllegalArgumentException("400 - Appointment cannot be null");

        Appointment exists = repositoryAppointment.findById(appointment.getId());

        if (exists != null) {
            throw new AppointmentConflictException("409 - This appointment already exists");
        }

        repositoryAppointment.create(appointment);
    }
}