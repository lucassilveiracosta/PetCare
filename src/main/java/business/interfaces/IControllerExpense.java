package business.interfaces;

import business.model.invoice.Expense;
import enums.ExpenseType;

import java.util.List;

public interface IControllerExpense {
    Expense getById(int id);
    List<Expense> getAll();
    void patch(int id, Expense partialData);
    void put(int id, Expense newExpense);
    void delete(int id);
    void post(Expense newExpense);

    List<Expense> filterByType(ExpenseType type);
}
