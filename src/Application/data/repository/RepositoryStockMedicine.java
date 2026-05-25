package data.repository;

import data.interfaces.IRepositoryStockMedicine;
import business.model.medicine.Medicine;
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
}