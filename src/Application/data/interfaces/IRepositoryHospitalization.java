package data.interfaces;

import business.model.invoice.Hospitalization;

import java.util.ArrayList;

public interface IRepositoryHospitalization {

    Hospitalization findById(int id);
    ArrayList<Hospitalization> findAll();
    void update(int index, Hospitalization hp);
    void create(Hospitalization hp);
    void remove(Hospitalization hp);
}
