package gui;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerAnimal;
import business.interfaces.IControllerAppointment;
import business.interfaces.IControllerPerson;
import business.model.animal.DomesticAnimal;
import business.model.animal.ExoticAnimal;
import business.model.animal.Vaccine;
import business.model.appointment.*;
import business.model.invoice.Expense;
import business.model.invoice.Invoice;
import business.model.invoice.Procedure;
import business.model.invoice.Product;
import business.model.person.Employee;
import business.model.person.Owner;
import business.model.person.Person;
import business.model.person.Specialty;
import business.model.person.Veterinarian;
import data.interfaces.IRepositoryPerson;
import data.repository.RepositoryPerson;
import enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MockDataLoader {

    private static boolean loaded = false;

    public void load() {
        // Os dados mockados foram desativados porque o sistema agora lê e persiste 
        // os dados reais através dos arquivos CSV.
    }
}
