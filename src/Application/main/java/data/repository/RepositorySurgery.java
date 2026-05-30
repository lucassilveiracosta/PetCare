package data.repository;

import business.model.invoice.Surgery;
import data.interfaces.IRepositorySurgery;

import java.util.ArrayList;
import java.util.List;

public class RepositorySurgery implements IRepositorySurgery {

    private final ArrayList<Surgery> surgeries;

    public RepositorySurgery(ArrayList<Surgery> surgeries) {
        this.surgeries = surgeries;
    }

    @Override
    public void create(Surgery s) {
        surgeries.add(s);
    }

    @Override
    public ArrayList<Surgery> findAll() {
        return surgeries;
    }

    @Override
    public Surgery findById(int id) {
        Surgery surgery = null;
        for (Surgery s: surgeries) {
            if (s.getId() == id) {
                surgery = s;
            }
        }
        return surgery;
    }

    @Override
    public void update(int id, Surgery s) {
        for (int i = 0; i < surgeries.size(); i++) {
            if (surgeries.get(i).getId() == id) {
                surgeries.set(i, s);
                return;
            }
        }
    }

    @Override
    public void remove(Surgery s) {
        if (s != null) surgeries.remove(s);
    }
}
