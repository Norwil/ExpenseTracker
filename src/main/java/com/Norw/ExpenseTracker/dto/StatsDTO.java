package com.Norw.ExpenseTracker.dto;

import com.Norw.ExpenseTracker.entity.Expense;
import com.Norw.ExpenseTracker.entity.Income;
import lombok.Data;

@Data
public class StatsDTO {
    private Double income;

    private Double expense;

    private Income latestIncome;

    private Expense latestExpense;

    private Double balance;

    private Double minIncome;

    private Double maxIncome;

    private Double minExpense;

    private Double maxExpense;
}
