package business.model.prontuario;

public class Hydration {

    private boolean euvolemico;
    private Double desitratacao;

    public Hydration(boolean euvolemico, Double desitratacao) {
        this.euvolemico = euvolemico;
        if(euvolemico) {
            this.desitratacao = null;
        }
        else {
            this.desitratacao = desitratacao;
        }
    }

    public boolean isEuvolemico() {
        return euvolemico;
    }

    public void setEuvolemico(boolean euvolemico) {
        this.euvolemico = euvolemico;
    }

    public Double getDesitratacao() {
        return desitratacao;
    }

    public void setDesitratacao(Double desitratacao) {
        this.desitratacao = desitratacao;
    }
}
