package business.model.notaFiscal;

import business.model.animal.Animal;

import java.time.LocalDateTime;

public abstract class Procedure {
    protected static int countId = 1;

    protected int id = countId++;
    private Animal patient;
    private LocalDateTime dateHour;
    private String description;
    private Double price;

    public Procedure(Double price, Animal patient, LocalDateTime dateHour, String description) {
        this.price = price;
        setPatient(patient);
        this.dateHour = dateHour;
        setDescription(description);
    }

    public Animal getPatient() {
        return patient;
    }

    public void setPatient(Animal patient) {
        if(patient == null){
            throw new IllegalArgumentException("400 - Invalid patient");
        }
        this.patient = patient;
    }

    public LocalDateTime getDateHour() {
        return dateHour;
    }

    public void setDateHour(LocalDateTime dateHour) {
        if (dateHour == null) {
            throw new IllegalArgumentException("400 - Invalid dateHour");
        }
        this.dateHour = dateHour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("400 - Invalid description");
        }
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }
}
