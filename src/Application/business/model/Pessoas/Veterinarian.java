package business.model.Pessoas;

import java.time.LocalDate;
import java.util.ArrayList;

public class Veterinarian extends Person {
    private String crmv;
    private ArrayList<Specialty> specialties;


    public Veterinarian(String name, String email, String password, LocalDate birthDate, String cpf, String telephone, String crmv, ArrayList<Specialty> specialties){
        super(name, email, password, birthDate, cpf, telephone);
        setCrmv(crmv);
        setSpecialties(specialties);
    }

    public String getCrmv(){
        return crmv;
    }

    private void setCrmv(String crmv){
        if(crmv == null || crmv.isBlank()){
            throw new IllegalArgumentException("400 - Invalid CRMV");
        }
        this.crmv = crmv;
    }

    public ArrayList<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(ArrayList<Specialty> specialties) {
        this.specialties = specialties;

    }
}
