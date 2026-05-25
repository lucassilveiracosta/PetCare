package business.interfaces;

import business.model.medicine.Medicine;
import java.util.List;

public interface IControllerStockMedicine {

    void registerMedicine(Medicine medicine);

    void writeOffStock(Medicine medicine, int quantity);

    List<Medicine> listAll();

    List<Medicine> listControlled();
}