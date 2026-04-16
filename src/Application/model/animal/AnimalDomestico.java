package model.animal;

public class AnimalDomestico {
    private boolean cadastro;
    private vacinasTomadas CarteiraDeVacina;
    private Temperamento temperamento;

    public AnimalDomestico(Dono dono, vacinasTomadas carteiraDeVacina, Temperamento temperamento, boolean cadastro) {
        this.dono = dono;
        CarteiraDeVacina = carteiraDeVacina;
        this.temperamento = temperamento;
        this.cadastro = cadastro;
    }

    public Dono getDono() {
        return dono;
    }

    public void setDono(Dono dono) {
        this.dono = dono;
    }

    public Temperamento getTemperamento() {
        return temperamento;
    }

    public void setTemperamento(Temperamento temperamento) {
        this.temperamento = temperamento;
    }

    public vacinasTomadas getCarteiraDeVacina() {
        return CarteiraDeVacina;
    }

    public void setCarteiraDeVacina(vacinasTomadas carteiraDeVacina) {
        CarteiraDeVacina = carteiraDeVacina;
    }

    public boolean isCadastro() {
        return cadastro;
    }

    public void setCadastro(boolean cadastro) {
        this.cadastro = cadastro;
    }


}
