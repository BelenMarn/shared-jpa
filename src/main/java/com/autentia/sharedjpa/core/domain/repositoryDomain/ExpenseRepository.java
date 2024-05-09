package com.autentia.sharedjpa.core.domain.repositoryDomain;


import com.autentia.sharedjpa.core.domain.Expense;
import com.autentia.sharedjpa.core.domain.exception.EmptyExpenseListException;
import com.autentia.sharedjpa.core.domain.exception.NegativeExpenseAmountException;
import com.autentia.sharedjpa.secondaryAdapter.entity.ExpenseEntity;

import java.util.List;

public interface ExpenseRepository {
    List<Expense> findExpenses() throws EmptyExpenseListException;
    void addExpense(Expense expense) throws NegativeExpenseAmountException;
    List<ExpenseEntity> findExpensesOfFriend(long friendId);
    double getAverageSpending() throws EmptyExpenseListException;
}
