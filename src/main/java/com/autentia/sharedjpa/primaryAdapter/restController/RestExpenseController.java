package com.autentia.sharedjpa.primaryAdapter.restController;

import com.autentia.sharedjpa.core.domain.Expense;
import com.autentia.sharedjpa.core.domain.exception.EmptyExpenseListException;
import com.autentia.sharedjpa.core.domain.exception.EmptyFriendListException;
import com.autentia.sharedjpa.core.domain.exception.FriendNotFoundException;
import com.autentia.sharedjpa.core.domain.exception.NegativeExpenseAmountException;
import com.autentia.sharedjpa.core.service.ExpenseService;
import com.autentia.sharedjpa.primaryAdapter.request.ExpenseRequest;
import com.autentia.sharedjpa.primaryAdapter.respond.ExpenseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/expense")
public class RestExpenseController {
    private ExpenseService expenseService;

    @Autowired
    public RestExpenseController(ExpenseService expenseService) {this.expenseService = expenseService;}

    @GetMapping
    public List<ExpenseResponse> findExpenses() throws EmptyExpenseListException, FriendNotFoundException {
        final List<Expense> expenses = expenseService.findExpenses();
        return toExpensesRespond(expenses);
    }

    @PostMapping("/friend/{id}")
    public void addExpense(@PathVariable long id, @RequestBody ExpenseRequest request) throws NegativeExpenseAmountException, EmptyFriendListException, FriendNotFoundException {
        expenseService.addExpense(id, request);

    }

    private List<ExpenseResponse> toExpensesRespond(List<Expense> expenses){
        return expenses.stream()
                .map(expense -> new ExpenseResponse(
                        expense.getIdExpense(),
                        expense.getFriend().getIdFriend(),
                        expense.getFriend().getName(),
                        expense.getAmount(),
                        expense.getDescription(),
                        expense.getExpenseDate()))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable long id) {
        expenseService.deleteExpense(id);
    }
}
