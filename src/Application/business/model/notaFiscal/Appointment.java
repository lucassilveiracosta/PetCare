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
        setDiagnostico(diagnosis);
        setVeterinarioResponsavel(responsableVeterinarian);
        setPrescricaoMedica(medicalPrescription);

    }

    public Veterinarian getVeterinarioResponsavel() {
        return responsableVeterinarian;
    }

    public void setVeterinarioResponsavel(Veterinarian responsableVeterinarian) {
        if(responsableVeterinarian == null){
            throw new IllegalArgumentException("Veterinario Responsável não pode ser nulo!");
        }
        this.responsableVeterinarian = responsableVeterinarian;
    }

    public String getDiagnostico() {
        return diagnosis;
    }

    public void setDiagnostico(String diagnosis) {
        if(diagnosis == null || diagnosis.isBlank()){
            throw new IllegalArgumentException("Diagnóstico não pode ser nulo!");
        }
        this.diagnosis = diagnosis;
    }

    public String getPrescricaoMedica() {
        return medicalPrescription;
    }

    public void setPrescricaoMedica(String medicalPrescription) {

        this.medicalPrescription = medicalPrescription;
    }
}
