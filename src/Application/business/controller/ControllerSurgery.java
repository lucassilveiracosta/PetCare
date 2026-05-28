package business.controller;

import business.interfaces.IControllerSurgery;
import business.model.invoice.Surgery;
import data.interfaces.IRepositorySurgery;
import exceptions.SurgeryConflictException;
import exceptions.SurgeryNotFoundException;

import java.util.ArrayList;

public class ControllerSurgery implements IControllerSurgery {

    private final IRepositorySurgery respositorySurgery;

    public ControllerSurgery(IRepositorySurgery repository) {
        this.respositorySurgery = repository;
    }

    @Override
    public Surgery getById(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        Surgery surgery = respositorySurgery.findById(id);

        if (surgery == null) throw new SurgeryNotFoundException("404 - ID not found");

        return surgery;
    }

    @Override
    public ArrayList<Surgery> getAll() {
        return respositorySurgery.findAll();
    }


    @Override
    public void patch(int id, Surgery partialData) {

        if (partialData == null) throw new IllegalArgumentException("400 - Surgery can't be null");

        Surgery exists = respositorySurgery.findById(id);
        if (exists == null) {
            throw new SurgeryNotFoundException("404 - Surgery with ID " + id + " not found");
        }

        if (partialData.getResponsibleVeterinarians() != null) {
            exists.setResponsibleVeterinarians(partialData.getResponsibleVeterinarians());
        }

        if (partialData.getMaterials() != null) {
            exists.setMaterials(partialData.getMaterials());
        }

        if (partialData.getAnesthesiaType() != null && !partialData.getAnesthesiaType().isBlank()) {
            exists.setAnesthesiaType(partialData.getAnesthesiaType());
        }

        if (partialData.getSurgeryRisk() != null) {
            exists.setSurgeryRisk(partialData.getSurgeryRisk());
        }

        if (partialData.getPatient() != null) {
            exists.setPatient(partialData.getPatient());
        }

        if (partialData.getPrice() > 0.0) {
            exists.setPrice(partialData.getPrice());
        }

        if (partialData.getDateHour() != null) {
            exists.setDateHour(partialData.getDateHour());
        }

        int index = respositorySurgery.findAll().indexOf(exists);
        respositorySurgery.update(index, exists);
    }

    @Override
    public void put(int id, Surgery newSurgery) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        if (newSurgery == null) throw new IllegalArgumentException("400 - Surgery can't be null");

        Surgery exists = respositorySurgery.findById(id);
        if (exists == null) throw new SurgeryNotFoundException("404 - ID not found");

        respositorySurgery.update(id, newSurgery);
    }

    @Override
    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");

        Surgery surgery = respositorySurgery.findById(id);
        if (surgery == null) throw new SurgeryNotFoundException("404 - ID not found");

        respositorySurgery.remove(surgery);
    }

    @Override
    public void post(Surgery surgery) {
        if (surgery == null) throw new IllegalArgumentException("400 - Surgery cannot be null");

        Surgery exists = respositorySurgery.findById(surgery.getId());

        if (exists != null) {
            throw new SurgeryConflictException("409 - This surgery already exists");
        }

        respositorySurgery.create(surgery);
    }
}
