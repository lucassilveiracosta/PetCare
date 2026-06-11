package data;

import business.model.animal.DomesticAnimal;
import business.model.appointment.Appointment;
import business.model.person.Owner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SaveData {
    
    public void saveDomesticAnimal(DomesticAnimal pet) {
        Locale.setDefault(Locale.US);
        String path = "domesticAnimalSave.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(pet.getId() + ","); // Salva o ID do animal
            bw.write(pet.getName() + ",");
            bw.write(pet.getSpecies() + ",");
            bw.write(pet.getRace() + ",");
            bw.write(pet.getTemperament().name() + ",");
            bw.write(pet.getWeight() + ",");
            bw.write(pet.getSex().name() + ",");
            bw.write(pet.getSize().name() + ",");
            bw.write(pet.getbirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ",");
            bw.write(pet.getStageOfLife().name() + ",");
            bw.write(pet.getOwner().getId() + ""); // Salva o ID do dono para o relacionamento
            
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveOwner(Owner owner) {
        Locale.setDefault(Locale.US);
        String path = "ownerSave.csv"; // Arquivo correto

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(owner.getId() + ",");
            bw.write(owner.getName() + ",");
            bw.write(owner.getEmail() + ",");
            bw.write(owner.getPassword() + ",");
            bw.write(owner.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ",");
            bw.write(owner.getCpf() + ",");
            bw.write(owner.getTelephone() + ",");
            bw.write(owner.getJob() + ",");
            bw.write(owner.getDescription() + "");
            
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveVeterinarian(business.model.person.Veterinarian vet) {
        Locale.setDefault(Locale.US);
        String path = "veterinarianSave.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(vet.getId() + ",");
            bw.write(vet.getName() + ",");
            bw.write(vet.getEmail() + ",");
            bw.write(vet.getPassword() + ",");
            bw.write(vet.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ",");
            bw.write(vet.getCpf() + ",");
            bw.write(vet.getTelephone() + ",");
            bw.write(vet.getCrmv() + "");
            
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAppointment(Appointment app) {
        Locale.setDefault(Locale.US);
        String path = "appointmentSave.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(app.getId() + ",");
            bw.write(app.getPrice() + ",");
            bw.write(app.getPatient().getId() + ","); // Relacionamento com Animal
            bw.write(app.getDateHourScheduled().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ",");
            bw.write(app.getDescription() + ",");
            bw.write(app.getResponsableVeterinarian().getId() + ","); // Relacionamento com Vet
            
            // Tratamento de campos que podem ser nulos em consultas pendentes
            String diag = app.getDiagnosis() != null ? app.getDiagnosis() : "null";
            String pres = app.getMedicalPrescription() != null ? app.getMedicalPrescription() : "null";
            
            bw.write(diag + ",");
            bw.write(pres + ",");
            bw.write(app.getStatus().name() + "");
            
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
