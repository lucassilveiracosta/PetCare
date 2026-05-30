package data.interfaces;

import main.java.business.model.medicine.Medicine;
import java.util.List;

public interface IRepositoryStockMedicine {
    void addMedicine(Medicine medicine);

    void removeMedicine(Medicine medicine);

    List<Medicine> getAllMedicines();

    Medicine findById(int id);

    void updateMedicine(Medicine medicine);

}