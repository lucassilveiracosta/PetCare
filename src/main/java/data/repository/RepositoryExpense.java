package data.repository;

import business.model.invoice.Expense;
import data.interfaces.IRepositoryExpense;

import java.util.ArrayList;

public class RepositoryExpense implements IRepositoryExpense {

    private final ArrayList<Expense> expenses;

    public RepositoryExpense(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public Expense findById(int id) {
        Expense expense = null;
        for (Expense e : expenses) {
            if (e.getId() == id) {
                expense = e;
            }
        }
        return expense;
    }

    @Override
    public ArrayList<Expense> findAll() {
        return expenses;
    }

    @Override
    public void update(int id, Expense e) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId() == id) {
                expenses.set(i, e);
                return;
            }
        }
    }

    @Override
    public void create(Expense e) {
        expenses.add(e);
    }

    @Override
    public void remove(Expense e) {
        if (e != null) {
            expenses.remove(e);
        }
    }
}
