package business.controller;

import business.interfaces.IControllerAppointment;
import business.model.appointment.Appointment;
import data.SaveData;
import data.interfaces.IRepositoryAppointment;
import exceptions.AppointmentConflictException;
import exceptions.AppointmentNotFoundException;
import exceptions.MedicalRecordDeletionException;
import exceptions.ScheduleConflictException;
import enums.AppointmentStatus;


import java.util.ArrayList;
import java.util.Calendar;

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



        if (partialData.getDiagnosis() != null && !partialData.getDiagnosis().isBlank()) {
            exists.setDiagnosis(partialData.getDiagnosis());
        }

        if (partialData.getMedicalPrescription() != null && !partialData.getMedicalPrescription().isBlank()) {
            exists.setMedicalPrescription(partialData.getMedicalPrescription());
        }

        if (partialData.getResponsibleVeterinarian() != null) {
            exists.setResponsibleVeterinarian(partialData.getResponsibleVeterinarian());
        }

        if (partialData.getPrice() > 0.0) {
            exists.setPrice(partialData.getPrice());
        }

        if (partialData.getPatient() != null) {
            exists.setPatient(partialData.getPatient());
        }

        if (partialData.getDateHourScheduled() != null) {
            exists.setDateHourScheduled(partialData.getDateHourScheduled());
        }


        int index = repositoryAppointment.findAll().indexOf(exists);
        repositoryAppointment.update(index, exists);
        persist();
    }

    @Override
    public void put(int id, Appointment newAppointment) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        if (newAppointment == null) throw new IllegalArgumentException("400 - Appointment can't be null");

        Appointment exists = repositoryAppointment.findById(id);
        if (exists == null) throw new AppointmentNotFoundException("404 - ID not found");

        repositoryAppointment.update(id, newAppointment);
        persist();
    }

    @Override
    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");

        Appointment appointment = repositoryAppointment.findById(id);
        if (appointment == null) throw new AppointmentNotFoundException("404 - ID not found");

        // REQ17
        if (appointment.getDiagnosis() != null && !appointment.getDiagnosis().isBlank()) {
            throw new MedicalRecordDeletionException("Deletion blocked: the medical record of "
                    + appointment.getPatient().getName() + " has clinical registries and cannot be deleted.");
        }

        repositoryAppointment.remove(appointment);
        persist();
    }

    @Override
    public void post(Appointment appointment) {
        if (appointment == null) throw new IllegalArgumentException("400 - Appointment cannot be null");

        Appointment exists = repositoryAppointment.findById(appointment.getId());

        if (exists != null) {
            throw new AppointmentConflictException("409 - This appointment already exists");
        }

        validateNoScheduleConflict(appointment); // REQ04

        repositoryAppointment.create(appointment);
        persist();
    }

    /**
     * REQ04 - A veterinarian cannot have two appointments at the same date/time.
     *
     * @throws ScheduleConflictException when the responsible vet is already booked.
     */
    private void validateNoScheduleConflict(Appointment appointment) {
        if (appointment.getResponsibleVeterinarian() == null || appointment.getDateHourScheduled() == null) return;
        int vetId = appointment.getResponsibleVeterinarian().getId();
        for (Appointment a : repositoryAppointment.findAll()) {
            if (a.getId() == appointment.getId()) continue;
            if (a.getResponsibleVeterinarian() != null
                    && a.getResponsibleVeterinarian().getId() == vetId
                    && appointment.getDateHourScheduled().equals(a.getDateHourScheduled())) {
                throw new ScheduleConflictException("Conflito de agenda: "
                        + appointment.getResponsibleVeterinarian().getName()
                        + " já tem um atendimento marcado nesse horário.");
            }
        }
    }

    private void persist() {
        new SaveData().saveAllAppointments(repositoryAppointment.findAll());
    }

    @Override
    public ArrayList<Appointment> filterByAppointmentStatus(AppointmentStatus status) {
        ArrayList<Appointment> filter = new ArrayList<>();

        for (Appointment appointment: repositoryAppointment.findAll()) {
            if (appointment.getStatus().equals(status)) {
                filter.add(appointment);
            }
        }

        return filter;
    }






}