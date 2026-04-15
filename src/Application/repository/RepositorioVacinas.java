package repository;

import model.prontuario.Vacina;

import java.util.ArrayList;

public class RepositorioVacinas {

    private ArrayList<Vacina> vacinasRep;

    public ArrayList<Vacina> getVacinasRep() {
        return vacinasRep;
    }

    public void setVacinas(ArrayList<Vacina> vacinas) {
        this.vacinasRep = vacinas;
    }
}
