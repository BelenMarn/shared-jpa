package com.autentia.sharedjpa.service;


import com.autentia.sharedjpa.core.domain.Balance;
import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.core.domain.exception.EmptyExpenseListException;
import com.autentia.sharedjpa.core.domain.exception.EmptyFriendListException;
import com.autentia.sharedjpa.core.domain.exception.NoBalanceFoundException;
import com.autentia.sharedjpa.core.domain.repositoryDomain.ExpenseRepository;
import com.autentia.sharedjpa.core.domain.repositoryDomain.FriendRepository;
import com.autentia.sharedjpa.core.service.BalanceService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BalanceServiceTest {
    private FriendRepository friendRepo = Mockito.mock(FriendRepository.class);
    private ExpenseRepository expenseRepo = Mockito.mock(ExpenseRepository.class);

    private final BalanceService sut = new BalanceService(expenseRepo, friendRepo);

    @Test
    public void shouldGetFinalBalance() throws EmptyExpenseListException, NoBalanceFoundException, EmptyFriendListException {
        // GIVEN
        List<Balance> expected = new ArrayList<>();
        Friend friend = new Friend();
        expected.add(new Balance(friend, 50.00));

        // Simulamos el comportamiento
        Mockito.when(expenseRepo.getAverageSpending()).thenReturn(25.0);
        Mockito.when(friendRepo.findAll()).thenReturn(Collections.singletonList(friend));

        // WHEN
        List<Balance> given = sut.getFinalBalance();

        // THEN
        Assertions.assertEquals(expected.size(), given.size());
    }
}

