package business.model.prontuario;

public class Hydration {

    private boolean euvolemic;
    private Double dehydration;

    public Hydration(boolean euvolemic, Double dehydration) {
        this.euvolemic = euvolemic;
        if(euvolemic) {
            this.dehydration = null;
        }
        else {
            this.dehydration = dehydration;
        }
    }

    public boolean isEuvolemic() {
        return euvolemic;
    }

    public void setEuvolemic(boolean euvolemic) {
        this.euvolemic = euvolemic;
    }

    public Double getDehydration() {
        return dehydration;
    }

    public void setDehydration(Double dehydration) {
        this.dehydration = dehydration;
    }
}
