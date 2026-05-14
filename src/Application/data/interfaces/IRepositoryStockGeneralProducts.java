package data.interfaces;

import business.model.notaFiscal.Produto;

import java.util.ArrayList;

public interface IRepositoryStockGeneralProducts{

    Produto findById(int id);
    ArrayList<Produto> findAll();
    void update(int index, Produto p);
    void create(Produto p);
    void remove(Produto p);
}
