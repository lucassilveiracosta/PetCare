package business.report;

import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.animal.Vaccine;
import business.model.appointment.Anamnesis;
import business.model.appointment.Appointment;
import business.model.appointment.Hydration;
import business.model.appointment.PhysicalExamination;
import business.model.appointment.VitalParameters;
import business.model.invoice.Hospitalization;
import business.model.invoice.Invoice;
import business.model.invoice.Procedure;
import business.model.invoice.Product;
import business.model.invoice.ServicoPetShop;
import business.model.invoice.Surgery;
import business.model.person.Veterinarian;
import enums.AppointmentStatus;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Generates PDF documents with iText: detailed invoices (REQ11) and complete
 * clinical histories (REQ13).
 */
public class PdfReportService {

    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // ── REQ11 — Invoice ───────────────────────────────────────────────────────
    public void generateInvoice(Invoice inv, File dest) throws IOException {
        try (Document doc = openDocument(dest)) {
            title(doc, "PetCare — Invoice #" + inv.getId());

            doc.add(new Paragraph("Owner: " + inv.getOwner().getName()));
            doc.add(new Paragraph("Patient: " + inv.getPatient().getName()));
            doc.add(new Paragraph("Date: " + inv.getDateHour().format(DATE_TIME)));

            double total = 0.0;

            // Procedures
            sectionTitle(doc, "Procedures / Services");
            if (inv.getProcedures() != null && !inv.getProcedures().isEmpty()) {
                Table table = new Table(UnitValue.createPercentArray(new float[]{3, 2, 1})).useAllAvailableWidth();
                header(table, "Description", "Type", "Price");
                for (Procedure p : inv.getProcedures()) {
                    double price = p.getPrice() != null ? p.getPrice() : 0.0;
                    total += price;
                    cell(table, p.getDescription());
                    cell(table, procedureType(p));
                    cell(table, money(price));
                }
                doc.add(table);
            } else {
                doc.add(new Paragraph("No procedures."));
            }

            // Products
            sectionTitle(doc, "Products");
            if (inv.getProducts() != null && !inv.getProducts().isEmpty()) {
                Table table = new Table(UnitValue.createPercentArray(new float[]{3, 1, 1})).useAllAvailableWidth();
                header(table, "Name", "Qty", "Price");
                for (Product prod : inv.getProducts()) {
                    double price = prod.getPrice() != null ? prod.getPrice() : 0.0;
                    total += price;
                    cell(table, prod.getName());
                    cell(table, String.valueOf(prod.getQuantity()));
                    cell(table, money(price));
                }
                doc.add(table);
            } else {
                doc.add(new Paragraph("No products."));
            }

            doc.add(new Paragraph("TOTAL: " + money(total))
                    .setBold().setFontSize(14));
        }
    }

    // ── Pet shop stock / receipt ──────────────────────────────────────────────
    public void generateStockReport(List<Product> products, File dest) throws IOException {
        try (Document doc = openDocument(dest)) {
            title(doc, "PetCare — Pet Shop Products");

            double total = 0.0;
            Table table = new Table(UnitValue.createPercentArray(new float[]{3, 1, 1, 1, 2})).useAllAvailableWidth();
            header(table, "Name", "Qty", "Price", "Subtotal", "Type");
            if (products != null) {
                for (Product p : products) {
                    double price = p.getPrice() != null ? p.getPrice() : 0.0;
                    double subtotal = price * p.getQuantity();
                    total += subtotal;
                    String type = p.isVet()
                            ? (p.getMedicineType() != null ? p.getMedicineType().name() : "VET")
                            : "PET SHOP";
                    cell(table, p.getName());
                    cell(table, String.valueOf(p.getQuantity()));
                    cell(table, money(price));
                    cell(table, money(subtotal));
                    cell(table, type);
                }
            }
            doc.add(table);
            doc.add(new Paragraph("TOTAL: " + money(total)).setBold().setFontSize(14));
        }
    }

    public void generateStockFinanceReport(List<Product> products, double spentThisMonth,
                                           double stockValue, File dest) throws IOException {
        try (Document doc = openDocument(dest)) {
            title(doc, "PetCare — Stock Report");

            doc.add(new Paragraph("Spent on stock this month: " + money(spentThisMonth)).setBold());
            doc.add(new Paragraph("Value still in stock: " + money(stockValue)).setBold());
            doc.add(new Paragraph(" "));

            sectionTitle(doc, "Products in stock");
            Table table = new Table(UnitValue.createPercentArray(new float[]{3, 1, 1, 1})).useAllAvailableWidth();
            header(table, "Name", "Qty", "Unit", "Total Value");
            if (products != null) {
                for (Product p : products) {
                    double price = p.getPrice() != null ? p.getPrice() : 0.0;
                    cell(table, p.getName());
                    cell(table, String.valueOf(p.getQuantity()));
                    cell(table, money(price));
                    cell(table, money(price * p.getQuantity()));
                }
            }
            doc.add(table);
        }
    }

