package data.interfaces;

import business.model.notaFiscal.NotaFiscal;

import java.util.ArrayList;

public interface IRepositoryInvoice {

     NotaFiscal findById(int id);
     ArrayList<NotaFiscal> findAll();
     void update(int index, NotaFiscal nf);
     void create(NotaFiscal nf);
     void remove(NotaFiscal nf);
}
