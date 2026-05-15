package business.model.prontuario;

public class Anamnesis {

    private String mainComplaint;
    private String dietaryRestriction;
    private String description;

    public Anamnesis(String mainComplaint, String dietaryRestriction, String description) {
        setMainComplaint(mainComplaint);
        setDietaryRestriction(dietaryRestriction);
        setDescription(description);
    }

    public String getMainComplaint() {
        return mainComplaint;
    }

    public void setMainComplaint(String mainComplaint) {
        if (mainComplaint == null || mainComplaint.isBlank()){
            throw new IllegalArgumentException("400 - Invalid main complaint");
        }
        this.mainComplaint = mainComplaint;
    }

    public String getDietaryRestriction() {
        return dietaryRestriction;
    }

    public void setDietaryRestriction(String dietaryRestriction) {
        if (dietaryRestriction == null || dietaryRestriction.isBlank()){
            throw new IllegalArgumentException("400 - Invalid dietary restriction");
        }
        this.dietaryRestriction = dietaryRestriction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
