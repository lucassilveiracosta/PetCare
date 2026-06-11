package business.controller;

import business.interfaces.*;
import business.model.animal.DomesticAnimal;
import business.model.appointment.Appointment;
import business.model.person.Owner;
import business.model.person.Person;
import business.model.person.Veterinarian;
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



    private ControllerPetCareServer() {

        data.LoadData loader = new data.LoadData();

        ArrayList<Owner> donos = loader.loadOwners();
        ArrayList<Veterinarian> vets = loader.loadVeterinarians();
        
        ArrayList<business.model.person.Person> pessoasCarregadas = new ArrayList<>();
        pessoasCarregadas.addAll(donos);
        pessoasCarregadas.addAll(vets);
        
        RepositoryPerson repPerson = new RepositoryPerson(pessoasCarregadas);

        RepositoryAnimal repAnimal = new RepositoryAnimal();
        ArrayList<DomesticAnimal> animais = loader.loadDomesticAnimals(donos);
        for (DomesticAnimal a : animais) {
            repAnimal.create(a);
        }

        ArrayList<Appointment> consultas = loader.loadAppointments(animais, vets);

        RepositoryAppointment repAppointment = new RepositoryAppointment(new ArrayList<>(consultas));
        
        RepositoryInvoice repInvoice = new RepositoryInvoice(new ArrayList<>());
        RepositoryStock repStock = new RepositoryStock(new ArrayList<>());
        RepositoryExpense repExpense = new RepositoryExpense(new ArrayList<>());


        this.controllerPerson = new ControllerPerson(repPerson);
        this.controllerAnimal = new ControllerAnimal(repAnimal);
        this.controllerAppointment = new ControllerAppointment(repAppointment);
        this.controllerInvoice = new ControllerInvoice(repInvoice);
        this.controllerStock = new ControllerStock(repStock);
        this.controllerExpense = new ControllerExpense(repExpense);
    }

    /**
     * Método público para a GUI pegar a instância do Servidor.
     * Se ela ainda não existir, ele cria. Se já existir, ele retorna a mesma.
     */
    public static ControllerPetCareServer getInstance() {
        if (instance == null) {
            instance = new ControllerPetCareServer();
        }
        return instance;
    }



    public IControllerPerson getPessoa() {
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
}
