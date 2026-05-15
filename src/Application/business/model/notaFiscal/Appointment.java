package business.model.notaFiscal;



import business.model.Pessoas.Veterinarian;
import business.model.animal.Animal;

import java.time.LocalDateTime;

public class Appointment extends Procedure {
    private Veterinarian responsableVeterinarian;
    private String diagnosis;
    private String medicalPrescription;

    public Appointment(Double price, Animal patient, LocalDateTime dateHour, String description, Veterinarian responsableVeterinarian, String diagnosis, String medicalPrescription) {
        super(price, patient, dateHour, description);
        setDiagnosis(diagnosis);
        setResponsableVeterinarian(responsableVeterinarian);
        setMedicalPrescription(medicalPrescription);

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
