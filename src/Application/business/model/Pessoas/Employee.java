package business.model.Pessoas;

import java.time.LocalDate;

public class Employee extends Person {
    private String position;
    private String workShift;

    public Employee(String name, String email, String password, LocalDate birthDate, String cpf, String telephone, String position, String workShift) {
        super(name, email, password, birthDate, cpf, telephone);
        setPosition(position);
        setWorkShift(workShift);
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position)
    {
        if(position == null || position.isBlank()){
            throw new IllegalArgumentException("400 - Invalid position");
        }
        this.position = position;
    }

    public String getWorkShift() {
        return workShift;
    }

    public void setWorkShift(String workShift) {
        if(workShift == null || workShift.isBlank()){
            throw new IllegalArgumentException("400 - Invalid work shift");
        }
        this.workShift = workShift;
    }
}
