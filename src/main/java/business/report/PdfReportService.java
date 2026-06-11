package business.report;

import business.model.animal.Animal;
import business.model.animal.Vaccine;
import business.model.appointment.Anamnesis;
import business.model.appointment.Appointment;
import business.model.appointment.PhysicalExamination;
import business.model.appointment.VitalParameters;
import business.model.invoice.Hospitalization;
import business.model.invoice.Invoice;
import business.model.invoice.Procedure;
import business.model.invoice.Product;
import business.model.invoice.ServicoPetShop;
import business.model.invoice.Surgery;
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

    // ── REQ13 — Clinical history ──────────────────────────────────────────────
    public void generateClinicalHistory(Animal animal, List<Appointment> appts, File dest) throws IOException {
        try (Document doc = openDocument(dest)) {
            title(doc, "PetCare — Clinical History");

            doc.add(new Paragraph("Animal: " + animal.getName()));
            doc.add(new Paragraph("Species / Race: " + animal.getSpecies() + " · " + animal.getRace()));
            doc.add(new Paragraph("Birth date: " + animal.getbirthDate().format(DATE)));
            doc.add(new Paragraph("Weight: " + animal.getWeight() + " kg"));

            sectionTitle(doc, "Consultations");
            if (appts == null || appts.isEmpty()) {
                doc.add(new Paragraph("No completed consultations recorded."));
            } else {
                for (Appointment a : appts) {
                    doc.add(new Paragraph(a.getDateHourScheduled().format(DATE_TIME)
                            + "  —  " + orDash(a.getDiagnosis())).setBold());

                    Anamnesis an = a.getAnamnesis();
                    if (an != null) {
                        doc.add(new Paragraph("Main complaint: " + orDash(an.getMainComplaint())));
                    }
                    PhysicalExamination exam = a.getPhisicalExam();
                    if (exam != null) {
                        String consc = exam.getLevelOfConsciousness() != null
                                ? exam.getLevelOfConsciousness().name() : "-";
                        doc.add(new Paragraph("Consciousness: " + consc + "  |  Notes: " + orDash(exam.getDescription())));
                        VitalParameters vp = exam.getVitalParameters();
                        if (vp != null) {
                            doc.add(new Paragraph("Vitals: "
                                    + numUnit(vp.getCelciusTemperature(), "°C") + ", "
                                    + numUnit(vp.getHeartRate(), "bpm") + ", "
                                    + numUnit(vp.getRespiratoryRate(), "rpm")
                                    + (vp.getMucosa() != null ? ", mucosa " + vp.getMucosa().name() : "")));
                        }
                    }
                    doc.add(new Paragraph("Prescription: " + orDash(a.getMedicalPrescription())));
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

    private String orDash(String value) {
        return (value != null && !value.isBlank()) ? value : "-";
    }
}
