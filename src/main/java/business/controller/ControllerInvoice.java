package business.controller;

import business.interfaces.IControllerInvoice;

import exceptions.InvoiceConflictException;
import exceptions.InvoiceNotFoundException;
import business.model.invoice.Invoice;
import data.SaveData;
import data.interfaces.IRepositoryInvoice;

import java.util.ArrayList;

public class ControllerInvoice implements IControllerInvoice {

    private final IRepositoryInvoice repositoryInvoice;

    public ControllerInvoice(IRepositoryInvoice repositoryInvoice) {
        this.repositoryInvoice = repositoryInvoice;
    }

    public Invoice getById(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        Invoice invoice = repositoryInvoice.findById(id);

        if (invoice == null) throw new InvoiceNotFoundException("404 - ID not found");

        return invoice;
    }

    public ArrayList<Invoice> getAll() {
        return repositoryInvoice.findAll();
    }

    public void patch(int id, Invoice invoice) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        if (invoice == null) throw new IllegalArgumentException("400 - Invoice can't be null");
        Invoice exists = repositoryInvoice.findById(id);
        if (exists == null) throw new InvoiceNotFoundException("404 - ID not found");

        repositoryInvoice.update(id, invoice);
        persist();
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        Invoice exists = repositoryInvoice.findById(id);
        if (exists == null) throw new InvoiceNotFoundException("404 - ID not found");

        repositoryInvoice.remove(exists);
        persist();
    }

    public void post(Invoice invoice) {
        Invoice exists = repositoryInvoice.findById(invoice.getId());
        if (exists != null) throw new InvoiceConflictException("409 - This invoice already exists");
        repositoryInvoice.create(invoice);
        persist();
    }

    private void persist() {
        new SaveData().saveAllInvoices(repositoryInvoice.findAll());
    }
}
