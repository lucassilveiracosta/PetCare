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
                String[] data = line.split(",", -1);
                
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

    public ArrayList<Veterinarian> loadVeterinarians() {
        ArrayList<Veterinarian> vets = new ArrayList<>();
        String path = "veterinarianSave.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty()) continue;
                String[] data = line.split(",", -1);
                
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String email = data[2];
                String password = data[3];
                LocalDate birthDate = LocalDate.parse(data[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String cpf = data[5];
                String telephone = data[6];
                String crmv = data[7];

                // Como a especialidade exigiria serialização complexa, iniciamos a lista vazia.
                Veterinarian vet = new Veterinarian(name, email, password, birthDate, cpf, telephone, crmv, new ArrayList<>());
                vet.setId(id);
                vets.add(vet);
            }
        } catch (IOException e) {
            System.out.println("Arquivo de veterinários não encontrado ou vazio. Iniciando lista vazia.");
        }
        return vets;
    }

    public ArrayList<DomesticAnimal> loadDomesticAnimals(List<Owner> loadedOwners) {
        ArrayList<DomesticAnimal> animals = new ArrayList<>();
        String path = "domesticAnimalSave.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty()) continue;
                String[] data = line.split(",", -1);

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

    public ArrayList<Appointment> loadAppointments(List<DomesticAnimal> loadedAnimals, List<Veterinarian> loadedVets) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        String path = "appointmentSave.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty()) continue;
                try {
                String[] data = line.split(",", -1);
                // id, price, patientId, dateHourScheduled, description, veterinarianId, diagnosis, medicalPrescription, status

                int id = Integer.parseInt(data[0]);
                Double price = Double.parseDouble(data[1]);
                int patientId = Integer.parseInt(data[2]);
                LocalDateTime dateHour = LocalDateTime.parse(data[3], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                String description = data[4];
                int vetId = Integer.parseInt(data[5]);
                String diagnosis = data[6].equals("null") || data[6].isEmpty() ? null : data[6];
                String prescription = data[7].equals("null") || data[7].isEmpty() ? null : data[7];
                
                AppointmentStatus status = null;
                if (data.length > 8 && data[8] != null && !data[8].trim().isEmpty() && !data[8].equals("null")) {
                    status = AppointmentStatus.valueOf(data[8]);
                }

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

                    if (diagnosis == null) {

                        Appointment app;
                        if (dateHour.isBefore(LocalDateTime.now())) {
                            app = new Appointment(price, patient, dateHour, description, vet, true);
                        } else {
                            app = new Appointment(price, patient, dateHour, description, vet);
                        }
                        app.setId(id);
                        if (status != null) app.setStatus(status);
                        appointments.add(app);
                    } else {

                        if (dateHour.isBefore(LocalDateTime.now())) {
                            Appointment app = new Appointment(price, patient, dateHour, description, vet, diagnosis, prescription, null, null, status, true);
                            app.setId(id);
                            appointments.add(app);
                        } else {
                            Appointment app = new Appointment(price, patient, dateHour, description, vet, diagnosis, prescription, null, null, status);
                            app.setId(id);
                            appointments.add(app);
                        }
                    }


                }
                } catch (Exception ex) {
                    System.out.println("Linha de consulta invalida ignorada: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo de consultas não encontrado ou vazio. Iniciando lista vazia.");
        }
        return appointments;
    }
}
