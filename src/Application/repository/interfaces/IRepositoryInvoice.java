package repository.interfaces;

import model.notaFiscal.NotaFiscal;

import java.util.ArrayList;

public interface IRepositoryInvoice {

    public NotaFiscal findById(int id);
    public ArrayList<NotaFiscal> findAll();
    public void update(int index, NotaFiscal nf);
    public void create(NotaFiscal nf);
    public void remove(NotaFiscal nf);
}
