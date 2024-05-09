package com.autentia.sharedjpa.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    private long idExpense;
    private Friend friend;
    private double amount;
    private String description;
    private String expenseDate;

}