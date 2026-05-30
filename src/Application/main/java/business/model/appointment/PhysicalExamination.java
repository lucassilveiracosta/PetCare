package main.java.business.model.appointment;

import enums.Conscience;

public class PhysicalExamination {

    private Conscience levelOfConsciousness;
    private VitalParameters vitalParameters;
    private String description;

    public PhysicalExamination(Conscience levelOfConsciousness, VitalParameters vitalParameters, String description) {
        setLevelOfConsciousness(levelOfConsciousness);
        setVitalParameters(vitalParameters);
        setDescription(description);
    }

    public Conscience getLevelOfConsciousness() {
        return levelOfConsciousness;
    }

    public void setLevelOfConsciousness(Conscience levelOfConsciousness) {
        if (levelOfConsciousness == null) {
            throw new IllegalArgumentException("400 - Invalid level of consciousness");
        }
        this.levelOfConsciousness = levelOfConsciousness;
    }

    public VitalParameters getVitalParameters() {
        return vitalParameters;
    }

    public void setVitalParameters(VitalParameters vitalParameters) {
        if (vitalParameters == null) {
            throw new IllegalArgumentException("400 - Invalid vital parameters");
        }
        this.vitalParameters = vitalParameters;
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
}
