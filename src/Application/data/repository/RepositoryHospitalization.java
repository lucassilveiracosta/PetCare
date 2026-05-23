package data.repository;


import business.model.invoice.Hospitalization;

import data.interfaces.IRepositoryHospitalization;

import java.util.ArrayList;

public class RepositoryHospitalization implements IRepositoryHospitalization {
    private final ArrayList<Hospitalization> hospitalizations;
    public RepositoryHospitalization(ArrayList<Hospitalization> hospitalizations) {
        this.hospitalizations = hospitalizations;
    }


    @Override
    public Hospitalization findById(int id) {
        Hospitalization consulta = null;
        for (Hospitalization c : hospitalizations) {
            if (c.getId() == id){
                consulta = c;
            }
        }
        return consulta;
    }

    @Override
    public ArrayList<Hospitalization> findAll() {
        return hospitalizations;
    }

    @Override
    public void update(int id, Hospitalization c) {
        for (int i = 0; i < hospitalizations.size(); i++) {
            if (hospitalizations.get(i).getId() == id) {
                hospitalizations.set(i, c);
                return;
            }
        }
    }

    @Override
    public void create(Hospitalization c) {
        hospitalizations.add(c);
    }

    @Override
    public void remove(Hospitalization c) {
        if (c != null) {
            hospitalizations.remove(c);
        }
    }
}
