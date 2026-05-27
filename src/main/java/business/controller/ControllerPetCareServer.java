package business.controller;

import business.interfaces.*;
import data.repository.*;

import java.util.ArrayList;


public class ControllerPetCareServer {


    private static ControllerPetCareServer instance;


    private IControllerPessoa controllerPerson;
    private IControllerAnimal controllerAnimal;
    private IControllerAppointment controllerAppointment;
    private IControllerInvoice controllerInvoice;
    private IControllerStock controllerStock;



    private ControllerPetCareServer() {

        RepositoryAnimal repAnimal = new RepositoryAnimal();
        RepositoryPerson repPerson = new RepositoryPerson(new ArrayList<>());
        RepositoryAppointment repAppointment = new RepositoryAppointment(new ArrayList<>());
        RepositoryInvoice repInvoice = new RepositoryInvoice(new ArrayList<>());
        RepositoryStock repStock = new RepositoryStock(new ArrayList<>());


        this.controllerPerson = new ControllerPessoa(repPerson);
        this.controllerAnimal = new ControllerAnimal(repAnimal);
        this.controllerAppointment = new ControllerAppointment(repAppointment);
        this.controllerInvoice = new ControllerInvoice(repInvoice);
        this.controllerStock = new ControllerStock(repStock);
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



    public IControllerPessoa getPerson() {
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
}
