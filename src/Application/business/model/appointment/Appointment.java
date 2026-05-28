package business.model.appointment;



import business.model.person.Veterinarian;
import business.model.animal.Animal;
import business.model.invoice.Procedure;
import enums.AppointmentStatus;

import java.time.LocalDateTime;

public class Appointment extends Procedure {
    private Veterinarian responsableVeterinarian;
    private String diagnosis;
    private Anamnesis anamnesis;
    private PhysicalExamination phisicalExam;
    private String medicalPrescription;
    private AppointmentStatus status;

    public Appointment(Double price, Animal patient, LocalDateTime dateHour, String description, Veterinarian responsableVeterinarian, String diagnosis, String medicalPrescription, Anamnesis anamnesis, PhysicalExamination phisicalExam, AppointmentStatus status) {
        super(price, patient, dateHour, description);
        setDiagnosis(diagnosis);
        setResponsableVeterinarian(responsableVeterinarian);
        setMedicalPrescription(medicalPrescription);
        setAnamnesis(anamnesis);
        setPhisicalExam(phisicalExam);
        setStatus(status);

    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        if (status == null) throw new IllegalArgumentException("400 - Invalid status");
        this.status = status;
    }

    public Anamnesis getAnamnesis() {
        return anamnesis;
    }

    public void setAnamnesis(Anamnesis anamnesis) {
        if (anamnesis == null) {
            throw new IllegalArgumentException("400 - Invalid anamnesis");
        }
        this.anamnesis = anamnesis;
    }

    public PhysicalExamination getPhisicalExam() {
        return phisicalExam;
    }

    public void setPhisicalExam(PhysicalExamination phisicalExam) {
        if (phisicalExam == null) {
            throw new IllegalArgumentException("400 - Invalid physical exam");
        }
        this.phisicalExam = phisicalExam;
    }

    public Veterinarian getResponsableVeterinarian() {
        return responsableVeterinarian;
    }

    public void setResponsableVeterinarian(Veterinarian responsableVeterinarian) {
        if(responsableVeterinarian == null){
            throw new IllegalArgumentException("400 - Invalid responsable veterinarian");
        }
        this.responsableVeterinarian = responsableVeterinarian;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        if(diagnosis == null || diagnosis.isBlank()){
            throw new IllegalArgumentException("400 - Invalid diagnosis");
        }
        this.diagnosis = diagnosis;
    }

    public String getMedicalPrescription() {
        return medicalPrescription;
    }

    public void setMedicalPrescription(String medicalPrescription) {

        this.medicalPrescription = medicalPrescription;
    }
}
