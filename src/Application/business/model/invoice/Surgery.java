package business.model.invoice;
import business.model.person.Veterinarian;
import business.model.animal.Animal;

import java.time.LocalDateTime;

public class Surgery extends Procedure {

    private Veterinarian responsableVeterinarian;
    private String anesthesiaType;
    private String surgeryRisk;

    public Surgery(Double price, Animal patient, LocalDateTime dateHour, String description, Veterinarian responsebleVeterinarian, String anesthesiaType, String surgeryRisk) {
        super(price, patient, dateHour, description);
        setSurgeryRisk(surgeryRisk);
        setAnesthesiaType(anesthesiaType);
        setResponsableVeterinarian(responsebleVeterinarian);

    }

    public String getSurgeryRisk() {
        return surgeryRisk;
    }

    public void setSurgeryRisk(String surgeryRisk) {
        if(surgeryRisk == null || surgeryRisk.isBlank()){
            throw new IllegalArgumentException("400 - Invalid surgery risk");
        }
        this.surgeryRisk = surgeryRisk;
    }

    public Veterinarian getResponsebleVeterinarian() {
        return responsableVeterinarian;
    }

    public void setResponsableVeterinarian(Veterinarian responsebleVeterinarian) {
        if(responsebleVeterinarian == null){
            throw new IllegalArgumentException("400 - Invalid responsable vatarinarian");
        }
        this.responsableVeterinarian = responsebleVeterinarian;
    }

    public String getAnesthesiaType() {
        return anesthesiaType;
    }

    public void setAnesthesiaType(String anesthesiaType) {
        if(anesthesiaType == null || anesthesiaType.isBlank()){
            throw new IllegalArgumentException("400 - Invalid anesthesia type");
        }
        this.anesthesiaType = anesthesiaType;
    }
}


