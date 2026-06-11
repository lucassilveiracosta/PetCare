package data.repository;

import business.model.invoice.Surgery;
import data.interfaces.IRepositorySurgery;

import java.util.ArrayList;

public class RepositorySurgery implements IRepositorySurgery {

    private final ArrayList<Surgery> surgeries;

    public RepositorySurgery(ArrayList<Surgery> surgeries) {
        this.surgeries = surgeries;
    }

    @Override
    public Surgery findById(int id) {
        Surgery found = null;
        for (Surgery s : surgeries) {
            if (s.getId() == id) {
                found = s;
            }
        }
        return found;
    }

    @Override
    public ArrayList<Surgery> findAll() {
        return surgeries;
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
    public void create(Surgery s) {
        surgeries.add(s);
    }

    @Override
    public void remove(Surgery s) {
        if (s != null) {
            surgeries.remove(s);
        }
    }
}
