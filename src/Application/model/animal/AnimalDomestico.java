package model.animal;

import enums.Porte;
import enums.Sexo;
import enums.Temperamento;
import java.time.LocalDate;
import java.util.ArrayList;

public class AnimalDomestico extends Animal {
    private boolean castrado;
    private ArrayList<String> vacinas;
    private Temperamento temperamento;

    public AnimalDomestico(int idAnimal, String nome, String especie, String raca, LocalDate dataNascimento, double peso, Porte porte, Sexo sexo,Dono dono, ArrayList<String> vacinas, Temperamento temperamento, boolean castrado) {
        super(idAnimal,  nome,  especie,  raca,  dataNascimento,  peso,  porte,  sexo);
        this.dono = dono;
        setVacinas(vacinas);
        this.temperamento = temperamento;
        this.castrado = castrado;
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


    public ArrayList<String> getVacinas() {
        return vacinas;
    }

    public void setVacinas(ArrayList<String> vacinas) {
        this.vacinas = vacinas;
    }
}
