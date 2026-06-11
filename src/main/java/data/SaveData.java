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
import business.model.person.Person;
import business.model.person.Specialty;
import business.model.person.Veterinarian;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Writes the whole CSV "database" (folder {@code database/}). Each saveAll*
 * rewrites the entire file from the current in-memory list (covers add/edit/remove).
 * Files are header-first and use ';' as the delimiter; sub-lists use '|' and '~'.
 */
public class SaveData {

    public static final String DIR = "database/";
    private static final DateTimeFormatter D = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace(';', ' ').replace('|', ' ').replace('~', ' ')
                .replace('\n', ' ').replace('\r', ' ').trim();
    }

    private void write(String file, String header, List<String> lines) {
        Locale.setDefault(Locale.US);
        new File(DIR).mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DIR + file, false))) {
            bw.write(header);
            bw.newLine();
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ── Persons (split into owners / veterinarians / employees) ───────────────
    public void saveAllPersons(List<Person> persons) {
        List<String> owners = new ArrayList<>();
        List<String> vets = new ArrayList<>();
        List<String> employees = new ArrayList<>();
        for (Person p : persons) {
            if (p instanceof Owner o) {
                owners.add(String.join(";",
                        String.valueOf(o.getId()), esc(o.getName()), esc(o.getEmail()), esc(o.getPassword()),
                        o.getBirthDate().format(D), esc(o.getCpf()), esc(o.getTelephone()),
                        esc(o.getJob()), esc(o.getDescription())));
            } else if (p instanceof Veterinarian v) {
                StringBuilder sp = new StringBuilder();
                if (v.getSpecialties() != null) {
                    for (Specialty s : v.getSpecialties()) {
                        if (sp.length() > 0) sp.append("|");
                        sp.append(esc(s.getName())).append("~").append(esc(s.getDescription()));
                    }
                }
                vets.add(String.join(";",
                        String.valueOf(v.getId()), esc(v.getName()), esc(v.getEmail()), esc(v.getPassword()),
                        v.getBirthDate().format(D), esc(v.getCpf()), esc(v.getTelephone()),
                        esc(v.getCrmv()), sp.toString()));
            } else if (p instanceof Employee e) {
                employees.add(String.join(";",
                        String.valueOf(e.getId()), esc(e.getName()), esc(e.getEmail()), esc(e.getPassword()),
                        e.getBirthDate().format(D), esc(e.getCpf()), esc(e.getTelephone()),
                        esc(e.getPosition()), esc(e.getWorkShift())));
            }
        }
        write("owners.csv", "id;name;email;password;birth;cpf;phone;job;description", owners);
        write("veterinarians.csv", "id;name;email;password;birth;cpf;phone;crmv;specialties", vets);
        write("employees.csv", "id;name;email;password;birth;cpf;phone;position;workShift", employees);
    }

    // ── Animals (domestic) ────────────────────────────────────────────────────
    public void saveAllAnimals(List<Animal> animals) {
        List<String> lines = new ArrayList<>();
        for (Animal a : animals) {
            if (!(a instanceof DomesticAnimal da)) continue;
            StringBuilder vac = new StringBuilder();
            if (da.getVaccines() != null) {
                for (Vaccine v : da.getVaccines()) {
                    if (vac.length() > 0) vac.append("|");
                    vac.append(esc(v.getVaccineName())).append("~")
                       .append(v.getVaccineDate().format(D)).append("~")
                       .append(esc(v.getDescription())).append("~")
                       .append(v.isRabbiesVaccine()).append("~")
                       .append(v.getExpireVaccineDate().format(D));
                }
            }
            lines.add(String.join(";",
                    String.valueOf(da.getId()), esc(da.getName()), esc(da.getSpecies()), esc(da.getRace()),
                    da.getTemperament().name(), String.valueOf(da.getWeight()), da.getSex().name(),
                    da.getSize().name(), da.getbirthDate().format(D), da.getStageOfLife().name(),
                    String.valueOf(da.getOwner().getId()), vac.toString()));
        }
        write("animals.csv",
                "id;name;species;race;temperament;weight;sex;size;birth;stage;ownerId;vaccines", lines);
    }

    // ── Appointments ──────────────────────────────────────────────────────────
    public void saveAllAppointments(List<Appointment> appointments) {
        List<String> lines = new ArrayList<>();
        for (Appointment a : appointments) {
            String diag = a.getDiagnosis() != null ? esc(a.getDiagnosis()) : "null";
            String pres = a.getMedicalPrescription() != null ? esc(a.getMedicalPrescription()) : "null";
            String stts = a.getStatus() != null ? a.getStatus().name() : "null";
            Anamnesis an = a.getAnamnesis();
            String complaint = an != null ? esc(an.getMainComplaint()) : "null";
            String dietary = an != null ? esc(an.getDietaryRestriction()) : "null";

            // Physical examination + vital parameters
            String consc = "null", examNotes = "null", temp = "null", hr = "null", rr = "null",
                    coag = "null", muc = "null", euv = "null", dehy = "null", vnotes = "null";
            PhysicalExamination exam = a.getPhisicalExam();
            if (exam != null) {
                consc = exam.getLevelOfConsciousness() != null ? exam.getLevelOfConsciousness().name() : "null";
                examNotes = esc(exam.getDescription());
                VitalParameters vp = exam.getVitalParameters();
                if (vp != null) {
                    temp = vp.getCelciusTemperature() != null ? String.valueOf(vp.getCelciusTemperature()) : "null";
                    hr = vp.getHeartRate() != null ? String.valueOf(vp.getHeartRate()) : "null";
                    rr = vp.getRespiratoryRate() != null ? String.valueOf(vp.getRespiratoryRate()) : "null";
                    coag = vp.getCoagulation() != null ? String.valueOf(vp.getCoagulation()) : "null";
                    muc = vp.getMucosa() != null ? vp.getMucosa().name() : "null";
                    vnotes = esc(vp.getDescription());
                    Hydration h = vp.getHydration();
                    if (h != null) {
                        euv = String.valueOf(h.isEuvolemic());
                        dehy = h.getDehydration() != null ? String.valueOf(h.getDehydration()) : "null";
                    }
                }
            }

            lines.add(String.join(";",
                    String.valueOf(a.getId()), String.valueOf(a.getPrice()),
                    String.valueOf(a.getPatient().getId()), a.getDateHourScheduled().format(DT),
                    esc(a.getDescription()), String.valueOf(a.getResponsableVeterinarian().getId()),
                    diag, pres, stts, complaint, dietary,
                    consc, examNotes, temp, hr, rr, coag, muc, euv, dehy, vnotes,
                    String.valueOf(a.isNeedsSurgery()), String.valueOf(a.isNeedsHospitalization())));
        }
        write("appointments.csv",
                "id;price;patientId;dateHour;description;vetId;diagnosis;prescription;status;mainComplaint;dietary;"
                        + "consciousness;examNotes;temperature;heartRate;respRate;coagulation;mucosa;euvolemic;dehydration;vitalNotes;"
                        + "needsSurgery;needsHospitalization",
                lines);
    }

    // ── Products (stock) ──────────────────────────────────────────────────────
    public void saveAllProducts(List<Product> products) {
        List<String> lines = new ArrayList<>();
        for (Product p : products) {
            String med = p.getMedicineType() != null ? p.getMedicineType().name() : "null";
            lines.add(String.join(";",
                    String.valueOf(p.getId()), esc(p.getName()), String.valueOf(p.getQuantity()),
                    String.valueOf(p.getPrice()), esc(p.getDescription()), String.valueOf(p.isVet()), med));
        }
        write("products.csv", "id;name;quantity;price;description;isVet;medicineType", lines);
    }

    // ── Expenses ──────────────────────────────────────────────────────────────
    public void saveAllExpenses(List<Expense> expenses) {
        List<String> lines = new ArrayList<>();
        for (Expense e : expenses) {
            lines.add(String.join(";",
                    String.valueOf(e.getId()), e.getType().name(), String.valueOf(e.getAmount()),
                    e.getDate().format(D), esc(e.getDescription())));
        }
        write("expenses.csv", "id;type;amount;date;description", lines);
    }

    // ── Surgeries ─────────────────────────────────────────────────────────────
    public void saveAllSurgeries(List<Surgery> surgeries) {
        List<String> lines = new ArrayList<>();
        for (Surgery s : surgeries) {
            lines.add(String.join(";",
                    String.valueOf(s.getId()), String.valueOf(s.getPrice()),
                    String.valueOf(s.getPatient().getId()), s.getDateHourScheduled().format(DT),
                    esc(s.getDescription()), String.valueOf(s.getResponsebleVeterinarian().getId()),
                    s.getSurgeryRisk().name(), esc(s.getAnesthesiaType()), esc(s.getSupplies())));
        }
        write("surgeries.csv",
                "id;price;patientId;dateHour;description;vetId;surgeryRisk;anesthesiaType;supplies", lines);
    }

    // ── Invoices (+ embedded pet shop services) ───────────────────────────────
    public void saveAllInvoices(List<Invoice> invoices) {
        List<String> invLines = new ArrayList<>();
        Map<Integer, ServicoPetShop> services = new LinkedHashMap<>();

        for (Invoice inv : invoices) {
            StringBuilder procs = new StringBuilder();
            if (inv.getProcedures() != null) {
                for (Procedure p : inv.getProcedures()) {
                    if (procs.length() > 0) procs.append("|");
                    if (p instanceof ServicoPetShop sps) {
                        services.put(sps.getId(), sps);
                        procs.append("SERVICE~").append(sps.getId());
                    } else if (p instanceof Surgery) {
                        procs.append("SURGERY~").append(p.getId());
                    } else if (p instanceof Appointment) {
                        procs.append("APPT~").append(p.getId());
                    } else {
                        procs.append("OTHER~").append(p.getId());
                    }
                }
            }
            StringBuilder prods = new StringBuilder();
            if (inv.getProducts() != null) {
                for (Product pr : inv.getProducts()) {
                    if (prods.length() > 0) prods.append("|");
                    prods.append(pr.getId());
                }
            }
            invLines.add(String.join(";",
                    String.valueOf(inv.getId()), String.valueOf(inv.getOwner().getId()),
                    String.valueOf(inv.getPatient().getId()), inv.getDateHour().format(DT),
                    procs.toString(), prods.toString(), String.valueOf(inv.isPaid())));
        }

        List<String> svcLines = new ArrayList<>();
        for (ServicoPetShop s : services.values()) {
            svcLines.add(String.join(";",
                    String.valueOf(s.getId()), String.valueOf(s.getPrice()),
                    String.valueOf(s.getPatient().getId()), s.getDateHourScheduled().format(DT),
                    esc(s.getDescription()), s.getServiceType().name(),
                    String.valueOf(s.getResponsableEmployee().getId())));
        }

        write("petshopServices.csv", "id;price;animalId;dateHour;description;serviceType;employeeId", svcLines);
        write("invoices.csv", "id;ownerId;animalId;dateHour;procedureRefs;productRefs;paid", invLines);
    }
}
