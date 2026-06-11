package business.model.appointment;

import business.model.animal.Animal;
import business.model.invoice.Surgery;

import java.util.ArrayList;

public class MedicalRecord { //prontuario

    protected static int countId = 1;
    protected int id = countId++;
    private final Animal patient;
    private Anamnesis patientAnamnesis;
    private PhysicalExamination physicalExamination;
    private VitalParameters vitalParameters;
    private ArrayList<String> exams;
    private ArrayList<Surgery> surgeries;

    public MedicalRecord(Animal patient, Anamnesis patientAnamnesis, PhysicalExamination physicalExamination, VitalParameters vitalParameters, ArrayList<String> exams, ArrayList<Surgery> surgeries) {
        this.patient = patient;
        setPatientAnamnesis(patientAnamnesis);
        setPhysicalExamination(physicalExamination);
        setVitalParameters(vitalParameters);
        this.exams = exams;
        this.surgeries = surgeries;
    }

    public Animal getPatient() {
        return patient;
    }

    public Anamnesis getPatientAnamnesis() {
        return patientAnamnesis;
    }

    public void setPatientAnamnesis(Anamnesis patientAnamnesis) {
        if (patientAnamnesis == null) {
            throw new IllegalArgumentException("Anamnesis can't be null");
        }
        this.patientAnamnesis = patientAnamnesis;
    }

    public PhysicalExamination getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(PhysicalExamination physicalExamination) {
        if (physicalExamination == null) {
            throw new IllegalArgumentException("Physical examination can't be null");
        }
        this.physicalExamination = physicalExamination;
    }

    public VitalParameters getVitalParameters() {
        return vitalParameters;
    }

    public void setVitalParameters(VitalParameters vitalParameters) {
        if (vitalParameters == null) {
            throw new IllegalArgumentException("Vital parameters can't be null");
        }
        this.vitalParameters = vitalParameters;
    }

    public ArrayList<String> getExams() {
        return exams;
    }

    public void setExams(ArrayList<String> exams) {
        this.exams = exams;
    }

    public ArrayList<Surgery> getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(ArrayList<Surgery> surgeries) {
        this.surgeries = surgeries;
    }

    public int getId() {
        return id;
    }
}