    // ── REQ12 — Veterinarian productivity ─────────────────────────────────────
    public void generateVetProductivityReport(List<Veterinarian> vets, List<Appointment> appointments,
                                              File dest) throws IOException {
        try (Document doc = openDocument(dest)) {
            title(doc, "PetCare — Veterinarian Productivity Report");

            sectionTitle(doc, "Summary");
            Table summary = new Table(UnitValue.createPercentArray(new float[]{3, 2, 1, 1, 2})).useAllAvailableWidth();
            header(summary, "Veterinarian", "CRMV", "Consultations", "Completed", "Revenue");
            for (Veterinarian v : vets) {
                int total = 0, completed = 0;
                double revenue = 0.0;
                for (Appointment a : appointments) {
                    if (a.getResponsableVeterinarian() == null
                            || a.getResponsableVeterinarian().getId() != v.getId()) continue;
                    total++;
                    if (a.getEffectiveStatus() == AppointmentStatus.COMPLETED) completed++;
                    revenue += a.getPrice() != null ? a.getPrice() : 0.0;
                }
                cell(summary, v.getName());
                cell(summary, v.getCrmv());
                cell(summary, String.valueOf(total));
                cell(summary, String.valueOf(completed));
                cell(summary, money(revenue));
            }
            doc.add(summary);

            for (Veterinarian v : vets) {
                sectionTitle(doc, v.getName() + " — Consultations (PDV)");
                Table t = new Table(UnitValue.createPercentArray(new float[]{2, 2, 3, 1, 1})).useAllAvailableWidth();
                header(t, "Date", "Animal", "Diagnosis", "Status", "Price");
                boolean any = false;
                for (Appointment a : appointments) {
                    if (a.getResponsableVeterinarian() == null
                            || a.getResponsableVeterinarian().getId() != v.getId()) continue;
                    any = true;
                    cell(t, a.getDateHourScheduled().format(DATE_TIME));
                    cell(t, a.getPatient().getName());
                    cell(t, orDash(a.getDiagnosis()));
                    cell(t, a.getEffectiveStatus().name());
                    cell(t, a.getPrice() != null ? money(a.getPrice()) : "-");
                }
                if (any) doc.add(t);
                else doc.add(new Paragraph("No consultations."));
            }
        }
    }

    public void generateSaleReceipt(Map<Product, Integer> cart, double total, File dest) throws IOException {
        try (Document doc = openDocument(dest)) {
            title(doc, "PetCare — Pet Shop Receipt");

            Table table = new Table(UnitValue.createPercentArray(new float[]{3, 1, 1, 1})).useAllAvailableWidth();
            header(table, "Product", "Qty", "Unit", "Subtotal");
            if (cart != null) {
                for (Map.Entry<Product, Integer> e : cart.entrySet()) {
                    Product p = e.getKey();
                    int qty = e.getValue();
                    double price = p.getPrice() != null ? p.getPrice() : 0.0;
                    cell(table, p.getName());
                    cell(table, String.valueOf(qty));
                    cell(table, money(price));
                    cell(table, money(price * qty));
                }
            }
            doc.add(table);
            doc.add(new Paragraph("TOTAL: " + money(total)).setBold().setFontSize(14));
        }
    }

    // ── REQ13 — Clinical history ──────────────────────────────────────────────
    public void generateClinicalHistory(Animal animal, List<Appointment> appts, File dest) throws IOException {
        generateClinicalHistory(animal, appts, null, dest);
    }

