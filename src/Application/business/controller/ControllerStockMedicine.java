package business.controller;

import business.interfaces.IControllerStockMedicine;
import data.interfaces.IRepositoryStockMedicine;
import business.model.medicine.Medicine;
import enums.MedicineType;
import exceptions.MedicineNoBatchException;
import exceptions.MedicineInsufficientStockException;
import java.util.ArrayList;
import java.util.List;

public class ControllerStockMedicine implements IControllerStockMedicine {

    private final IRepositoryStockMedicine repository;

    public ControllerStockMedicine(IRepositoryStockMedicine repository) {
        this.repository = repository;
    }

    @Override
    public void registerMedicine(Medicine medicine) {
        if (medicine == null) {
            throw new IllegalArgumentException("400 - Invalid medicine data");
        }

        if (medicine.getType() == MedicineType.CONTROLADO &&
                (medicine.getBatch() == null || medicine.getBatch().isBlank())) {
            throw new MedicineNoBatchException("400 - Controlled medicines require a valid batch number");
        }

        repository.addMedicine(medicine);
    }

    @Override
    public void writeOffStock(Medicine medicine, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("400 - Invalid quantity for deduction");
        }

        if (medicine.getQuantity() < quantity) {
            throw new MedicineInsufficientStockException("400 - Insufficient stock. Available: " + medicine.getQuantity());
        }

        medicine.setQuantity(medicine.getQuantity() - quantity);

    }

    @Override
    public List<Medicine> listAll() {

        return repository.getAllMedicines();
    }

    @Override
    public List<Medicine> listControlled() {
        List<Medicine> controlledMedicines = new ArrayList<>();
        for (Medicine med : repository.getAllMedicines()) {
            if (med.getType() == MedicineType.CONTROLADO) {
                controlledMedicines.add(med);
            }
        }
        return controlledMedicines;
    }
    public Medicine findById(int id) {
        return repository.findById(id);
    }

    public void updateMedicine(Medicine medicine) {
        if (medicine == null) {
            throw new IllegalArgumentException("400 - Invalid medicine data for update");
        }
        repository.updateMedicine(medicine);
    }

    public void removeMedicine(Medicine medicine) {
        if (medicine == null) {
            throw new IllegalArgumentException("400 - Invalid medicine data for removal");
        }
        repository.removeMedicine(medicine);
    }
}