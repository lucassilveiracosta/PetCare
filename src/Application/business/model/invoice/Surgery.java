package business.model.invoice;
import business.model.person.Veterinarian;
import business.model.animal.Animal;
import enums.SurgeryRisk;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Surgery extends Procedure {

    private String anesthesiaType; //sugestão: transformar em enum
    private SurgeryRisk surgeryRisk;
    private ArrayList<Veterinarian> responsibleVeterinarians;
    private ArrayList<Product> materials;

    public Surgery(Double price, Animal patient, LocalDateTime dateHour, String description, ArrayList<Veterinarian> responsibleVeterinarians, ArrayList<Product> materials,String anesthesiaType, SurgeryRisk surgeryRisk) {
        super(price, patient, dateHour, description);
        setSurgeryRisk(surgeryRisk);
        setAnesthesiaType(anesthesiaType);
        this.responsibleVeterinarians = responsibleVeterinarians;
        this.materials = materials;
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

    public String getAnesthesiaType() {
        return anesthesiaType;
    }

    public void setAnesthesiaType(String anesthesiaType) {
        if(anesthesiaType == null || anesthesiaType.isBlank()){
            throw new IllegalArgumentException("400 - Invalid anesthesia type");
        }
        this.anesthesiaType = anesthesiaType;
    }

    public ArrayList<Veterinarian> getResponsibleVeterinarians() {
        return responsibleVeterinarians;
    }

    public void setResponsibleVeterinarians(ArrayList<Veterinarian> responsibleVeterinarians) {
        if(responsibleVeterinarians == null){
            throw new IllegalArgumentException("400 - Invalid Veterinarians");
        }
        this.responsibleVeterinarians = responsibleVeterinarians;
    }

    public ArrayList<Product> getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList<Product> materials) {
        if(materials == null){
            throw new IllegalArgumentException("400 - Invalid materials");
        }
        this.materials = materials;
    }
}