    public void generateClinicalHistory(Animal animal, List<Appointment> appts, String period, File dest) throws IOException {
        try (Document doc = openDocument(dest)) {
            title(doc, "PetCare — Clinical History");
            if (period != null && !period.isBlank()) {
                doc.add(new Paragraph("Period: " + period).setBold());
            }

            // ── Animal data ──────────────────────────────────────────────────
            sectionTitle(doc, "Animal");
            doc.add(new Paragraph("Name: " + animal.getName()));
            doc.add(new Paragraph("Species / Race: " + animal.getSpecies() + " · " + animal.getRace()));
            doc.add(new Paragraph("Sex / Size: " + animal.getSex().name() + " · " + animal.getSize().name()));
            doc.add(new Paragraph("Stage of life: " + animal.getStageOfLife().name()));
            doc.add(new Paragraph("Birth date: " + animal.getbirthDate().format(DATE)));
            doc.add(new Paragraph("Weight: " + animal.getWeight() + " kg"));
            if (animal instanceof DomesticAnimal da) {
                doc.add(new Paragraph("Temperament: " + da.getTemperament().name()));
                doc.add(new Paragraph("Owner: " + da.getOwner().getName()));
            }

            // ── Consultations (all fields) ───────────────────────────────────
            sectionTitle(doc, "Consultations");
            if (appts == null || appts.isEmpty()) {
                doc.add(new Paragraph("No completed consultations recorded."));
            } else {
                for (Appointment a : appts) {
                    doc.add(new Paragraph(a.getDateHourScheduled().format(DATE_TIME)).setBold().setFontSize(12));
                    doc.add(new Paragraph("Veterinarian: "
                            + (a.getResponsableVeterinarian() != null ? a.getResponsableVeterinarian().getName() : "-")));
                    doc.add(new Paragraph("Description: " + orDash(a.getDescription())));
                    doc.add(new Paragraph("Price: " + (a.getPrice() != null ? money(a.getPrice()) : "-")));
                    doc.add(new Paragraph("Diagnosis: " + orDash(a.getDiagnosis())));
                    doc.add(new Paragraph("Prescription: " + orDash(a.getMedicalPrescription())));

                    Anamnesis an = a.getAnamnesis();
                    if (an != null) {
                        doc.add(new Paragraph("Main complaint: " + orDash(an.getMainComplaint())));
                        doc.add(new Paragraph("Dietary restriction: " + orDash(an.getDietaryRestriction())));
                        if (an.getDescription() != null && !an.getDescription().isBlank()) {
                            doc.add(new Paragraph("Anamnesis notes: " + an.getDescription()));
                        }
                    }

                    PhysicalExamination exam = a.getPhisicalExam();
                    if (exam != null) {
                        doc.add(new Paragraph("Consciousness: "
                                + (exam.getLevelOfConsciousness() != null ? exam.getLevelOfConsciousness().name() : "-")));
                        doc.add(new Paragraph("Exam notes: " + orDash(exam.getDescription())));
                        VitalParameters vp = exam.getVitalParameters();
                        if (vp != null) {
                            doc.add(new Paragraph("Temperature: " + numUnit(vp.getCelciusTemperature(), "°C")
                                    + "   |   Heart rate: " + numUnit(vp.getHeartRate(), "bpm")
                                    + "   |   Resp. rate: " + numUnit(vp.getRespiratoryRate(), "rpm")));
                            doc.add(new Paragraph("Coagulation: " + numUnit(vp.getCoagulation(), "s")
                                    + "   |   Mucosa: " + (vp.getMucosa() != null ? vp.getMucosa().name() : "-")
                                    + "   |   Hydration: " + hydration(vp.getHydration())));
                            doc.add(new Paragraph("Vital notes: " + orDash(vp.getDescription())));
                        }
                    }
                    doc.add(new Paragraph(" "));
                }
            }

            sectionTitle(doc, "Vaccines");
            if (animal.getVaccines() == null || animal.getVaccines().isEmpty()) {
                doc.add(new Paragraph("No vaccines recorded."));
            } else {
                Table table = new Table(UnitValue.createPercentArray(new float[]{2, 1, 1, 1})).useAllAvailableWidth();
                header(table, "Vaccine", "Applied", "Expires", "Rabies");
                for (Vaccine v : animal.getVaccines()) {
                    cell(table, v.getVaccineName());
                    cell(table, v.getVaccineDate().format(DATE));
                    cell(table, v.getExpireVaccineDate().format(DATE));
                    cell(table, v.isRabbiesVaccine() ? "Yes" : "No");
                }
                doc.add(table);
            }
        }
    }

    // ── helpers ───────────────────────────────────────────────────────────────
    private Document openDocument(File dest) throws IOException {
        PdfWriter writer = new PdfWriter(dest.getAbsolutePath());
        PdfDocument pdf = new PdfDocument(writer);
        return new Document(pdf);
    }

    private void title(Document doc, String text) {
        doc.add(new Paragraph(text).setBold().setFontSize(18));
    }

    private void sectionTitle(Document doc, String text) {
        doc.add(new Paragraph(text).setBold().setFontSize(13));
    }

    private void header(Table table, String... headers) {
        for (String h : headers) {
            table.addHeaderCell(new Cell().add(new Paragraph(h).setBold()));
        }
    }

    private void cell(Table table, String text) {
        table.addCell(new Cell().add(new Paragraph(text != null ? text : "-")));
    }

    private String procedureType(Procedure p) {
        if (p instanceof Appointment) return "Consultation";
        if (p instanceof Surgery) return "Surgery";
        if (p instanceof ServicoPetShop) return "PetShop Service";
        if (p instanceof Hospitalization) return "Hospitalization";
        return "Procedure";
    }

    private String money(double v) {
        return String.format("R$ %.2f", v);
    }

    private String numUnit(Number value, String unit) {
        return value != null ? value + " " + unit : "-";
    }

    private String hydration(Hydration h) {
        if (h == null) return "-";
        if (h.isEuvolemic()) return "Euvolemic";
        return h.getDehydration() != null ? "Dehydrated (" + h.getDehydration() + "%)" : "Dehydrated";
    }

    private String orDash(String value) {
        return (value != null && !value.isBlank()) ? value : "-";
    }
}
