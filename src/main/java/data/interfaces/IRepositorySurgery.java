package data.interfaces;

import business.model.invoice.Surgery;

import java.util.ArrayList;

public interface IRepositorySurgery {

    Surgery findById(int id);
    ArrayList<Surgery> findAll();
    void update(int index, Surgery s);
    void create(Surgery s);
    void remove(Surgery s);
}
