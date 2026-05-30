package data.repository;

import data.interfaces.IRepositoryStockMedicine;
import main.java.business.model.medicine.Medicine;
import java.util.ArrayList;
import java.util.List;

public class RepositoryStockMedicine implements IRepositoryStockMedicine {

    private final List<Medicine> medicineList = new ArrayList<>();

    @Override
    public void addMedicine(Medicine medicine) {
        this.medicineList.add(medicine);
    }

    @Override
    public void removeMedicine(Medicine medicine) {
        this.medicineList.remove(medicine);
    }

    @Override
    public List<Medicine> getAllMedicines() {
        return new ArrayList<>(this.medicineList);
    }

    @Override
    public Medicine findById(int id) {
        for (Medicine med : medicineList) {
            if (med.getId() == id) {
                return med;
            }
        }
        return null;
    }

    @Override
    public void updateMedicine(Medicine updateMedicine) {
        if (updateMedicine == null) {
            return;
        }
        for (int i = 0; i < medicineList.size(); i++) {
            if (medicineList.get(i).getId() == updateMedicine.getId()) {
                medicineList.set(i, updateMedicine);
                return;
            }
        }
    }
}