package interfaces;

import model.notaFiscal.Produto;

import java.util.ArrayList;

public interface IRepositoryStockVetProducts {

    Produto findById(int id);
    ArrayList<Produto> findAll();
    void update(int index, Produto p);
    void create(Produto p);
    void  remove(Produto p);
}
