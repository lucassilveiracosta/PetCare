package data;

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
import business.model.invoice.ServicoPetShop;
import business.model.invoice.Surgery;
import business.model.person.Employee;
import business.model.person.Owner;
import business.model.person.Specialty;
import business.model.person.Veterinarian;
import enums.AppointmentStatus;
import enums.Conscience;
import enums.ExpenseType;
import enums.MedicineType;
import enums.Mucosa;
import enums.PetShopServices;
import enums.Sex;
import enums.SurgeryRisk;
import enums.Size;
import enums.StageOfLife;
import enums.Temperament;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads the CSV "database" (folder {@code database/}). Files are header-first and
 * use ';' as the delimiter; sub-lists use '|' and '~'. Bad lines are skipped.
 */
public class LoadData {

    private static final String DIR = SaveData.DIR;
    private static final DateTimeFormatter D = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /** Reads all data rows of a file (skips the header and blank lines). */
    private List<String[]> rows(String file) {
        List<String[]> out = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DIR + file))) {
            String line;
            boolean header = true;
            while ((line = br.readLine()) != null) {
                if (header) { header = false; continue; }
                if (line.trim().isEmpty()) continue;
                out.add(line.split(";", -1));
            }
        } catch (IOException e) {
            // arquivo ainda não existe — lista vazia
        }
        return out;
    }

    private static boolean isNull(String s) {
        return s == null || s.isBlank() || s.equals("null");
    }

    private static Integer parseIntN(String s) {
        return isNull(s) ? null : Integer.valueOf(s.trim());
    }

    private static Double parseDblN(String s) {
        return isNull(s) ? null : Double.valueOf(s.trim());
    }

    public ArrayList<Owner> loadOwners() {
        ArrayList<Owner> owners = new ArrayList<>();
        for (String[] d : rows("owners.csv")) {
            try {
                Owner o = new Owner(d[1], d[2], d[3], LocalDate.parse(d[4], D), d[5], d[6], d[7], d[8]);
                o.setId(Integer.parseInt(d[0]));
                owners.add(o);
            } catch (Exception e) { /* skip bad line */ }
        }
        return owners;
    }

    public ArrayList<Veterinarian> loadVeterinarians() {
        ArrayList<Veterinarian> vets = new ArrayList<>();
        for (String[] d : rows("veterinarians.csv")) {
            try {
                ArrayList<Specialty> specs = new ArrayList<>();
                if (d.length > 8 && !isNull(d[8])) {
                    for (String s : d[8].split("\\|")) {
                        String[] parts = s.split("~", -1);
                        String desc = parts.length > 1 && !parts[1].isBlank() ? parts[1] : "-";
                        specs.add(new Specialty(parts[0], desc));
                    }
                }
                Veterinarian v = new Veterinarian(d[1], d[2], d[3], LocalDate.parse(d[4], D), d[5], d[6], d[7], specs);
                v.setId(Integer.parseInt(d[0]));
                vets.add(v);
            } catch (Exception e) { /* skip */ }
        }
        return vets;
    }

    public ArrayList<Employee> loadEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        for (String[] d : rows("employees.csv")) {
            try {
                Employee e = new Employee(d[1], d[2], d[3], LocalDate.parse(d[4], D), d[5], d[6], d[7], d[8]);
                e.setId(Integer.parseInt(d[0]));
                employees.add(e);
            } catch (Exception ex) { /* skip */ }
        }
        return employees;
    }

    public ArrayList<DomesticAnimal> loadDomesticAnimals(List<Owner> owners) {
        ArrayList<DomesticAnimal> animals = new ArrayList<>();
        for (String[] d : rows("animals.csv")) {
            try {
                int ownerId = Integer.parseInt(d[10]);
                Owner owner = null;
                for (Owner o : owners) if (o.getId() == ownerId) { owner = o; break; }
                if (owner == null) continue;

                ArrayList<Vaccine> vaccines = new ArrayList<>();
                if (d.length > 11 && !isNull(d[11])) {
                    for (String s : d[11].split("\\|")) {
                        String[] p = s.split("~", -1);
                        vaccines.add(new Vaccine(p[0], LocalDate.parse(p[1], D), p[2],
                                Boolean.parseBoolean(p[3]), LocalDate.parse(p[4], D)));
                    }
                }

                DomesticAnimal a = new DomesticAnimal(d[1], d[2], d[3], LocalDate.parse(d[8], D),
                        StageOfLife.valueOf(d[9]), Double.parseDouble(d[5]), Size.valueOf(d[7]),
                        Sex.valueOf(d[6]), owner, Temperament.valueOf(d[4]), false, vaccines);
                a.setId(Integer.parseInt(d[0]));
                animals.add(a);
            } catch (Exception e) { /* skip */ }
        }
        return animals;
    }

    public ArrayList<Appointment> loadAppointments(List<DomesticAnimal> animals, List<Veterinarian> vets) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        for (String[] d : rows("appointments.csv")) {
            try {
                int id = Integer.parseInt(d[0]);
                Double price = Double.parseDouble(d[1]);
                int patientId = Integer.parseInt(d[2]);
                LocalDateTime dateHour = LocalDateTime.parse(d[3], DT);
                String description = d[4];
                int vetId = Integer.parseInt(d[5]);
                String diagnosis = isNull(d[6]) ? null : d[6];
                String prescription = isNull(d[7]) ? null : d[7];
                AppointmentStatus status = isNull(d[8]) ? null : AppointmentStatus.valueOf(d[8]);
                Anamnesis anamnesis = null;
                if (d.length > 9 && !isNull(d[9])) {
                    String dietary = d.length > 10 && !isNull(d[10]) ? d[10] : "-";
                    anamnesis = new Anamnesis(d[9], dietary, "Consultation finalized");
                }

                DomesticAnimal patient = null;
                for (DomesticAnimal a : animals) if (a.getId() == patientId) { patient = a; break; }
                Veterinarian vet = null;
                for (Veterinarian v : vets) if (v.getId() == vetId) { vet = v; break; }
                if (patient == null || vet == null) continue;

                boolean past = dateHour.isBefore(LocalDateTime.now());
                Appointment app;
                if (diagnosis == null) {
                    app = past ? new Appointment(price, patient, dateHour, description, vet, true)
                               : new Appointment(price, patient, dateHour, description, vet);
                } else {
                    app = past
                        ? new Appointment(price, patient, dateHour, description, vet, diagnosis, prescription, anamnesis, null, status, true)
                        : new Appointment(price, patient, dateHour, description, vet, diagnosis, prescription, anamnesis, null, status);
                }
                app.setId(id);

                // Physical examination + vital parameters (columns 11..20)
                if (d.length > 11 && !isNull(d[11])) {
                    VitalParameters vp = new VitalParameters(
                            parseIntN(d[14]), parseIntN(d[15]), parseDblN(d[13]),
                            isNull(d[17]) ? null : Mucosa.valueOf(d[17]), parseIntN(d[16]),
                            new Hydration(Boolean.parseBoolean(d[18]), parseDblN(d[19])),
                            isNull(d[20]) ? "-" : d[20]);
                    PhysicalExamination exam = new PhysicalExamination(
                            Conscience.valueOf(d[11]), vp, isNull(d[12]) ? "-" : d[12]);
                    app.setPhisicalExam(exam);
                }

                if (d.length > 21) app.setNeedsSurgery(Boolean.parseBoolean(d[21]));
                if (d.length > 22) app.setNeedsHospitalization(Boolean.parseBoolean(d[22]));

                appointments.add(app);
            } catch (Exception e) { /* skip */ }
        }
        return appointments;
    }

    public ArrayList<ServicoPetShop> loadPetShopServices(List<DomesticAnimal> animals, List<Employee> employees) {
        ArrayList<ServicoPetShop> services = new ArrayList<>();
        for (String[] d : rows("petshopServices.csv")) {
            try {
                int animalId = Integer.parseInt(d[2]);
                int empId = Integer.parseInt(d[6]);
                DomesticAnimal animal = null;
                for (DomesticAnimal a : animals) if (a.getId() == animalId) { animal = a; break; }
                Employee emp = null;
                for (Employee e : employees) if (e.getId() == empId) { emp = e; break; }
                if (animal == null || emp == null) continue;

                ServicoPetShop s = new ServicoPetShop(Double.parseDouble(d[1]), animal,
                        LocalDateTime.parse(d[3], DT), d[4], PetShopServices.valueOf(d[5]), emp);
                s.setId(Integer.parseInt(d[0]));
                services.add(s);
            } catch (Exception e) { /* skip */ }
        }
        return services;
    }

    public ArrayList<Product> loadProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for (String[] d : rows("products.csv")) {
            try {
                boolean isVet = Boolean.parseBoolean(d[5]);
                MedicineType med = isVet && !isNull(d[6]) ? MedicineType.valueOf(d[6]) : null;
                Product p = new Product(d[1], Integer.parseInt(d[2]), d[4], Double.parseDouble(d[3]), isVet, med);
                p.setId(Integer.parseInt(d[0]));
                products.add(p);
            } catch (Exception e) { /* skip */ }
        }
        return products;
    }

    public ArrayList<Expense> loadExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();
        for (String[] d : rows("expenses.csv")) {
            try {
                Expense e = new Expense(ExpenseType.valueOf(d[1]), Double.parseDouble(d[2]),
                        LocalDate.parse(d[3], D), d[4]);
                e.setId(Integer.parseInt(d[0]));
                expenses.add(e);
            } catch (Exception ex) { /* skip */ }
        }
        return expenses;
    }

    public ArrayList<Surgery> loadSurgeries(List<DomesticAnimal> animals, List<Veterinarian> vets) {
        ArrayList<Surgery> surgeries = new ArrayList<>();
        for (String[] d : rows("surgeries.csv")) {
            try {
                int patientId = Integer.parseInt(d[2]);
                int vetId = Integer.parseInt(d[5]);
                DomesticAnimal patient = null;
                for (DomesticAnimal a : animals) if (a.getId() == patientId) { patient = a; break; }
                Veterinarian vet = null;
                for (Veterinarian v : vets) if (v.getId() == vetId) { vet = v; break; }
                if (patient == null || vet == null) continue;

                Surgery s = new Surgery(true, Double.parseDouble(d[1]), patient,
                        LocalDateTime.parse(d[3], DT), d[4], SurgeryRisk.valueOf(d[6]), vet, d[7]);
                s.setSupplies(d.length > 8 ? d[8] : "-");
                s.setId(Integer.parseInt(d[0]));
                surgeries.add(s);
            } catch (Exception e) { /* skip */ }
        }
        return surgeries;
    }

    public ArrayList<Invoice> loadInvoices(List<Owner> owners, List<DomesticAnimal> animals,
                                           List<Appointment> appointments, List<ServicoPetShop> services,
                                           List<Surgery> surgeries, List<Product> products) {
        ArrayList<Invoice> invoices = new ArrayList<>();
        for (String[] d : rows("invoices.csv")) {
            try {
                int ownerId = Integer.parseInt(d[1]);
                int animalId = Integer.parseInt(d[2]);
                Owner owner = null;
                for (Owner o : owners) if (o.getId() == ownerId) { owner = o; break; }
                DomesticAnimal animal = null;
                for (DomesticAnimal a : animals) if (a.getId() == animalId) { animal = a; break; }
                if (owner == null || animal == null) continue;

                ArrayList<Procedure> procs = new ArrayList<>();
                if (d.length > 4 && !isNull(d[4])) {
                    for (String ref : d[4].split("\\|")) {
                        String[] r = ref.split("~", -1);
                        int rid = Integer.parseInt(r[1]);
                        if (r[0].equals("APPT")) {
                            for (Appointment a : appointments) if (a.getId() == rid) { procs.add(a); break; }
                        } else if (r[0].equals("SERVICE")) {
                            for (ServicoPetShop s : services) if (s.getId() == rid) { procs.add(s); break; }
                        } else if (r[0].equals("SURGERY")) {
                            for (Surgery s : surgeries) if (s.getId() == rid) { procs.add(s); break; }
                        }
                    }
                }
                ArrayList<Product> prods = new ArrayList<>();
                if (d.length > 5 && !isNull(d[5])) {
                    for (String pid : d[5].split("\\|")) {
                        int rid = Integer.parseInt(pid);
                        for (Product p : products) if (p.getId() == rid) { prods.add(p); break; }
                    }
                }

                Invoice inv = new Invoice(owner, animal, procs, prods);
                inv.setId(Integer.parseInt(d[0]));
                inv.setDateHour(LocalDateTime.parse(d[3], DT));
                invoices.add(inv);
            } catch (Exception e) { /* skip */ }
        }
        return invoices;
    }
}
