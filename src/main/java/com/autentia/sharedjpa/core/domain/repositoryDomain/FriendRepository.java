package com.autentia.sharedjpa.core.domain.repositoryDomain;


import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.core.domain.exception.EmptyExpenseListException;
import com.autentia.sharedjpa.core.domain.exception.EmptyFriendListException;

import java.util.List;

public interface FriendRepository {
    List<Friend> findAll() throws EmptyFriendListException;
    void save(Friend friend);
    void update(Long id, String name);
    Friend findFriendById(long idFriend);
    int getNumberOfFriends() throws EmptyExpenseListException;
    double getBalanceOfFriend(long id);
}
