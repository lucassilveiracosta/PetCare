package repository.Interface;

import model.notaFiscal.Produto;

import java.util.ArrayList;

public interface IRepositoryStockGeneralProducts{

    public Produto findById(int id);
    public ArrayList<Produto> findAll();
    public void update (int id, Produto p);
    public void create (Produto p);
    public void  remove (Produto p);
}
