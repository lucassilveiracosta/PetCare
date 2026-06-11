package business.controller;

import business.interfaces.IControllerHospitalization;
import business.model.invoice.Hospitalization;
import data.SaveData;
import data.interfaces.IRepositoryHospitalization;
import exceptions.AppointmentConflictException;
import exceptions.AppointmentNotFoundException;

import java.util.ArrayList;

public class ControllerHospitalization implements IControllerHospitalization {
    private final IRepositoryHospitalization repositoryHospitalization;

    public ControllerHospitalization(IRepositoryHospitalization repository) {
        this.repositoryHospitalization = repository;
    }

    @Override
    public Hospitalization getById(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        Hospitalization hospitalization = repositoryHospitalization.findById(id);

        if (hospitalization == null) throw new AppointmentNotFoundException("404 - ID not found");

        return hospitalization;
    }

    @Override
    public ArrayList<Hospitalization> getAll() {
        return repositoryHospitalization.findAll();
    }


    @Override
    public void patch(int id, Hospitalization partialData) {

        if (partialData == null) throw new IllegalArgumentException("400 - Hospitalization can't be null");

        Hospitalization exists = repositoryHospitalization.findById(id);
        if (exists == null) {
            throw new AppointmentNotFoundException("404 - Hospitalization with ID " + id + " not found");
        }
        /*
        para colocar por partes
         */


        int index = repositoryHospitalization.findAll().indexOf(exists);
        repositoryHospitalization.update(index, exists);
        persist();
    }

    @Override
    public void put(int id, Hospitalization newHospitalziation) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        if (newHospitalziation == null) throw new IllegalArgumentException("400 - Hospitalization can't be null");

        Hospitalization exists = repositoryHospitalization.findById(id);
        if (exists == null) throw new AppointmentNotFoundException("404 - ID not found");

        repositoryHospitalization.update(id, newHospitalziation);
        persist();
    }

    @Override
    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");

        Hospitalization hospitalization = repositoryHospitalization.findById(id);
        if (hospitalization == null) throw new AppointmentNotFoundException("404 - ID not found");

        repositoryHospitalization.remove(hospitalization);
        persist();
    }

    @Override
    public void post(Hospitalization hospitalization) {
        if (hospitalization == null) throw new IllegalArgumentException("400 - Hospitalization cannot be null");

        Hospitalization exists = repositoryHospitalization.findById(hospitalization.getId());

        if (exists != null) {
            throw new AppointmentConflictException("409 - This hospitalization already exists");
        }

        repositoryHospitalization.create(hospitalization);
        persist();
    }

    private void persist() {
        new SaveData().saveAllHospitalizations(repositoryHospitalization.findAll());
    }
}
