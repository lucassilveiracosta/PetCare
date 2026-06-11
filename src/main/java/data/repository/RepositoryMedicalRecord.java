package data.repository;
import business.model.appointment.MedicalRecord;
import data.interfaces.IRepositoryMedicalRecord;
import java.util.ArrayList;

public class RepositoryMedicalRecord implements IRepositoryMedicalRecord {

    private final ArrayList<MedicalRecord> medicalRecords;

    public RepositoryMedicalRecord(ArrayList<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    @Override
    public MedicalRecord findById(int id) {
        MedicalRecord medicalrecord = null;
        for (MedicalRecord md: medicalRecords) {
            if (md.getId() == id) {
                medicalrecord = md;
            }
        }
        return medicalrecord;
    }

    @Override
    public ArrayList<MedicalRecord> findAll() {
        return medicalRecords;
    }

    @Override
    public void update(int id, MedicalRecord md) {
        for (int i = 0; i < medicalRecords.size(); i++) {
            if (medicalRecords.get(i).getId() == id) {
                medicalRecords.set(i, md);
                return;
            }
        }
    }

    @Override
    public void create(MedicalRecord md) {
        medicalRecords.add(md);
    }

    @Override
    public void remove(MedicalRecord md) {
        if (md != null) medicalRecords.remove(md);
    }
}
