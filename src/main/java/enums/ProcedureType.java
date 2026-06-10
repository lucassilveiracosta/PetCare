package enums;

public enum ProcedureType {
    GENERAL_CONSULTATION("General Consultation"),
    VACCINATION("Vaccination"),
    SURGERY("Surgery"),
    EMERGENCY("Emergency"),
    FOLLOW_UP("Follow-up"),
    CHECKUP("Checkup"),
    OTHER("Other");

    private final String label;

    ProcedureType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
