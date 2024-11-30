package com.Norw.ExpenseTracker.dto;


import com.Norw.ExpenseTracker.entity.Expense;
import com.Norw.ExpenseTracker.entity.Income;
import lombok.Data;

import java.util.List;

@Data
public class GraphDTO {

    private List<Expense> expensesList;

    private List<Income> incomeList;



}
