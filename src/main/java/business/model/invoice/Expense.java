package business.model.invoice;

import enums.ExpenseType;

import java.time.LocalDate;

public class Expense {

    private static int count_id = 1;
    private int id = count_id++;
    private ExpenseType type;
    private Double amount;
    private LocalDate date;
    private String description;

    public Expense(ExpenseType type, Double amount, LocalDate date, String description) {
        setType(type);
        setAmount(amount);
        setDate(date);
        setDescription(description);
    }

    public int getId() {
        return id;
    }

    public ExpenseType getType() {
        return type;
    }

    public void setType(ExpenseType type) {
        if (type == null) throw new IllegalArgumentException("400 - Invalid expense type");
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("400 - The amount must be positive");
        }
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("400 - Invalid date");
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("400 - The expense description can't be blank");
        }
        this.description = description;
    }
}
