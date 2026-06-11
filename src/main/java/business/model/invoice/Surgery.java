package business.model.invoice;
import business.model.person.Specialty;
import business.model.person.Veterinarian;
import business.model.animal.Animal;
import enums.SurgeryRisk;

import java.time.LocalDateTime;

public class Surgery extends Procedure {

    private Veterinarian responsibleVeterinarian; 
    private String anesthesiaType;
    private SurgeryRisk surgeryRisk;
    private String supplies = "-"; // supplies

    public Surgery(Double price, Animal patient, LocalDateTime dateHour, String description,  SurgeryRisk surgeryRisk, Veterinarian responsibleVeterinarian, String anesthesiaType) {
        super(price, patient, dateHour, description);
        setSurgeryRisk(surgeryRisk);
        setAnesthesiaType(anesthesiaType);
        setResponsibleVeterinarian(responsibleVeterinarian);

    }

    public Surgery(Boolean bool, Double price, Animal patient, LocalDateTime dateHour, String description, SurgeryRisk surgeryRisk, Veterinarian responsibleVeterinarian, String anesthesiaType) {
        super(bool, price, patient, dateHour, description);
        setSurgeryRisk(surgeryRisk);
        setAnesthesiaType(anesthesiaType);
        setResponsibleVeterinarian(responsibleVeterinarian);
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

    public Veterinarian getResponsibleVeterinarian() {
        return responsibleVeterinarian;
    }

    public void setResponsibleVeterinarian(Veterinarian responsibleVeterinarian) {
        for (Specialty s: responsibleVeterinarian.getSpecialties()) {
            if (s.getName().isBlank()) throw new IllegalArgumentException("400 - dont have active specialty");
        }
        if (responsibleVeterinarian == null){
            throw new IllegalArgumentException("400 - Invalid responsible veterinarian");
        }
        this.responsibleVeterinarian = responsibleVeterinarian;
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


