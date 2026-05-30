package main.java.business.model.invoice;


import enums.PetShopServices;
import main.java.business.model.animal.Animal;
import main.java.business.model.person.Employee;

import java.time.LocalDateTime;

public class ServicoPetShop extends Procedure {
    private PetShopServices serviceType;
    private Employee responsableEmployee;

    public ServicoPetShop(Double price, Animal patient, LocalDateTime dateHour, String description, PetShopServices serviceType, Employee responsableEmployee){
        super(price, patient, dateHour, description);
        setServiceType(serviceType);
        setResponsableEmployee(responsableEmployee);
    }

    public PetShopServices getServiceType() {
        return serviceType;
    }

    public void setServiceType(PetShopServices serviceType) {
        if(serviceType == null){
            throw new IllegalArgumentException("400 - Invalid service type");
        }
        this.serviceType = serviceType;
    }

    public Employee getResponsableEmployee() {
        return responsableEmployee;
    }

    public void setResponsableEmployee(Employee responsableEmployee) {
        if(responsableEmployee == null){
            throw new IllegalArgumentException("400 - Invalid responsable employee");
        }
        this.responsableEmployee = responsableEmployee;
    }
}
