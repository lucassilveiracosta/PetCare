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
        if(dono == null ){
            throw new IllegalArgumentException("Nome não pode ser nulo!");
        }
        this.dono = dono;
    }

    public Temperamento getTemperamento() {
        return temperamento;
    }

    public void setTemperamento(Temperamento temperamento) {
        if(temperamento == null ){
            throw new IllegalArgumentException("Insira um temperamento válido!");
        }
        this.temperamento = temperamento;
    }

    public vacinasTomadas getCarteiraDeVacina() {
        return CarteiraDeVacina;
    }

    public void setCarteiraDeVacina(vacinasTomadas carteiraDeVacina) {
        if(carteiraDeVacina == null ){
            throw new IllegalArgumentException("Carteira de Vacina inválida!");
        }
        CarteiraDeVacina = carteiraDeVacina;
    }

    public boolean isCadastro() {
        return cadastro;
    }

    public void setCadastro(boolean cadastro) {
        this.cadastro = cadastro;
    }


}
