package gui;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerPerson;
import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.animal.Vaccine;
import business.model.appointment.Anamnesis;
import business.model.appointment.Appointment;
import business.model.appointment.Hydration;
import business.model.appointment.PhysicalExamination;
import business.model.appointment.VitalParameters;
import business.model.invoice.Expense;
import business.model.invoice.Invoice;
import business.model.invoice.Procedure;
import business.model.invoice.Product;
import business.model.person.Employee;
import business.model.person.Owner;
import business.model.person.Specialty;
import business.model.person.Veterinarian;
import enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Seeds the CSV database with test data on first run (when empty). On later runs
 * the data comes from {@code database/} and the guards below skip re-seeding.
 */
public class MockDataLoader {

    private static boolean loaded = false;

    public void load() {
        if (loaded) return;
        loaded = true;

        ControllerPetCareServer server = ControllerPetCareServer.getInstance();
        IControllerPerson person = server.getPerson();

        boolean freshDatabase = person.getAllOwners().isEmpty()
                && person.getAllVets().isEmpty()
                && server.getAnimal().getAll().isEmpty();

        if (freshDatabase) {
            seedEverything(server, person);
        } else {
            // DB already has people/animals; only ensure the side entities exist
            if (person.getAllEmployees().isEmpty()) seedEmployees(person);
            if (server.getStock().getAll().isEmpty()) seedStock(server);
            if (server.getExpense().getAll().isEmpty()) seedExpenses(server);
        }

        server.saveAll();
    }

