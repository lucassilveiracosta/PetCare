package data.repository;

import business.model.notaFiscal.NotaFiscal;
import data.interfaces.IRepositoryInvoice;

import java.util.ArrayList;

public class RepositoryInvoice implements IRepositoryInvoice {

    private final ArrayList<NotaFiscal> notasFiscais;

    public RepositoryInvoice(ArrayList<NotaFiscal> notasFiscais) {
        this.notasFiscais = notasFiscais;
    }

    @Override
    public NotaFiscal findById(int id) {
        NotaFiscal notaFiscal = null;
        for (NotaFiscal nf: notasFiscais) {
            if (nf.getId() == id) {
                notaFiscal = nf;
            }
        }
        return notaFiscal;
    }

    @Override
    public ArrayList<NotaFiscal> findAll() {
        return notasFiscais;
    }

    @Override
    public void update(int id, NotaFiscal nf) {
        for (int i = 0; i < notasFiscais.size(); i++) {
            if (notasFiscais.get(i).getId() == id) {
                notasFiscais.set(i, nf);
                return;
            }
        }
    }


    @Override
    public void create(NotaFiscal nf) {
        notasFiscais.add(nf);
    }

    @Override
    public void remove(NotaFiscal nf) {
        if (nf != null) notasFiscais.remove(nf);
    }
}
