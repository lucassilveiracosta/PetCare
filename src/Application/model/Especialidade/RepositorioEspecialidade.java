package model.Especialidade;

import java.util.ArrayList;

public class RepositorioEspecialidade {
    private ArrayList<Especialidade> especialidadeRep;

    public ArrayList<Especialidade> getEspecialidades() {
        return especialidadeRep;
    }

    public void setEspecialidades(ArrayList<Especialidade> especialidades){
        especialidadeRep = especialidades;
    }

}