    private void seedEverything(ControllerPetCareServer server, IControllerPerson person) {
        // ── Veterinarians (with specialties) ─────────────────────────────────
        ArrayList<Specialty> carterSpec = new ArrayList<>();
        carterSpec.add(new Specialty("Feline Medicine", "Cat diseases and surgery"));
        Veterinarian carter = new Veterinarian("Dr. Sarah Carter", "sarah.carter@petcare.com", "password123",
                LocalDate.of(1985, 4, 12), "111.222.333-44", "(11) 91234-5678", "CRMV-SP 12345", carterSpec);

        ArrayList<Specialty> williamsSpec = new ArrayList<>();
        williamsSpec.add(new Specialty("General Clinic", "General veterinary practice"));
        williamsSpec.add(new Specialty("Dermatology", "Skin and coat conditions"));
        Veterinarian williams = new Veterinarian("Dr. John Williams", "john.williams@petcare.com", "password123",
                LocalDate.of(1979, 8, 25), "222.333.444-55", "(11) 99876-5432", "CRMV-SP 67890", williamsSpec);

        person.post(carter);
        person.post(williams);

        // ── Owners ───────────────────────────────────────────────────────────
        Owner michael = new Owner("Michael Turner", "michael.turner@email.com", "password123",
                LocalDate.of(1990, 3, 5), "333.444.555-66", "(11) 97654-3210", "Teacher", "Regular afternoon customer");
        Owner emma = new Owner("Emma Davis", "emma.davis@email.com", "password123",
                LocalDate.of(1995, 7, 18), "444.555.666-77", "(11) 96543-2109", "Nurse", "Prefers morning appointments");
        Owner carlos = new Owner("Carlos Mendes", "carlos.mendes@email.com", "password123",
                LocalDate.of(1987, 11, 22), "555.666.777-11", "(11) 95555-7788", "Engineer", "Owns two dogs");
        Owner ana = new Owner("Ana Souza", "ana.souza@email.com", "password123",
                LocalDate.of(1993, 2, 9), "666.777.888-22", "(11) 94444-9900", "Designer", "New customer");
        person.post(michael);
        person.post(emma);
        person.post(carlos);
        person.post(ana);

        seedEmployees(person);

        // ── Animals (with vaccines on some) ──────────────────────────────────
        ArrayList<Vaccine> rexV = new ArrayList<>();
        rexV.add(new Vaccine("Rabies", LocalDate.of(2025, 1, 10), "Antirabies vaccine", true, LocalDate.of(2027, 1, 10)));
        DomesticAnimal rex = new DomesticAnimal("Rex", "Dog", "German Shepherd", LocalDate.of(2020, 6, 15),
                StageOfLife.ADULT, 28.5, Size.LARGE, Sex.MALE, michael, Temperament.REACTIVE, false, rexV);

        DomesticAnimal whiskers = new DomesticAnimal("Whiskers", "Cat", "Persian", LocalDate.of(2021, 11, 3),
                StageOfLife.ADULT, 4.2, Size.SMALL, Sex.FEMALE, michael, Temperament.DOCILE, true, new ArrayList<>());

        ArrayList<Vaccine> buddyV = new ArrayList<>();
        buddyV.add(new Vaccine("Rabies", LocalDate.of(2025, 5, 20), "Antirabies vaccine", true, LocalDate.of(2027, 5, 20)));
        DomesticAnimal buddy = new DomesticAnimal("Buddy", "Dog", "Golden Retriever", LocalDate.of(2019, 2, 28),
                StageOfLife.ADULT, 32.0, Size.LARGE, Sex.MALE, emma, Temperament.DOCILE, true, buddyV);

        DomesticAnimal thor = new DomesticAnimal("Thor", "Dog", "Rottweiler", LocalDate.of(2021, 1, 10),
                StageOfLife.ADULT, 40.0, Size.GIANT, Sex.MALE, michael, Temperament.REACTIVE, false, new ArrayList<>());

        ArrayList<Vaccine> lunaV = new ArrayList<>();
        lunaV.add(new Vaccine("Rabies", LocalDate.of(2023, 3, 1), "Antirabies vaccine expired", true, LocalDate.of(2024, 3, 1)));
        DomesticAnimal luna = new DomesticAnimal("Luna", "Cat", "Tabby", LocalDate.of(2022, 8, 14),
                StageOfLife.ADULT, 3.8, Size.SMALL, Sex.FEMALE, emma, Temperament.ANXIOUS, false, lunaV);

        DomesticAnimal mia = new DomesticAnimal("Mia", "Cat", "Siamese", LocalDate.of(2023, 5, 5),
                StageOfLife.ADULT, 3.5, Size.SMALL, Sex.FEMALE, carlos, Temperament.DOCILE, true, new ArrayList<>());
        DomesticAnimal bella = new DomesticAnimal("Bella", "Dog", "Poodle", LocalDate.of(2022, 9, 20),
                StageOfLife.ADULT, 8.0, Size.MEDIUM, Sex.FEMALE, ana, Temperament.DOCILE, true, new ArrayList<>());

        for (DomesticAnimal a : List.of(rex, whiskers, buddy, thor, luna, mia, bella)) {
            server.getAnimal().post(a);
        }

        // ── Appointments ─────────────────────────────────────────────────────
        // Completed consultations dated relative to "now" so the clinical history
        // (this week / this month) shows data. Plus a couple older ones and pending.
        LocalDateTime now = LocalDateTime.now();
        server.getAppointment().post(completed(120.0, rex, now.minusDays(2).withHour(10).withMinute(0),
                "Gastrointestinal consultation", williams, "Gastrointestinal infection",
                "Metronidazole 250 mg for 7 days"));
        server.getAppointment().post(completed(150.0, rex, now.minusDays(20).withHour(9).withMinute(0),
                "Dermatology follow-up", williams, "Allergic dermatitis", "Hydrocortisone cream for 10 days"));
        server.getAppointment().post(completed(160.0, buddy, now.minusDays(3).withHour(15).withMinute(0),
                "Post-surgery check", carter, "Healthy recovery", "Rest for 14 days"));
        server.getAppointment().post(completed(200.0, luna, now.minusDays(5).withHour(16).withMinute(0),
                "Respiratory infection", carter, "Upper respiratory tract infection", "Doxycycline for 10 days"));
        server.getAppointment().post(completed(130.0, whiskers, now.minusDays(9).withHour(11).withMinute(0),
                "Anemia investigation", carter, "Mild normocytic anemia", "Iron supplement for 30 days"));

        // Pending (future) consultations for the dashboard / waiting list
        server.getAppointment().post(new Appointment(120.0, thor, now.plusDays(7).withHour(9).withMinute(0), "Annual checkup", williams));
        server.getAppointment().post(new Appointment(130.0, mia, now.plusDays(15).withHour(10).withMinute(0), "Annual vaccination", williams));
        server.getAppointment().post(new Appointment(100.0, bella, now.plusDays(20).withHour(18).withMinute(0), "General consultation", carter));

        seedStock(server);
        seedExpenses(server);

        // ── Invoices from the completed appointments ─────────────────────────
        List<Product> products = server.getStock().getAll();
        for (Appointment a : server.getAppointment().getAll()) {
            if (a.getDiagnosis() == null) continue;
            if (!(a.getPatient() instanceof DomesticAnimal da)) continue;
            ArrayList<Procedure> procs = new ArrayList<>();
            procs.add(a);
            ArrayList<Product> sold = new ArrayList<>();
            if (!products.isEmpty()) sold.add(products.get(0));
            server.getInvoice().post(new Invoice(da.getOwner(), da, procs, sold));
        }

        // Flag a couple consultations as needing surgery / hospitalization (Surgery Center).
        // Buddy gets both flags AND has an unpaid invoice → demonstrates REQ16 (discharge block).
        for (Appointment a : server.getAppointment().getAll()) {
            if (a.getPatient().getName().equals("Buddy")) {
                a.setNeedsSurgery(true);
                a.setNeedsHospitalization(true);
            }
            if (a.getPatient().getName().equals("Thor")) a.setNeedsHospitalization(true);
        }
    }

