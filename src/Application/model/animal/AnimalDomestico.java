package model.animal;

import enums.Porte;
import enums.Sexo;
import enums.Temperamento;
import enums.FaseDaVida;
import model.Pessoas.Dono;

import java.time.LocalDate;
import java.util.ArrayList;

public class AnimalDomestico extends Animal {
    private Dono dono;
    private boolean castrado;
    private ArrayList<Vacina> vacinas;
    private Temperamento temperamento;

    public AnimalDomestico(String nome, String especie, String raca, LocalDate dataNascimento, FaseDaVida faseDaVida, Double peso, Porte porte, Sexo sexo, Dono dono, ArrayList<Vacina> vacinas, Temperamento temperamento, boolean castrado) {
        super(nome,  especie,  raca,  dataNascimento, faseDaVida,  peso,  porte,  sexo);
        setDono(dono);
        setVacinas(vacinas);
        setTemperamento(temperamento);
        setCastrado(castrado);
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

    public boolean isCastrado() {
        return castrado;
    }

    public void setCastrado(boolean castrado) {
        if(temperamento == null ){
            throw new IllegalArgumentException("Insira uma expressão válida, para animal doéstico castrado!");
        }
        this.castrado = castrado;
    }


    public ArrayList<Vacina> getVacinas() {
        return vacinas;
    }

    public void setVacinas(ArrayList<Vacina> vacinas) {
        this.vacinas = vacinas;
    }
}
