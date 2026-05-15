package data.repository;


import business.model.notaFiscal.Appointment;
import data.interfaces.IRepositoryAppointment;

import java.util.ArrayList;

public class RepositoryAppointment implements IRepositoryAppointment {

    private final ArrayList<Appointment> consultas;
    public RepositoryAppointment(ArrayList<Appointment> consultas) {
        this.consultas = consultas;
    }


    @Override
    public Appointment findById(int id) {
        Appointment consulta = null;
        for (Appointment c : consultas) {
            if (c.getId() == id){
                consulta = c;
            }
        }
        return consulta;
    }

    @Override
    public ArrayList<Appointment> findAll() {
        return consultas;
    }

    @Override
    public void update(int id, Appointment c) {
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getId() == id) {
                consultas.set(i, c);
                return;
            }
        }
    }

    @Override
    public void create(Appointment c) {
        consultas.add(c);
    }

    @Override
    public void remove(Appointment c) {
        if (c != null) {
            consultas.remove(c);
        }
    }
}