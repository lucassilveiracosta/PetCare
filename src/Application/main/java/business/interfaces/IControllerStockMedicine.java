package main.java.business.interfaces;

import main.java.business.model.medicine.Medicine;
import java.util.List;

public interface IControllerStockMedicine {

    void registerMedicine(Medicine medicine);

    void writeOffStock(Medicine medicine, int quantity);

    List<Medicine> listAll();

    List<Medicine> listControlled();

    Medicine findById(int id);
    void updateMedicine(Medicine medicine);
    void removeMedicine(Medicine medicine);
}