package buissness;

import exceptions.InvoiceConflictException;
import exceptions.InvoiceNotFoundException;
import model.notaFiscal.NotaFiscal;
import repository.Interface.IRepositoryInvoice;

import java.util.ArrayList;

public class BuissnessInvoice {

    private IRepositoryInvoice repositoryInvoice;

    public NotaFiscal getById(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");
        NotaFiscal nf = repositoryInvoice.findById(id);

        if (nf == null) throw new InvoiceNotFoundException("404 - ID not found");


        return nf;
    }

    public ArrayList<NotaFiscal> getAll() {
        return repositoryInvoice.findAll();
    }

    public void patch(int id, NotaFiscal nf) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");
        NotaFiscal notaFiscal = repositoryInvoice.findById(id);
        if (notaFiscal == null) throw new InvoiceNotFoundException("404 - ID not found");
        if (nf == null) throw new IllegalArgumentException("Invoice can't be null");
        repositoryInvoice.update(id, nf);
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");
        NotaFiscal notaFiscal = repositoryInvoice.findById(id);
        if (notaFiscal == null) throw new InvoiceNotFoundException("404 - ID not found");

        repositoryInvoice.remove(notaFiscal);
    }

    public void post(NotaFiscal nf) {
        NotaFiscal exists = repositoryInvoice.findById(nf.getId());
        if (nf == exists) throw new InvoiceConflictException("This invoice already exists");
        repositoryInvoice.create(nf);
    }
}
