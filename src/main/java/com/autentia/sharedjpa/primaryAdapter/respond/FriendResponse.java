package com.autentia.sharedjpa.primaryAdapter.respond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
public class FriendResponse {
    private long idFriend;
    private String name;

}
