package model;

public class Hidratacao {

    private boolean euvolemico;
    private Double desitratacao;

    public Hidratacao(boolean euvolemico, Double desitratacao) {
        this.euvolemico = euvolemico;
        if(euvolemico) {
            this.desitratacao = null;
        }
        else {
            this.desitratacao = desitratacao;
        }

    }
}
