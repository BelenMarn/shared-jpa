package com.autentia.sharedjpa.primaryAdapter.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequest {
    private long idFriend;
    private String name;

}
