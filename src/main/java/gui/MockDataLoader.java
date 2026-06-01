package gui;

import business.controller.ControllerPetCareServer;
import business.interfaces.IControllerAnimal;
import business.interfaces.IControllerAppointment;
import business.interfaces.IControllerPerson;
import business.model.animal.DomesticAnimal;
import business.model.animal.ExoticAnimal;
import business.model.animal.Vaccine;
import business.model.appointment.*;
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
        if (loaded) return;
        loaded = true;
        ControllerPetCareServer server = ControllerPetCareServer.getInstance();
        IControllerAnimal animalCtrl = server.getAnimal();
        IControllerAppointment appointmentCtrl = server.getAppointment();

        // ── Veterinarians ────────────────────────────────────────────────────────
        ArrayList<Specialty> carterSpecialties = new ArrayList<>();
        carterSpecialties.add(new Specialty("Feline Medicine", "Specializes in cat diseases and surgery"));

        Veterinarian drCarter = new Veterinarian(
                "Dr. Sarah Carter", "sarah.carter@petcare.com", "password123",
                LocalDate.of(1985, 4, 12), "111.222.333-44", "(11) 91234-5678",
                "CRMV-SP 12345", carterSpecialties);

        ArrayList<Specialty> williamsSpecialties = new ArrayList<>();
        williamsSpecialties.add(new Specialty("General Clinic", "General veterinary practice"));
        williamsSpecialties.add(new Specialty("Dermatology", "Skin and coat conditions"));

        Veterinarian drWilliams = new Veterinarian(
                "Dr. John Williams", "john.williams@petcare.com", "password123",
                LocalDate.of(1979, 8, 25), "222.333.444-55", "(11) 99876-5432",
                "CRMV-SP 67890", williamsSpecialties);


        server.getPessoa().post(drWilliams);
        server.getPessoa().post(drCarter);

        // ── Owners ───────────────────────────────────────────────────────────────
        Owner michael = new Owner(
                "Michael Turner", "michael.turner@email.com", "password123",
                LocalDate.of(1990, 3, 5), "333.444.555-66", "(11) 97654-3210",
                "Teacher", "Regular customer, prefers afternoon appointments");

        Owner emma = new Owner(
                "Emma Davis", "emma.davis@email.com", "password123",
                LocalDate.of(1995, 7, 18), "444.555.666-77", "(11) 96543-2109",
                "Nurse", "Prefers morning appointments");

        // ── Animals ──────────────────────────────────────────────────────────────

        // Rex — German Shepherd, adult male, valid rabies vaccine
        ArrayList<Vaccine> rexVaccines = new ArrayList<>();
        rexVaccines.add(new Vaccine("V8", LocalDate.of(2025, 1, 10),
                "Polyvalent annual vaccine", false, LocalDate.of(2026, 1, 10)));
        rexVaccines.add(new Vaccine("Rabies", LocalDate.of(2025, 1, 10),
                "Antirabies vaccine", true, LocalDate.of(2026, 1, 10)));

        DomesticAnimal rex = new DomesticAnimal(
                "Rex", "Dog", "German Shepherd", LocalDate.of(2020, 6, 15),
                StageOfLife.ADULTO, 28.5, Size.GRANDE, Sex.MACHO,
                michael, Temperament.REATIVO, false, rexVaccines);

        // Whiskers — Persian Cat, adult female, no vaccines
        ArrayList<Vaccine> whiskersVaccines = new ArrayList<>();

        DomesticAnimal whiskers = new DomesticAnimal(
                "Whiskers", "Cat", "Persian", LocalDate.of(2021, 11, 3),
                StageOfLife.ADULTO, 4.2, Size.PEQUENO, Sex.FEMEA,
                michael, Temperament.DOCIL, true, whiskersVaccines);

        // Buddy — Golden Retriever, adult male, valid rabies vaccine
        ArrayList<Vaccine> buddyVaccines = new ArrayList<>();
        buddyVaccines.add(new Vaccine("Rabies", LocalDate.of(2025, 5, 20),
                "Antirabies vaccine", true, LocalDate.of(2026, 5, 20)));

        DomesticAnimal buddy = new DomesticAnimal(
                "Buddy", "Dog", "Golden Retriever", LocalDate.of(2019, 2, 28),
                StageOfLife.ADULTO, 32.0, Size.GRANDE, Sex.MACHO,
                emma, Temperament.DOCIL, true, buddyVaccines);

        // Kiwi — Amazon Parrot, exotic, no vaccines
        ArrayList<Vaccine> kiwiVaccines = new ArrayList<>();

        ExoticAnimal kiwi = new ExoticAnimal(
                "Kiwi", "Bird", "Amazon Parrot", LocalDate.of(2022, 4, 7),
                StageOfLife.ADULTO, 0.5, Size.MINI, Sex.MACHO,
                "REG-2022-001", "CHIP-9876-KW",
                true, "Seeds, fruits and leafy vegetables",
                Origin.LEGALIZADO, kiwiVaccines);

        // Luna — Tabby Cat, adult female, expired rabies vaccine
        ArrayList<Vaccine> lunaVaccines = new ArrayList<>();
        lunaVaccines.add(new Vaccine("Rabies", LocalDate.of(2023, 3, 1),
                "Antirabies vaccine — expired", true, LocalDate.of(2024, 3, 1)));

        DomesticAnimal luna = new DomesticAnimal(
                "Luna", "Cat", "Tabby", LocalDate.of(2022, 8, 14),
                StageOfLife.ADULTO, 3.8, Size.PEQUENO, Sex.FEMEA,
                emma, Temperament.ANSIOSO, false, lunaVaccines);

        animalCtrl.post(rex);
        animalCtrl.post(whiskers);
        animalCtrl.post(buddy);
        animalCtrl.post(kiwi);
        animalCtrl.post(luna);

        // ── Past Appointments ─────────────────────────────────────────────────────

        // Rex — gastrointestinal infection, Mar 2025
        Anamnesis rexAnamnesis = new Anamnesis(
                "Vomiting and lethargy for 2 days",
                "No dietary restrictions",
                "Owner reported the dog stopped eating since yesterday");
        VitalParameters rexVitals = new VitalParameters(
                95, 24, 39.2, Mucosa.NORMACORADAS, 2,
                new Hydration(true, null), "Within normal range");
        PhysicalExamination rexExam = new PhysicalExamination(
                Conscience.APATICO, rexVitals, "Slightly depressed, mild abdominal tenderness");
        Appointment rexAppt = new Appointment(
                150.0, rex, LocalDateTime.of(2025, 3, 15, 10, 0),
                "Gastrointestinal consultation", drWilliams,
                "Gastrointestinal infection",
                "Metronidazole 250 mg — 1 tablet twice a day for 7 days. Light diet.",
                rexAnamnesis, rexExam, AppointmentStatus.COMPLETED);

        // Rex — skin rash follow-up, Jun 2025
        Anamnesis rexAnamnesis2 = new Anamnesis(
                "Skin rash on the belly",
                "No restrictions",
                "Owner noticed red patches 5 days ago, possibly allergic");
        VitalParameters rexVitals2 = new VitalParameters(
                92, 22, 38.7, Mucosa.NORMACORADAS, 2,
                new Hydration(true, null), "Normal");
        PhysicalExamination rexExam2 = new PhysicalExamination(
                Conscience.ALERTA, rexVitals2, "Alert, cooperative. Erythematous lesions on ventral abdomen");
        Appointment rexAppt2 = new Appointment(
                180.0, rex, LocalDateTime.of(2025, 6, 5, 11, 30),
                "Dermatology follow-up", drWilliams,
                "Allergic dermatitis",
                "Hydrocortisone cream — apply twice daily for 10 days. Avoid grass contact.",
                rexAnamnesis2, rexExam2, AppointmentStatus.COMPLETED);

        // Whiskers — anemia investigation, Jan 2025
        Anamnesis whiskersAnamnesis = new Anamnesis(
                "Pale gums and very low energy",
                "No dietary restrictions",
                "Cat has been unusually lethargic for over a week");
        VitalParameters whiskersVitals = new VitalParameters(
                140, 30, 38.5, Mucosa.PALIDAS, 2,
                new Hydration(false, 5.0), "Low TPC, mild dehydration");
        PhysicalExamination whiskersExam = new PhysicalExamination(
                Conscience.APATICO, whiskersVitals,
                "Pale mucosa, mild dehydration, slightly elevated HR");
        Appointment whiskersAppt = new Appointment(
                200.0, whiskers, LocalDateTime.of(2025, 1, 20, 14, 30),
                "Anemia investigation", drCarter,
                "Mild normocytic anemia",
                "Iron supplement 1 ml — once daily for 30 days. Return for blood work in 4 weeks.",
                whiskersAnamnesis, whiskersExam, AppointmentStatus.COMPLETED);

        // Buddy — annual checkup, Nov 2024
        Anamnesis buddyAnamnesis = new Anamnesis(
                "Routine annual visit",
                "No restrictions",
                "Owner reports no concerns. Dog is active and eating well.");
        VitalParameters buddyVitals = new VitalParameters(
                88, 20, 38.8, Mucosa.NORMACORADAS, 2,
                new Hydration(true, null), "All parameters within normal range");
        PhysicalExamination buddyExam = new PhysicalExamination(
                Conscience.ALERTA, buddyVitals,
                "Healthy and well-conditioned. Clean teeth, good coat.");
        Appointment buddyAppt = new Appointment(
                120.0, buddy, LocalDateTime.of(2024, 11, 10, 9, 0),
                "Annual checkup", drWilliams,
                "Healthy — no clinical concerns",
                "No medication required. Annual V10 vaccination administered.",
                buddyAnamnesis, buddyExam, AppointmentStatus.COMPLETED);

        // Luna — respiratory infection, Apr 2025
        Anamnesis lunaAnamnesis = new Anamnesis(
                "Sneezing and nasal discharge",
                "No restrictions",
                "Symptoms started 3 days ago after contact with a new cat at home");
        VitalParameters lunaVitals = new VitalParameters(
                160, 36, 39.5, Mucosa.NORMACORADAS, 2,
                new Hydration(true, null), "Slightly elevated temperature");
        PhysicalExamination lunaExam = new PhysicalExamination(
                Conscience.ALERTA, lunaVitals,
                "Alert but irritable. Bilateral nasal discharge. Mild fever.");
        Appointment lunaAppt = new Appointment(
                130.0, luna, LocalDateTime.of(2025, 4, 8, 16, 0),
                "Respiratory infection", drCarter,
                "Upper respiratory tract infection",
                "Doxycycline 50 mg — 1 tablet once daily for 10 days. Isolate from other pets.",
                lunaAnamnesis, lunaExam, AppointmentStatus.COMPLETED);

        appointmentCtrl.post(rexAppt);
        appointmentCtrl.post(rexAppt2);
        appointmentCtrl.post(whiskersAppt);
        appointmentCtrl.post(buddyAppt);
        appointmentCtrl.post(lunaAppt);

        // ── Pending appointments (future dates, no medical data yet) ──────────────
        Appointment buddyPending = new Appointment(
                130.0, buddy, LocalDateTime.now().plusDays(15).withHour(10).withMinute(0),
                "Annual vaccination", drWilliams);
        Appointment lunaPending = new Appointment(
                150.0, luna, LocalDateTime.now().plusDays(30).withHour(14).withMinute(30),
                "Dermatology follow-up", drCarter);

        // ── In-progress appointment (today, no medical data yet) ──────────────────
        Appointment rexToday = new Appointment(
                160.0, rex, LocalDateTime.now().withHour(9).withMinute(0),
                "Post-surgery check", drWilliams);

        // ── Expired appointment (past date, never attended) ───────────────────────
        Appointment whiskersExpired = new Appointment(
                120.0, whiskers, LocalDateTime.now().minusDays(20).withHour(11).withMinute(0),
                "Routine checkup — missed", drCarter);

        appointmentCtrl.post(buddyPending);
        appointmentCtrl.post(lunaPending);
        appointmentCtrl.post(rexToday);
        appointmentCtrl.post(whiskersExpired);
    }
}
