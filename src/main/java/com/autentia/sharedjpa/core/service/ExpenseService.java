package com.autentia.sharedjpa.core.service;


import com.autentia.sharedjpa.core.domain.Expense;
import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.core.domain.exception.EmptyExpenseListException;
import com.autentia.sharedjpa.core.domain.exception.FriendNotFoundException;
import com.autentia.sharedjpa.core.domain.exception.NegativeExpenseAmountException;
import com.autentia.sharedjpa.core.domain.repositoryDomain.ExpenseRepository;
import com.autentia.sharedjpa.primaryAdapter.request.ExpenseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class ExpenseService {

    private final FriendService friendService;
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, FriendService friendService) {
        this.expenseRepository = expenseRepository;
        this.friendService = friendService;
    }

    public List<Expense> findExpenses() throws EmptyExpenseListException, FriendNotFoundException {
        final List<Expense> expenses = new ArrayList<Expense>();
        final Iterator<Expense> expenseIterator = expenseRepository.findExpenses().iterator();

        while (expenseIterator.hasNext()) {
            expenses.add(expenseIterator.next());
        }

        for(Expense expense : expenses) {
            expense.setFriend(friendService.findFriendById(expense.getFriend().getIdFriend()));
        }

        if(!expenses.isEmpty()){
            return expenses;

        }else{
            throw new EmptyExpenseListException("No expenses found");
        }

    }

    public void addExpense(long idFriend, ExpenseRequest request) throws FriendNotFoundException, NegativeExpenseAmountException {
        Friend friend = getFriend(idFriend);

        if (friend == null) {
            throw new FriendNotFoundException("Friend not found");
        }

        if (request.getAmount() <= 0) {
            throw new NegativeExpenseAmountException("Amount must be positive");
        }

        Expense expense = new Expense();
        expense.setIdExpense(generateExpenseId());
        expense.setFriend(friend);
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(generateDate());

        expenseRepository.addExpense(expense);
    }

    public Friend getFriend(long idFriend) throws FriendNotFoundException {
        Friend friend = friendService.findFriendById(idFriend);
        return friend;
    }

    public long generateExpenseId(){
        return new Date().getTime();
    }
    public String generateDate(){
        long currentDate = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(currentDate);

        return dateFormat.format(date);
    }


    public void deleteExpense(long id) {
        expenseRepository.deleteExpense(id);

    }
}
