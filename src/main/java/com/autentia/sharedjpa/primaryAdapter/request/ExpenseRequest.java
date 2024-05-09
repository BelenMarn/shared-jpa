package com.autentia.sharedjpa.primaryAdapter.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {
    private double amount;
    private String description;

}