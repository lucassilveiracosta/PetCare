package business.model.person;

public class Specialty {
    private String name;
    private String description;

    public Specialty(String name, String description) {
        setName(name);
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("400 - Invalid name");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
