package com.autentia.sharedjpa.core.service;

import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.core.domain.exception.EmptyFriendListException;
import com.autentia.sharedjpa.core.domain.exception.FriendNotFoundException;
import com.autentia.sharedjpa.core.domain.repositoryDomain.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Service
public class FriendService {

    private final FriendRepository friendRepo;

    @Autowired
    public FriendService(FriendRepository friendRepo) {
        this.friendRepo = friendRepo;
    }

    public List<Friend> findFriends() throws EmptyFriendListException {
        final List<Friend> friends = new ArrayList<>();
        final Iterator<Friend> friendsIterator = friendRepo.findAll().iterator();
        while(friendsIterator.hasNext()){
            friends.add(friendsIterator.next());
        }

        if(!friends.isEmpty()){
            return friends;
        }else{
            throw new EmptyFriendListException("Empty friend list");
        }
    }

    public Friend findFriendById(long idFriend) throws FriendNotFoundException {
        final Friend friend = friendRepo.findFriendById(idFriend);

        if(friend == null){
            throw new FriendNotFoundException("Friend not found");
        }else{
            return friend;
        }

    }

    public void addFriend(Friend friend){
        friend.setIdFriend(generateFriendId());
        friendRepo.save(friend);
    }

    public void updateFriend(Long id, String name){
        friendRepo.update(id, name);
    }

    public long generateFriendId(){
        return new Date().getTime();
    }

}
