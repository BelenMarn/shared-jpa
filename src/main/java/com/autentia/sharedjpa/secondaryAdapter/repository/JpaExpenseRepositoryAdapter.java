package com.autentia.sharedjpa.secondaryAdapter.repository;


import com.autentia.sharedjpa.core.domain.Expense;
import com.autentia.sharedjpa.core.domain.repositoryDomain.ExpenseRepository;
import com.autentia.sharedjpa.core.service.Mapper.ExpenseMapper;
import com.autentia.sharedjpa.secondaryAdapter.entity.ExpenseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
@Primary
public class JpaExpenseRepositoryAdapter implements ExpenseRepository {

    private final JpaExpenseRepository jpaExpenseRepository;

    @Autowired
    public JpaExpenseRepositoryAdapter(JpaExpenseRepository jpaExpenseRepository) {
        this.jpaExpenseRepository = jpaExpenseRepository;
    }


    @Override
    public List<Expense> findExpenses() {

        List<Expense> expenses = jpaExpenseRepository.findAll().stream()
                .map(ExpenseMapper.INSTANCE::toDomainFromEntity)
                .collect(Collectors.toList());

        for (Expense expense : expenses) {
            expense.getFriend().setName(jpaExpenseRepository.findExpenseFriendNameById(expense.getIdExpense()));
        }

        return expenses;
    }

    @Override
    public void addExpense(Expense expense){
        ExpenseEntity expenseEntity = ExpenseMapper.INSTANCE.toEntityFromDomain(expense);
        jpaExpenseRepository.save(expenseEntity);
    }

    @Override
    public List<ExpenseEntity> findExpensesOfFriend(long friendId) {
        List<ExpenseEntity> allExpenses = jpaExpenseRepository.findAll();

        List<ExpenseEntity> expensesOfFriend = new ArrayList<>();

        for (ExpenseEntity expense : allExpenses) {
            if (expense.getIdFriend() == friendId) {
                expensesOfFriend.add(expense);
            }
        }
        return expensesOfFriend;
    }

    @Override
    public double getAverageSpending() {
        List<ExpenseEntity> expenses = jpaExpenseRepository.findAll();

        double totalAmount = expenses.stream().mapToDouble(ExpenseEntity::getAmount).sum();
        double average = totalAmount / expenses.size();

        double roundedAverage = Math.round(average * 100.0) / 100.0;
        return roundedAverage;
    }

    @Override
    public void deleteExpense(long id) {
        jpaExpenseRepository.deleteById(id);
    }
}
