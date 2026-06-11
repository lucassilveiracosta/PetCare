package business.model.invoice;

import business.model.animal.Animal;

import java.time.LocalDateTime;

public abstract class Procedure {
    protected static int countId = 1;

    protected int id = countId++;
    private Animal patient;
    private LocalDateTime dateHourScheduled;
    private String description;
    private Double price;

    public Procedure(Double price, Animal patient, LocalDateTime dateHourScheduled, String description) {
        this.price = price;
        setPatient(patient);
        setDateHourScheduled(dateHourScheduled);
        setDescription(description);
    }

    //Allows past appointments
    public Procedure(Boolean bool, Double price, Animal patient, LocalDateTime dateHourScheduled, String description) {
        this.price = price;
        setPatient(patient);
        this.dateHourScheduled = dateHourScheduled;
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

    public LocalDateTime getDateHourScheduled() {
        return dateHourScheduled;
    }

    public void setDateHourScheduled(LocalDateTime dateHourScheduled) {
        if (dateHourScheduled == null) {
            throw new IllegalArgumentException("400 - Invalid dateHourScheduled");
        }

        if (dateHourScheduled.isBefore(LocalDateTime.now())) throw new IllegalArgumentException("400 - You cant create a procedure before today");
        this.dateHourScheduled = dateHourScheduled;
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

    public void setId(int id) {
        this.id = id;
        if (id >= countId) {
            countId = id + 1;
        }
    }
}
