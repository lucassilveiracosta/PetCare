package business.model.invoice;
import business.model.person.Veterinarian;
import business.model.animal.Animal;
import enums.SurgeryRisk;

import java.time.LocalDateTime;

public class Surgery extends Procedure {

    private Veterinarian responsableVeterinarian; //corrigir gramatica
    private String anesthesiaType;
    private SurgeryRisk surgeryRisk;
    private String supplies = "-"; // insumos

    public Surgery(Double price, Animal patient, LocalDateTime dateHour, String description,  SurgeryRisk surgeryRisk, Veterinarian responsebleVeterinarian, String anesthesiaType) {
        super(price, patient, dateHour, description);
        setSurgeryRisk(surgeryRisk);
        setAnesthesiaType(anesthesiaType);
        setResponsableVeterinarian(responsebleVeterinarian);

    }

    // Allows "now"/past dates (used when registering or loading a surgery)
    public Surgery(Boolean bool, Double price, Animal patient, LocalDateTime dateHour, String description, SurgeryRisk surgeryRisk, Veterinarian responsebleVeterinarian, String anesthesiaType) {
        super(bool, price, patient, dateHour, description);
        setSurgeryRisk(surgeryRisk);
        setAnesthesiaType(anesthesiaType);
        setResponsableVeterinarian(responsebleVeterinarian);
    }

    public SurgeryRisk getSurgeryRisk() {
        return surgeryRisk;
    }

    public void setSurgeryRisk(SurgeryRisk surgeryRisk) {
        if(surgeryRisk == null){
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

    public String getSupplies() {
        return supplies;
    }

    public void setSupplies(String supplies) {
        this.supplies = (supplies != null && !supplies.isBlank()) ? supplies : "-";
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


