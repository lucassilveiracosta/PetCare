package data.interfaces;

import business.model.invoice.Procedure;

import java.util.List;

public interface IRepositoryProcedure {
    void create(Procedure p);
    Procedure findById(int id);
    List<Procedure> findAll();
    void update(int index, Procedure p);
    void remove(Procedure p);
}
