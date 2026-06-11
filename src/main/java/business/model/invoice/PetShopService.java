package business.model.invoice;

import business.model.person.Employee;
import business.model.animal.Animal;
import enums.PetShopServices;

import java.time.LocalDateTime;

public class PetShopService extends Procedure {
    private PetShopServices serviceType;
    private Employee responsibleEmployee;

    public PetShopService(Double price, Animal patient, LocalDateTime dateHour, String description, PetShopServices serviceType, Employee responsibleEmployee){
        super(price, patient, dateHour, description);
        setServiceType(serviceType);
        setResponsibleEmployee(responsibleEmployee);
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

    public Employee getResponsibleEmployee() {
        return responsibleEmployee;
    }

    public void setResponsibleEmployee(Employee responsibleEmployee) {
        if(responsibleEmployee == null){
            throw new IllegalArgumentException("400 - Invalid responsible employee");
        }
        this.responsibleEmployee = responsibleEmployee;
    }
}
