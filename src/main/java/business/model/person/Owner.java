package business.model.person;

import java.time.LocalDate;

public class Owner extends Person {
    private String job;
    private String description;

    public Owner(String name, String email, String password, LocalDate birthdate, String cpf, String telephone, String job, String description){
        super(name, email, password, birthdate, cpf, telephone);
        setJob(job);
        setDescription(description);
    }
    public String getJob(){
        return job;
    }
    public void setJob(String job){
        if(job == null || job.isBlank()){
            throw new IllegalArgumentException("400 - Invalid job");
        }
        this.job = job;
    }

    public String getDescription(){
        return description;
    }
    private void setDescription(String description){
        if(description == null || description.isBlank()){
            throw new IllegalArgumentException("400 - Invalid description");
        }
        this.description = description;
    }

}
