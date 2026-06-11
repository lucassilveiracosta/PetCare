package business.controller;

import business.interfaces.IControllerInvoice;

import exceptions.InvoiceConflictException;
import exceptions.InvoiceNotFoundException;
import exceptions.ScheduleConflictException;
import exceptions.UnpaidInvoiceException;
import business.model.invoice.Invoice;
import business.model.invoice.PetShopService;
import business.model.invoice.Procedure;
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
        validateNoServiceConflict(invoice); // REQ05
        repositoryInvoice.create(invoice);
        persist();
    }

    /**
     * REQ05 - An employee cannot have two pet shop services (bath/grooming) at the
     * same date/time. Pet shop services are stored as procedures inside invoices.
     *
     * @throws ScheduleConflictException when the responsible employee is already booked.
     */
    private void validateNoServiceConflict(Invoice invoice) {
        if (invoice.getProcedures() == null) return;
        for (Procedure proc : invoice.getProcedures()) {
            if (!(proc instanceof PetShopService newService)) continue;
            if (newService.getResponsibleEmployee() == null || newService.getDateHourScheduled() == null) continue;
            int empId = newService.getResponsibleEmployee().getId();
            for (Invoice inv : repositoryInvoice.findAll()) {
                if (inv.getProcedures() == null) continue;
                for (Procedure existing : inv.getProcedures()) {
                    if (existing instanceof PetShopService s
                            && s.getResponsibleEmployee() != null
                            && s.getResponsibleEmployee().getId() == empId
                            && newService.getDateHourScheduled().equals(s.getDateHourScheduled())) {
                        throw new ScheduleConflictException("Conflito de agenda: "
                                + newService.getResponsibleEmployee().getName()
                                + " já tem um serviço marcado nesse horário.");
                    }
                }
            }
        }
    }

    /** Marks an invoice as settled (used to clear hospital costs before discharge). */
    @Override
    public void markAsPaid(int id) {
        Invoice invoice = getById(id);
        invoice.setPaid(true);
        persist();
    }

    /**
     * REQ16 - Blocks the discharge (alta) of an animal while it still has an
     * unpaid invoice.
     *
     * @throws UnpaidInvoiceException when the patient has at least one unpaid invoice.
     */
    @Override
    public void validateDischarge(int animalId) {
        for (Invoice invoice : repositoryInvoice.findAll()) {
            if (invoice.getPatient().getId() == animalId && !invoice.isPaid()) {
                throw new UnpaidInvoiceException("Alta bloqueada: o animal "
                        + invoice.getPatient().getName() + " possui fatura pendente (#"
                        + invoice.getId() + "). Quite os custos antes da alta.");
            }
        }
    }

    private void persist() {
        new SaveData().saveAllInvoices(repositoryInvoice.findAll());
    }
}
