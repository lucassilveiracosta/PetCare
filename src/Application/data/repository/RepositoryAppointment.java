package data.repository;


import business.model.notaFiscal.Consulta;
import data.interfaces.IRepositoryAppointment;

import java.util.ArrayList;

public class RepositoryAppointment implements IRepositoryAppointment {

    private final ArrayList<Consulta> consultas;
    public RepositoryAppointment(ArrayList<Consulta> consultas) {
        this.consultas = consultas;
    }


    @Override
    public Consulta findById(int id) {
        Consulta consulta = null;
        for (Consulta c : consultas) {
            if (c.getId() == id){
                consulta = c;
            }
        }
        return consulta;
    }

    @Override
    public ArrayList<Consulta> findAll() {
        return consultas;
    }

    @Override
    public void update(int id, Consulta c) {
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getId() == id) {
                consultas.set(i, c);
                return;
            }
        }
    }

    @Override
    public void create(Consulta c) {
        consultas.add(c);
    }

    @Override
    public void remove(Consulta c) {
        if (c != null) {
            consultas.remove(c);
        }
    }
}