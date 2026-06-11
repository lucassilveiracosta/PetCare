package business.controller;

import business.interfaces.IControllerExpense;
import business.model.invoice.Expense;
import data.SaveData;
import data.interfaces.IRepositoryExpense;
import enums.ExpenseType;

import java.util.ArrayList;
import java.util.List;

public class ControllerExpense implements IControllerExpense {

    private final IRepositoryExpense repositoryExpense;

    public ControllerExpense(IRepositoryExpense repository) {
        this.repositoryExpense = repository;
    }

    @Override
    public Expense getById(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        Expense expense = repositoryExpense.findById(id);
        if (expense == null) throw new IllegalArgumentException("404 - Expense with ID " + id + " not found");
        return expense;
    }

    @Override
    public ArrayList<Expense> getAll() {
        return repositoryExpense.findAll();
    }

    @Override
    public void patch(int id, Expense partialData) {
        if (partialData == null) throw new IllegalArgumentException("400 - Expense can't be null");

        Expense exists = repositoryExpense.findById(id);
        if (exists == null) throw new IllegalArgumentException("404 - Expense with ID " + id + " not found");

        if (partialData.getType() != null) {
            exists.setType(partialData.getType());
        }
        if (partialData.getAmount() != null && partialData.getAmount() > 0) {
            exists.setAmount(partialData.getAmount());
        }
        if (partialData.getDate() != null) {
            exists.setDate(partialData.getDate());
        }
        if (partialData.getDescription() != null && !partialData.getDescription().isBlank()) {
            exists.setDescription(partialData.getDescription());
        }

        repositoryExpense.update(id, exists);
        persist();
    }

    @Override
    public void put(int id, Expense newExpense) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        if (newExpense == null) throw new IllegalArgumentException("400 - Expense can't be null");

        Expense exists = repositoryExpense.findById(id);
        if (exists == null) throw new IllegalArgumentException("404 - Expense with ID " + id + " not found");

        repositoryExpense.update(id, newExpense);
        persist();
    }

    @Override
    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");

        Expense expense = repositoryExpense.findById(id);
        if (expense == null) throw new IllegalArgumentException("404 - Expense with ID " + id + " not found");

        repositoryExpense.remove(expense);
        persist();
    }

    @Override
    public void post(Expense newExpense) {
        if (newExpense == null) throw new IllegalArgumentException("400 - Expense can't be null");

        Expense exists = repositoryExpense.findById(newExpense.getId());
        if (exists != null) throw new IllegalArgumentException("409 - This expense already exists");

        repositoryExpense.create(newExpense);
        persist();
    }

    private void persist() {
        new SaveData().saveAllExpenses(repositoryExpense.findAll());
    }

    @Override
    public List<Expense> filterByType(ExpenseType type) {
        List<Expense> filter = new ArrayList<>();
        for (Expense e : repositoryExpense.findAll()) {
            if (e.getType() == type) {
                filter.add(e);
            }
        }
        return filter;
    }
}
