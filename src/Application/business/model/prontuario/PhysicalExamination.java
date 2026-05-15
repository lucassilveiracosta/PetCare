package business.model.prontuario;

import enums.Consciencia;

public class PhysicalExamination {

    private Consciencia levelOfConsciousness;
    private ParametrosVitais vitalParameters;
    private String description;

    public PhysicalExamination(Consciencia levelOfConsciousness, ParametrosVitais vitalParameters, String description) {
        setLevelOfConsciousness(levelOfConsciousness);
        setVitalParameters(vitalParameters);
        setDescription(description);
    }

    public Consciencia getLevelOfConsciousness() {
        return levelOfConsciousness;
    }

    public void setLevelOfConsciousness(Consciencia levelOfConsciousness) {
        if (levelOfConsciousness == null) {
            throw new IllegalArgumentException("Deve ser preenchido um nivel de consciencia!");
        }
        this.levelOfConsciousness = levelOfConsciousness;
    }

    public ParametrosVitais getVitalParameters() {
        return vitalParameters;
    }

    public void setVitalParameters(ParametrosVitais vitalParameters) {
        if (vitalParameters == null) {
            throw new IllegalArgumentException("Deve ser preenchido os parametros vitais!");
        }
        this.vitalParameters = vitalParameters;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("A descrição não pode ser nula ou ficar em branco!");
        }
        this.description = description;
    }
}
