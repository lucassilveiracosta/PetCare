package main.java.business.model.invoice;



import main.java.business.model.animal.Animal;
import main.java.business.model.person.Owner;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Invoice {
    protected static int countId = 1;

    protected int id = countId++;
    private Owner owner;
    private Animal patient;
    private final LocalDateTime dateHour = LocalDateTime.now();
    private ArrayList<Procedure> procedures;
    private ArrayList<Product> products;

    public Invoice(Owner owner, Animal patient, ArrayList<Procedure> procedures, ArrayList<Product> products) {
        setDono(owner);
        setPaciente(patient);
        this.procedures = procedures;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setDono(Owner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("400 - Invalid owner");
        }
        this.owner = owner;
    }

    public Animal getPatient() {
        return patient;
    }

    public void setPaciente(Animal patient) {
        if (patient == null) {
            throw new IllegalArgumentException("400 - Invalid patient");
        }
        this.patient = patient;
    }

    public LocalDateTime getDateHour() {
        return dateHour;
    }

    public ArrayList<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(ArrayList<Procedure> procedures) {
        if (procedures == null) {
            throw new IllegalArgumentException("400 - Invalid procedures");
        }
        this.procedures = procedures;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        if (products == null) {
            throw new IllegalArgumentException("400 - Invalid products");
        }
        this.products = products;
    }
}