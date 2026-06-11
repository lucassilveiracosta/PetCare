package data.interfaces;
import business.model.appointment.MedicalRecord;
import java.util.ArrayList;

public interface IRepositoryMedicalRecord {
    void create(MedicalRecord medicalRecord);
    MedicalRecord findById(int id);
    ArrayList<MedicalRecord> findAll();
    void  update(int index, MedicalRecord medicalRecord);
    void remove(MedicalRecord md);
}
