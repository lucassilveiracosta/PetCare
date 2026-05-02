package repository.Interface;

import model.notaFiscal.NotaFiscal;

import java.util.ArrayList;

public interface IRepositoryInvoice {

    public NotaFiscal findById(int id);
    public ArrayList<NotaFiscal> findAll();
    public void update(int id);
    public void create(NotaFiscal nf);
    public void delete(int id);
}
