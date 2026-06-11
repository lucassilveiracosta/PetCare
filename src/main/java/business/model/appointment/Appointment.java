package business.model.appointment;

import business.model.person.Veterinarian;
import business.model.animal.Animal;
import business.model.invoice.Procedure;
import enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Appointment extends Procedure {
    private Veterinarian responsableVeterinarian;
    private String diagnosis;
    private Anamnesis anamnesis;
    private PhysicalExamination phisicalExam;
    private String medicalPrescription;
    private AppointmentStatus status;
    private boolean needsSurgery = false;
    private boolean needsHospitalization = false;

    // Constructor for completed appointments (has full medical data)
    public Appointment(Double price, Animal patient, LocalDateTime dateHour, String description,
                       Veterinarian responsableVeterinarian, String diagnosis,
                       String medicalPrescription, Anamnesis anamnesis, PhysicalExamination phisicalExam, AppointmentStatus status) {
        super(price, patient, dateHour, description);
        setResponsableVeterinarian(responsableVeterinarian);
        setDiagnosis(diagnosis);
        setMedicalPrescription(medicalPrescription);
        this.anamnesis = anamnesis;
        this.phisicalExam = phisicalExam;
        setStatus(status);
    }

    public Appointment(Double price, Animal patient, LocalDateTime dateHour, String description,
                       Veterinarian responsableVeterinarian, String diagnosis,
                       String medicalPrescription, Anamnesis anamnesis, PhysicalExamination phisicalExam, AppointmentStatus status, Boolean bool) {
        super(bool, price, patient, dateHour, description);
        setResponsableVeterinarian(responsableVeterinarian);
        setDiagnosis(diagnosis);
        setMedicalPrescription(medicalPrescription);
        this.anamnesis = anamnesis;
        this.phisicalExam = phisicalExam;
        setStatus(status);
    }

    // Constructor for scheduled (pending) appointments — no medical data yet
    public Appointment(Double price, Animal patient, LocalDateTime dateHour, String description,
                       Veterinarian responsableVeterinarian) {
        super(price, patient, dateHour, description);
        setResponsableVeterinarian(responsableVeterinarian);
    }

    // Constructor for pending appointments with gambiarra (old appointments)
    public Appointment(Double price, Animal patient, LocalDateTime dateHour, String description,
                       Veterinarian responsableVeterinarian, Boolean bool) {
        super(bool, price, patient, dateHour, description);
        setResponsableVeterinarian(responsableVeterinarian);
    }

    public Appointment(Boolean bool, Double price, Animal patient, LocalDateTime dateHour, String description,
                       Veterinarian responsableVeterinarian) {
        super(bool, price, patient, dateHour, description);
        setResponsableVeterinarian(responsableVeterinarian);
    }

    public AppointmentStatus getEffectiveStatus() {
        if (anamnesis != null) return AppointmentStatus.COMPLETED;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime scheduled = getDateHourScheduled();
        if (scheduled.toLocalDate().equals(LocalDate.now())) return AppointmentStatus.IN_PROGRESS;
        if (scheduled.isAfter(now)) return AppointmentStatus.PENDING;
        return AppointmentStatus.EXPIRED;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        if (status == null) throw new IllegalArgumentException("400 - Invalid status");
        this.status = status;
    }

    public boolean isNeedsSurgery() {
        return needsSurgery;
    }

    public void setNeedsSurgery(boolean needsSurgery) {
        this.needsSurgery = needsSurgery;
    }

    public boolean isNeedsHospitalization() {
        return needsHospitalization;
    }

    public void setNeedsHospitalization(boolean needsHospitalization) {
        this.needsHospitalization = needsHospitalization;
    }

    public Anamnesis getAnamnesis() {
        return anamnesis;
    }

    public void setAnamnesis(Anamnesis anamnesis) {
        this.anamnesis = anamnesis;
    }

    public PhysicalExamination getPhisicalExam() {
        return phisicalExam;
    }

    public void setPhisicalExam(PhysicalExamination phisicalExam) {
        this.phisicalExam = phisicalExam;
    }

    public Veterinarian getResponsableVeterinarian() {
        return responsableVeterinarian;
    }

    public void setResponsableVeterinarian(Veterinarian responsableVeterinarian) {
        if (responsableVeterinarian == null) {
            throw new IllegalArgumentException("400 - Invalid responsable veterinarian");
        }
        this.responsableVeterinarian = responsableVeterinarian;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        if (diagnosis == null || diagnosis.isBlank()) {
            throw new IllegalArgumentException("400 - Invalid diagnosis");
        }
        this.diagnosis = diagnosis;
    }

    public String getMedicalPrescription() {
        return medicalPrescription;
    }

    public void setMedicalPrescription(String medicalPrescription) {
        this.medicalPrescription = medicalPrescription;
    }
}