    private Appointment completed(double price, Animal patient, LocalDateTime when, String desc,
                                  Veterinarian vet, String diagnosis, String prescription) {
        VitalParameters vitals = new VitalParameters(92, 22, 38.6, Mucosa.NORMAL_COLORED, 2,
                new Hydration(true, null), "Within normal range");
        PhysicalExamination exam = new PhysicalExamination(Conscience.ALERT, vitals, "Alert and cooperative");
        Anamnesis an = new Anamnesis("Recorded complaint", "No restrictions", "Consultation finalized");
        // Boolean variant allows past dates
        return new Appointment(price, patient, when, desc, vet, diagnosis, prescription, an, exam,
                AppointmentStatus.COMPLETED, true);
    }

    private void seedEmployees(IControllerPerson person) {
        person.post(new Employee("Olivia Brooks", "olivia.brooks@petcare.com", "password123",
                LocalDate.of(1992, 9, 14), "555.666.777-88", "(11) 95555-1122", "Groomer", "Morning"));
        person.post(new Employee("Daniel Reed", "daniel.reed@petcare.com", "password123",
                LocalDate.of(1988, 1, 30), "666.777.888-99", "(11) 94444-3344", "Bather", "Afternoon"));
        person.post(new Employee("Sophia Lima", "sophia.lima@petcare.com", "password123",
                LocalDate.of(1995, 5, 2), "888.999.000-11", "(11) 93333-5566", "Groomer", "Afternoon"));
    }

    private void seedStock(ControllerPetCareServer server) {
        server.getStock().post(new Product("Premium Dog Food 10kg", 40, "Dry food for adult large dogs", 120.00, false, null));
        server.getStock().post(new Product("Hypoallergenic Cat Shampoo", 25, "Gentle shampoo for cats", 22.50, false, null));
        server.getStock().post(new Product("Rubber Chew Toy", 12, "Durable chew toy for dogs", 18.00, false, null));
        server.getStock().post(new Product("Cat Litter 5kg", 30, "Clumping litter", 35.90, false, null));
        server.getStock().post(new Product("Dog Collar M", 3, "Adjustable nylon collar", 27.00, false, null));
        server.getStock().post(new Product("Amoxicillin 500mg", 50, "Broad-spectrum antibiotic", 15.50, true, MedicineType.COMMON));
        server.getStock().post(new Product("Tramadol 50mg", 20, "Controlled analgesic", 28.00, true, MedicineType.CONTROLLED));
        server.getStock().post(new Product("V8 Vaccine", 8, "Polyvalent canine vaccine", 45.00, true, MedicineType.COMMON));
        server.getStock().post(new Product("Anti-flea Pipette", 2, "Topical flea treatment", 39.90, true, MedicineType.COMMON));
    }

    private void seedExpenses(ControllerPetCareServer server) {
        LocalDate today = LocalDate.now();
        server.getExpense().post(new Expense(ExpenseType.INVENTORY, 850.0, today.withDayOfMonth(3), "Monthly medicine restock"));
        server.getExpense().post(new Expense(ExpenseType.INVENTORY, 420.0, today.withDayOfMonth(8), "Pet shop products restock"));
        server.getExpense().post(new Expense(ExpenseType.SALARY, 6200.0, today.withDayOfMonth(5), "Staff salaries"));
        server.getExpense().post(new Expense(ExpenseType.RENT, 1800.0, today.withDayOfMonth(10), "Clinic rent"));
        server.getExpense().post(new Expense(ExpenseType.EQUIPMENT, 650.0, today.withDayOfMonth(2), "New ultrasound probe"));
    }
}
