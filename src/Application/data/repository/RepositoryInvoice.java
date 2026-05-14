package data.repository;

import business.model.notaFiscal.NotaFiscal;
import data.interfaces.IRepositoryInvoice;

import java.util.ArrayList;

public class RepositoryInvoice implements IRepositoryInvoice {

    private ArrayList<NotaFiscal> notasFiscais;

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
    public void update(int index, NotaFiscal nf) { // avaliar como implementar update, sem prints na camada de dados
        notasFiscais.set(index, nf);
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
