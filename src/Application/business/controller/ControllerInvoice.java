package business.controller;

import business.interfaces.IControllerInvoice;

import exceptions.InvoiceConflictException;
import exceptions.InvoiceNotFoundException;
import business.model.notaFiscal.NotaFiscal;
import data.interfaces.IRepositoryInvoice;

import java.util.ArrayList;

public class ControllerInvoice implements IControllerInvoice {

    private final IRepositoryInvoice repositoryInvoice;

    public ControllerInvoice(IRepositoryInvoice repositoryInvoice) {
        this.repositoryInvoice = repositoryInvoice;
    }

    public NotaFiscal getById(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        NotaFiscal invoice = repositoryInvoice.findById(id);

        if (invoice == null) throw new InvoiceNotFoundException("404 - ID not found");

        return invoice;
    }

    public ArrayList<NotaFiscal> getAll() {
        return repositoryInvoice.findAll();
    }

    public void patch(int id, NotaFiscal invoice) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        if (invoice == null) throw new IllegalArgumentException("400 - Invoice can't be null");
        NotaFiscal exists = repositoryInvoice.findById(id);
        if (exists == null) throw new InvoiceNotFoundException("404 - ID not found");

        repositoryInvoice.update(id, invoice);
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        NotaFiscal exists = repositoryInvoice.findById(id);
        if (exists == null) throw new InvoiceNotFoundException("404 - ID not found");

        repositoryInvoice.remove(exists);
    }

    public void post(NotaFiscal invoice) {
        NotaFiscal exists = repositoryInvoice.findById(invoice.getId());
        if (exists != null) throw new InvoiceConflictException("409 - This invoice already exists");
        repositoryInvoice.create(invoice);
    }
}
