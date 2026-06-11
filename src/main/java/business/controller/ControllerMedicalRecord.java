package business.controller;

import business.interfaces.IControllerMedicalRecord;
import business.model.appointment.MedicalRecord;
import data.interfaces.IRepositoryMedicalRecord;
import exceptions.MedicalRecorConflictException;
import exceptions.MedicalRecordHasSurgeryException;
import exceptions.MedicalRecordNotFoundException;


import java.util.ArrayList;

public class ControllerMedicalRecord implements IControllerMedicalRecord {

    private final IRepositoryMedicalRecord repositoryMedicalRecord;

    public ControllerMedicalRecord(IRepositoryMedicalRecord repositoryMedicalRecord) {
        this.repositoryMedicalRecord = repositoryMedicalRecord;
    }

    public MedicalRecord getById(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        MedicalRecord medicalRecord = repositoryMedicalRecord.findById(id);

        if (medicalRecord == null) throw new MedicalRecordNotFoundException("404 - ID not found");

        return medicalRecord;
    }

    public ArrayList<MedicalRecord> getAll() {
        return repositoryMedicalRecord.findAll();
    }

    public void patch(int id, MedicalRecord medicalRecord) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        if (medicalRecord == null) throw new IllegalArgumentException("400 - MedicalRecord can't be null");
        MedicalRecord exists = repositoryMedicalRecord.findById(id);
        if (exists == null) throw new MedicalRecordNotFoundException("404 - ID not found");

        repositoryMedicalRecord.update(id, medicalRecord);
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        MedicalRecord exists = repositoryMedicalRecord.findById(id);
        if (exists == null) throw new MedicalRecordNotFoundException("404 - ID not found");
        if (!exists.getSurgeries().isEmpty()) throw new MedicalRecordHasSurgeryException("400 - Surgeries can't be deleted");
        repositoryMedicalRecord.remove(exists);
    }

    public void post(MedicalRecord medicalRecord) {
        MedicalRecord exists = repositoryMedicalRecord.findById(medicalRecord.getId());
        if (exists != null) throw new MedicalRecorConflictException("409 - This medicalRecord already exists");
        repositoryMedicalRecord.create(medicalRecord);
    }
}
