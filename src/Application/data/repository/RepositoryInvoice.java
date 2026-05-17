package data.repository;

import business.model.invoice.Invoice;
import data.interfaces.IRepositoryInvoice;

import java.util.ArrayList;

public class RepositoryInvoice implements IRepositoryInvoice {

    private final ArrayList<Invoice> notasFiscais;

    public RepositoryInvoice(ArrayList<Invoice> notasFiscais) {
        this.notasFiscais = notasFiscais;
    }

    @Override
    public Invoice findById(int id) {
        Invoice notaFiscal = null;
        for (Invoice nf: notasFiscais) {
            if (nf.getId() == id) {
                notaFiscal = nf;
            }
        }
        return notaFiscal;
    }

    @Override
    public ArrayList<Invoice> findAll() {
        return notasFiscais;
    }

    @Override
    public void update(int id, Invoice nf) {
        for (int i = 0; i < notasFiscais.size(); i++) {
            if (notasFiscais.get(i).getId() == id) {
                notasFiscais.set(i, nf);
                return;
            }
        }
    }


    @Override
    public void create(Invoice nf) {
        notasFiscais.add(nf);
    }

    @Override
    public void remove(Invoice nf) {
        if (nf != null) notasFiscais.remove(nf);
    }
}
