package com.autentia.sharedjpa.secondaryAdapter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expense")
public class ExpenseEntity {

    @Id
    private long idExpense;
    private long idFriend;
    private double amount;
    private String description;
    private String expenseDate;

}