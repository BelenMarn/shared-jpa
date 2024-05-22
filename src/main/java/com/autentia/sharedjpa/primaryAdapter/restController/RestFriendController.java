package com.autentia.sharedjpa.primaryAdapter.restController;

import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.core.domain.exception.EmptyFriendListException;
import com.autentia.sharedjpa.core.domain.exception.FriendNotFoundException;
import com.autentia.sharedjpa.core.service.FriendService;
import com.autentia.sharedjpa.primaryAdapter.request.FriendRequest;
import com.autentia.sharedjpa.primaryAdapter.respond.FriendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/friend")
public class RestFriendController {

    private final FriendService friendService;

    @Autowired
    public RestFriendController(FriendService friendService){this.friendService = friendService;}

    @GetMapping()
    public List<FriendResponse> findAll() throws EmptyFriendListException {
        final List<Friend> friends = friendService.findFriends();

        if(!friends.isEmpty()){
            return toFriendsRespond(friends);
        }else{
            throw new EmptyFriendListException("Empty friend list");
        }

    }

    @GetMapping("/{id}")
    public Friend findById(@PathVariable long id) throws FriendNotFoundException {
        final Friend friend = friendService.findFriendById(id);

        if(friend != null){
            return friend;
        }else{
            throw new FriendNotFoundException("Friend not found");
        }

    }

    @PostMapping()
    public void newFriend(@RequestBody FriendRequest request){
        final Friend friend = toFriend(request);
        friendService.addFriend(friend);
    }

    @PutMapping("/{id}")
    public void updateFriend(@PathVariable Long id, @RequestParam String name) {
        friendService.updateFriend(id, name);
    }

    private Friend toFriend(FriendRequest request) {
        return new Friend(
                request.getIdFriend(),
                request.getName());
    }

    private List<FriendResponse> toFriendsRespond(List<Friend> friends){
        return friends.stream()
                .map(friend -> new FriendResponse(
                    friend.getIdFriend(),
                    friend.getName()))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteFriend(@PathVariable Long id) {
        friendService.deleteFriend(id);
    }
}
