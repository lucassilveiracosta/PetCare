package data;

import business.model.animal.DomesticAnimal;
import business.model.animal.Vaccine;
import business.model.appointment.Appointment;
import business.model.person.Owner;
import business.model.person.Veterinarian;
import enums.AppointmentStatus;
import enums.Sex;
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

public class LoadData {

    public ArrayList<Owner> loadOwners() {
        ArrayList<Owner> owners = new ArrayList<>();
        String path = "ownerSave.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String email = data[2];
                String password = data[3];
                LocalDate birthDate = LocalDate.parse(data[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String cpf = data[5];
                String telephone = data[6];
                String job = data[7];
                String description = data.length > 8 ? data[8] : "";

                Owner owner = new Owner(name, email, password, birthDate, cpf, telephone, job, description);
                owner.setId(id); // Usa o método adicionado para setar o ID original
                owners.add(owner);
            }
        } catch (IOException e) {
            System.out.println("Arquivo de donos não encontrado ou vazio. Iniciando lista vazia.");
        }
        return owners;
    }

    public ArrayList<DomesticAnimal> loadDomesticAnimals(List<Owner> loadedOwners) {
        ArrayList<DomesticAnimal> animals = new ArrayList<>();
        String path = "domesticAnimalSave.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                // pet.getId(), pet.getName(), pet.getSpecies(), pet.getRace(), pet.getTemperament().name(),
                // pet.getWeight(), pet.getSex().name(), pet.getSize().name(), pet.getbirthDate(),
                // pet.getStageOfLife().name(), pet.getOwner().getId()

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String species = data[2];
                String race = data[3];
                Temperament temperament = Temperament.valueOf(data[4]);
                Double weight = Double.parseDouble(data[5]);
                Sex sex = Sex.valueOf(data[6]);
                Size size = Size.valueOf(data[7]);
                LocalDate birthDate = LocalDate.parse(data[8], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                StageOfLife stageOfLife = StageOfLife.valueOf(data[9]);
                int ownerId = Integer.parseInt(data[10]);

                // Busca o dono pelo ID
                Owner petOwner = null;
                for (Owner o : loadedOwners) {
                    if (o.getId() == ownerId) {
                        petOwner = o;
                        break;
                    }
                }

                if (petOwner != null) {
                    // Inicializamos 'castrated' como falso e vacinas como vazias por enquanto
                    DomesticAnimal animal = new DomesticAnimal(name, species, race, birthDate, stageOfLife, weight, size, sex, petOwner, temperament, false, new ArrayList<Vaccine>());
                    animal.setId(id);
                    animals.add(animal);
                } else {
                    System.out.println("Dono de ID " + ownerId + " não encontrado para o pet " + name);
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo de animais não encontrado ou vazio. Iniciando lista vazia.");
        }
        return animals;
    }

    public List<Appointment> loadAppointments(List<DomesticAnimal> loadedAnimals, List<Veterinarian> loadedVets) {
        List<Appointment> appointments = new ArrayList<>();
        String path = "appointmentSave.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                // id, price, patientId, dateHourScheduled, description, veterinarianId, diagnosis, medicalPrescription, status

                int id = Integer.parseInt(data[0]);
                Double price = Double.parseDouble(data[1]);
                int patientId = Integer.parseInt(data[2]);
                LocalDateTime dateHour = LocalDateTime.parse(data[3], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                String description = data[4];
                int vetId = Integer.parseInt(data[5]);
                String diagnosis = data[6].equals("null") ? null : data[6];
                String prescription = data[7].equals("null") ? null : data[7];
                AppointmentStatus status = AppointmentStatus.valueOf(data[8]);

                // Busca o animal pelo ID
                DomesticAnimal patient = null;
                for (DomesticAnimal a : loadedAnimals) {
                    if (a.getId() == patientId) {
                        patient = a;
                        break;
                    }
                }

                // Busca o veterinario pelo ID
                Veterinarian vet = null;
                for (Veterinarian v : loadedVets) {
                    if (v.getId() == vetId) {
                        vet = v;
                        break;
                    }
                }

                if (patient != null && vet != null) {
                    Appointment app = new Appointment(price, patient, dateHour, description, vet, diagnosis, prescription, null, null, status);
                    app.setId(id);
                    appointments.add(app);
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo de consultas não encontrado ou vazio. Iniciando lista vazia.");
        }
        return appointments;
    }
}
