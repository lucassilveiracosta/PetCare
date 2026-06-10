package data.interfaces;

import business.model.invoice.Expense;

import java.util.ArrayList;

public interface IRepositoryExpense {

    Expense findById(int id);
    ArrayList<Expense> findAll();
    void update(int index, Expense e);
    void create(Expense e);
    void remove(Expense e);
}
