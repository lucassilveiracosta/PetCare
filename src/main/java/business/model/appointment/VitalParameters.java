package business.model.appointment;

import enums.Mucosa;

public class VitalParameters {

    private Integer heartRate;
    private Integer respiratoryRate;
    private Double celciusTemperature;
    private Integer coagulation;
    private Mucosa mucosa;
    private Hydration hydration;
    private String description;

    public VitalParameters(Integer heartRate, Integer respiratoryRate, Double celciusTemperature, Mucosa mucosa, Integer coagulation, Hydration hydration, String description) {
        this.heartRate = heartRate;
        this.respiratoryRate = respiratoryRate;
        this.celciusTemperature = celciusTemperature;
        this.mucosa = mucosa;
        this.coagulation = coagulation;
        this.hydration = hydration;
        setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("400 - invalid description");
        }
        this.description = description;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Integer getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(Integer respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public Double getCelciusTemperature() {
        return celciusTemperature;
    }

    public void setCelciusTemperature(Double celciusTemperature) {
        this.celciusTemperature = celciusTemperature;
    }

    public Integer getCoagulation() {
        return coagulation;
    }

    public void setCoagulation(Integer coagulation) {
        this.coagulation = coagulation;
    }

    public Mucosa getMucosa() {
        return mucosa;
    }

    public void setMucosa(Mucosa mucosa) {
        this.mucosa = mucosa;
    }

    public Hydration getHydration() {
        return hydration;
    }

    public void setHydration(Hydration hydration) {
        this.hydration = hydration;
    }
}
