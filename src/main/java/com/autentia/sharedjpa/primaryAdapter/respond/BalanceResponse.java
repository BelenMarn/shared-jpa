package com.autentia.sharedjpa.primaryAdapter.respond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponse {
    private String name;
    private double balance;

}
