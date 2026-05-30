package main.java.business.interfaces;

import main.java.business.model.invoice.Invoice;

import java.util.List;

public interface IControllerInvoice {
    Invoice getById(int id);
    List<Invoice> getAll();
    void patch(int id, Invoice nf);
    void delete(int id);
    void post(Invoice nf);
}
