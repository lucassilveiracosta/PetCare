package business.controller;

import business.interfaces.*;
import business.model.animal.DomesticAnimal;
import business.model.appointment.Appointment;
import business.model.invoice.Expense;
import business.model.invoice.Hospitalization;
import business.model.invoice.Invoice;
import business.model.invoice.Product;
import business.model.invoice.PetShopService;
import business.model.invoice.Surgery;
import business.model.person.Employee;
import business.model.person.Owner;
import business.model.person.Person;
import business.model.person.Veterinarian;
import data.LoadData;
import data.SaveData;
import data.repository.*;

import java.util.ArrayList;


public class ControllerPetCareServer {


    private static ControllerPetCareServer instance;


    private IControllerPerson controllerPerson;
    private IControllerAnimal controllerAnimal;
    private IControllerAppointment controllerAppointment;
    private IControllerInvoice controllerInvoice;
    private IControllerStock controllerStock;
    private IControllerExpense controllerExpense;
    private IControllerSurgery controllerSurgery;
    private IControllerHospitalization controllerHospitalization;



    private ControllerPetCareServer() {

        LoadData loader = new LoadData();

        // ── Persons (owners + vets + employees) ──────────────────────────────
        ArrayList<Owner> owners = loader.loadOwners();
        ArrayList<Veterinarian> vets = loader.loadVeterinarians();
        ArrayList<Employee> employees = loader.loadEmployees();
        ArrayList<Person> persons = new ArrayList<>();
        persons.addAll(owners);
        persons.addAll(vets);
        persons.addAll(employees);
        RepositoryPerson repPerson = new RepositoryPerson(persons);

        // ── Animals ──────────────────────────────────────────────────────────
        RepositoryAnimal repAnimal = new RepositoryAnimal();
        ArrayList<DomesticAnimal> animals = loader.loadDomesticAnimals(owners);
        for (DomesticAnimal a : animals) repAnimal.create(a);

        // ── Appointments / pet shop services ─────────────────────────────────
        ArrayList<Appointment> appointments = loader.loadAppointments(animals, vets);
        RepositoryAppointment repAppointment = new RepositoryAppointment(new ArrayList<>(appointments));
        ArrayList<PetShopService> services = loader.loadPetShopServices(animals, employees);

        // ── Surgeries ────────────────────────────────────────────────────────
        ArrayList<Surgery> surgeries = loader.loadSurgeries(animals, vets);
        RepositorySurgery repSurgery = new RepositorySurgery(surgeries);

        // ── Hospitalizations ─────────────────────────────────────────────────
        ArrayList<Hospitalization> hospitalizations = loader.loadHospitalizations(animals, vets);
        RepositoryHospitalization repHospitalization = new RepositoryHospitalization(hospitalizations);

        // ── Stock / expenses / invoices ──────────────────────────────────────
        ArrayList<Product> products = loader.loadProducts();
        RepositoryStock repStock = new RepositoryStock(products);
        ArrayList<Expense> expenses = loader.loadExpenses();
        RepositoryExpense repExpense = new RepositoryExpense(expenses);
        ArrayList<Invoice> invoices = loader.loadInvoices(owners, animals, appointments, services, surgeries, hospitalizations, products);
        RepositoryInvoice repInvoice = new RepositoryInvoice(invoices);

        this.controllerPerson = new ControllerPerson(repPerson);
        this.controllerAnimal = new ControllerAnimal(repAnimal);
        this.controllerAppointment = new ControllerAppointment(repAppointment);
        this.controllerInvoice = new ControllerInvoice(repInvoice);
        this.controllerStock = new ControllerStock(repStock);
        this.controllerExpense = new ControllerExpense(repExpense);
        this.controllerSurgery = new ControllerSurgery(repSurgery);
        this.controllerHospitalization = new ControllerHospitalization(repHospitalization);
    }

    public static ControllerPetCareServer getInstance() {
        if (instance == null) {
            instance = new ControllerPetCareServer();
        }
        return instance;
    }

    /** Rewrites the whole CSV database from the current in-memory state. */
    public void saveAll() {
        SaveData sd = new SaveData();
        sd.saveAllPersons(new ArrayList<>(controllerPerson.getAll()));
        sd.saveAllAnimals(new ArrayList<>(controllerAnimal.getAll()));
        sd.saveAllAppointments(new ArrayList<>(controllerAppointment.getAll()));
        sd.saveAllProducts(new ArrayList<>(controllerStock.getAll()));
        sd.saveAllExpenses(new ArrayList<>(controllerExpense.getAll()));
        sd.saveAllSurgeries(new ArrayList<>(controllerSurgery.getAll()));
        sd.saveAllHospitalizations(new ArrayList<>(controllerHospitalization.getAll()));
        sd.saveAllInvoices(new ArrayList<>(controllerInvoice.getAll()));
    }

    public IControllerPerson getPerson() {
        return controllerPerson;
    }

    public IControllerAnimal getAnimal() {
        return controllerAnimal;
    }

    public IControllerAppointment getAppointment() {
        return controllerAppointment;
    }

    public IControllerInvoice getInvoice() {
        return controllerInvoice;
    }

    public IControllerStock getStock() {
        return controllerStock;
    }

    public IControllerExpense getExpense() {
        return controllerExpense;
    }

    public IControllerSurgery getSurgery() {
        return controllerSurgery;
    }

    public IControllerHospitalization getHospitalization() {
        return controllerHospitalization;
    }
}
