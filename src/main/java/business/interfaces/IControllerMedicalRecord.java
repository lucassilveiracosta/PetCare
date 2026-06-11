package business.interfaces;

import business.model.appointment.MedicalRecord;

import java.util.List;

public interface IControllerMedicalRecord {
    MedicalRecord getById(int id);
    List<MedicalRecord> getAll();
    void patch(int id, MedicalRecord md);
    void delete(int id);
    void post(MedicalRecord md);
}
