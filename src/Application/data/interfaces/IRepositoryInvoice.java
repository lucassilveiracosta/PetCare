package data.interfaces;

import business.model.notaFiscal.Invoice;

import java.util.ArrayList;

public interface IRepositoryInvoice {

     Invoice findById(int id);
     ArrayList<Invoice> findAll();
     void update(int index, Invoice nf);
     void create(Invoice nf);
     void remove(Invoice nf);
}
