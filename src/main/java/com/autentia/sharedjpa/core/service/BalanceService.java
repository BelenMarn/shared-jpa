package com.autentia.sharedjpa.core.service;

import com.autentia.sharedjpa.core.domain.Balance;
import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.core.domain.exception.EmptyExpenseListException;
import com.autentia.sharedjpa.core.domain.exception.EmptyFriendListException;
import com.autentia.sharedjpa.core.domain.exception.NoBalanceFoundException;
import com.autentia.sharedjpa.core.domain.repositoryDomain.ExpenseRepository;
import com.autentia.sharedjpa.core.domain.repositoryDomain.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BalanceService {
    private FriendRepository friendRepo;
    private ExpenseRepository expenseRepo;

    @Autowired
    public BalanceService(ExpenseRepository expenseRepo, FriendRepository friendRepo) {
        this.expenseRepo = expenseRepo;
        this.friendRepo = friendRepo;
    }

    public List<Balance> getFinalBalance() throws EmptyExpenseListException, EmptyFriendListException, NoBalanceFoundException {
        double averageSpending = expenseRepo.getAverageSpending();
        List<Balance> balances = new ArrayList<>();

        List<Friend> friends = friendRepo.findAll();

        for(Friend friend : friends) {
            Balance balance = new Balance();
            Friend f = new Friend(friend.getIdFriend(), friend.getName());
            balance.setFriend(f);

            double unroundedBalance = friendRepo.getBalanceOfFriend(friend.getIdFriend()) - averageSpending;
            double roundedBalance = Math.round(unroundedBalance * 100.0) / 100.0;
            balance.setBalance(roundedBalance);

            balances.add(balance);
        }

        if (!balances.isEmpty()) {
            return balances;
        } else {
            throw new NoBalanceFoundException("No balance found");
        }
    }

}
