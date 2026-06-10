package data;

import business.model.animal.Animal;
import business.model.animal.DomesticAnimal;
import business.model.appointment.Appointment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SaveData {
    public void saveDomesticAnimal(DomesticAnimal pet) {
        Locale.setDefault(Locale.US);
        // Caminho relativo (salvará na pasta raiz do projeto PetCare)
        String path = "domesticAnimalSave.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
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

    public void saveAppointment(Appointment app) {
        Locale.setDefault(Locale.US);
        String path = "appointmentSave.csv";

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
