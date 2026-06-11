package business.controller;

import business.interfaces.IControllerSurgery;
import business.model.invoice.Surgery;
import business.model.person.Specialty;
import business.model.person.Veterinarian;
import data.SaveData;
import data.interfaces.IRepositorySurgery;
import exceptions.InactiveSpecialtyException;

import java.util.ArrayList;

public class ControllerSurgery implements IControllerSurgery {

    private final IRepositorySurgery repositorySurgery;

    public ControllerSurgery(IRepositorySurgery repository) {
        this.repositorySurgery = repository;
    }

    @Override
    public Surgery getById(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        Surgery surgery = repositorySurgery.findById(id);
        if (surgery == null) throw new IllegalArgumentException("404 - Surgery with ID " + id + " not found");
        return surgery;
    }

    @Override
    public ArrayList<Surgery> getAll() {
        return repositorySurgery.findAll();
    }

    @Override
    public void patch(int id, Surgery partialData) {
        if (partialData == null) throw new IllegalArgumentException("400 - Surgery can't be null");
        Surgery exists = repositorySurgery.findById(id);
        if (exists == null) throw new IllegalArgumentException("404 - Surgery with ID " + id + " not found");
        repositorySurgery.update(id, partialData);
        persist();
    }

    @Override
    public void put(int id, Surgery newSurgery) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        if (newSurgery == null) throw new IllegalArgumentException("400 - Surgery can't be null");
        Surgery exists = repositorySurgery.findById(id);
        if (exists == null) throw new IllegalArgumentException("404 - Surgery with ID " + id + " not found");
        repositorySurgery.update(id, newSurgery);
        persist();
    }

    @Override
    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        Surgery surgery = repositorySurgery.findById(id);
        if (surgery == null) throw new IllegalArgumentException("404 - Surgery with ID " + id + " not found");
        repositorySurgery.remove(surgery);
        persist();
    }

    @Override
    public void post(Surgery surgery) {
        if (surgery == null) throw new IllegalArgumentException("400 - Surgery can't be null");
        validateSurgeonSpecialty(surgery.getResponsibleVeterinarian()); // REQ18
        Surgery exists = repositorySurgery.findById(surgery.getId());
        if (exists != null) throw new IllegalArgumentException("409 - This surgery already exists");
        repositorySurgery.create(surgery);
        persist();
    }

    /**
     * REQ18 - A surgery can only be assigned to a veterinarian that has at least
     * one active (non-blank) specialty registered.
     *
     * @throws InactiveSpecialtyException when the surgeon has no active specialty.
     */
    private void validateSurgeonSpecialty(Veterinarian vet) {
        boolean hasActive = false;
        if (vet != null && vet.getSpecialties() != null) {
            for (Specialty s : vet.getSpecialties()) {
                if (s != null && s.getName() != null && !s.getName().isBlank()) {
                    hasActive = true;
                    break;
                }
            }
        }
        if (!hasActive) {
            throw new InactiveSpecialtyException("Cirurgia bloqueada: o veterinário "
                    + (vet != null ? vet.getName() : "")
                    + " não possui especialidade ativa cadastrada.");
        }
    }

    private void persist() {
        new SaveData().saveAllSurgeries(repositorySurgery.findAll());
    }
}
