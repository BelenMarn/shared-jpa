package com.autentia.sharedjpa.primaryAdapter.respond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {
    private long idExpense;
    private long idFriend;
    private String nameFriend;
    private double amount;
    private String description;
    private String expenseDate;


    public ExpenseResponse(long idExpense, long idFriend, double amount, String description, String expenseDate) {
        this.idExpense = idExpense;
        this.idFriend = idFriend;
        this.amount = amount;
        this.description = description;
        this.expenseDate = expenseDate;
    }
}
