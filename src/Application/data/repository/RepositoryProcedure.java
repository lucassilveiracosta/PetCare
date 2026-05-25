package data.repository;

import business.model.invoice.Invoice;
import business.model.invoice.Procedure;
import data.interfaces.IRepositoryProcedure;

import java.util.ArrayList;
import java.util.List;

public class RepositoryProcedure implements IRepositoryProcedure {

    private final ArrayList<Procedure> procedures;

    public RepositoryProcedure(ArrayList<Procedure> procedures) {
        this.procedures = procedures;
    }


    @Override
    public void create(Procedure p) {
        procedures.add(p);
    }

    @Override
    public List<Procedure> findAll() {
        return procedures;
    }

    @Override
    public Procedure findById(int id) {
        Procedure procedure = null;
        for (Procedure p: procedures) {
            if (p.getId() == id) {
                procedure = p;
            }
        }
        return procedure;
    }

    @Override
    public void update(int id, Procedure p) {
        for (int i = 0; i < procedures.size(); i++) {
            if (procedures.get(i).getId() == id) {
                procedures.set(i, p);
                return;
            }
        }
    }

    @Override
    public void remove(Procedure p) {
        if (p != null) procedures.remove(p);
    }

}

